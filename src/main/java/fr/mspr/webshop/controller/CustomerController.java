package fr.mspr.webshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.mspr.webshop.data.CustomerDTO;
import fr.mspr.webshop.utils.ListToPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("api/webshop/customer")
public class CustomerController {
    @Value("${app.customers.api-url}")
    private String CUSTOMER_APIURL;

    @GetMapping("mock/get/{id}")
    public ResponseEntity<CustomerDTO> getProfile(@PathVariable long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/%s", CUSTOMER_APIURL, id);
        ResponseEntity<String> responseProducts = null;
        try {
            responseProducts = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            CustomerDTO res = CustomerDTO.builder().build();
            res.setErrorMessage(String.format("No profile with id %s found", id));
            res.setHasError(true);
            return ResponseEntity.status(500).body(res);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            CustomerDTO customerDTO = mapper.readValue(responseProducts.getBody(), CustomerDTO.class);
            return ResponseEntity.ok().body(customerDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("mock/list")
    public ResponseEntity<Page<?>> getProfiles(
            @RequestParam(required = false, defaultValue = "9") int size
            , @RequestParam(required = false, defaultValue = "0") int page) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseProducts = null;
        responseProducts = restTemplate.getForEntity(CUSTOMER_APIURL, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            CustomerDTO[] customerDTOS = mapper.readValue(responseProducts.getBody(), CustomerDTO[].class);
            Pageable pageable = PageRequest.of(page, size);

            return ResponseEntity.ok().body(ListToPage.toPage(Arrays.asList(customerDTOS), pageable, customerDTOS.length));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
