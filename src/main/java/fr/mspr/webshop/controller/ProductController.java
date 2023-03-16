package fr.mspr.webshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.mspr.webshop.data.ProductDTO;
import fr.mspr.webshop.utils.ListToPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("api/webshop/product")
public class ProductController {
    @Value("${app.products.api-url}")
    private String PRODUCT_APIURL;

    @Value("${app.customers.api-url}")
    private String CUSTOMER_APIURL;


    @GetMapping("mock/get/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/%s", PRODUCT_APIURL, id);
        ResponseEntity<String> responseProducts = null;
        try {
            responseProducts = restTemplate.getForEntity(url, String.class);
        } catch (HttpServerErrorException e) {
            ProductDTO res = ProductDTO.builder().build();
            res.setErrorMessage(String.format("No product with id %s found", id));
            res.setHasError(true);
            return ResponseEntity.status(500).body(res);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            ProductDTO productDTO = mapper.readValue(responseProducts.getBody(), ProductDTO.class);
            return ResponseEntity.ok().body(productDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("mock/list")
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false, defaultValue = "9") int size
            , @RequestParam(required = false, defaultValue = "0") int page) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseProducts = null;
         try{
            responseProducts = restTemplate.getForEntity(PRODUCT_APIURL, String.class);
        }catch (HttpServerErrorException e){
            return ResponseEntity.status(500).body(Map.of("error",true,"message","external API is not responding"));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            ProductDTO[] productDTOS = mapper.readValue(responseProducts.getBody(), ProductDTO[].class);
            Pageable pageable = PageRequest.of(page, size);

            return ResponseEntity.ok().body(ListToPage.toPage(Arrays.asList(productDTOS), pageable, productDTOS.length));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("mock/customer/{customerId}/order/{id}")
    private ResponseEntity<?> getProductByUserOrder(@PathVariable Long id, @PathVariable Long customerId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseProducts = null;
        String url = String.format("%s/%s/orders/%s/products", CUSTOMER_APIURL,customerId, id);
        try{
        responseProducts = restTemplate.getForEntity(url, String.class);
        }catch (HttpServerErrorException e){
            return ResponseEntity.status(500).body(Map.of("error",true,"message","external API is not responding"));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            ProductDTO[] productDTOS = mapper.readValue(responseProducts.getBody(), ProductDTO[].class);
            return ResponseEntity.ok().body(productDTOS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
