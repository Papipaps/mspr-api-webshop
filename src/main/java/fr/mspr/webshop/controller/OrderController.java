package fr.mspr.webshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.mspr.webshop.data.OrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("api/webshop/order")
@RestController
public class OrderController {

    @Value("${app.customers.api-url}")
    private String CUSTOMER_APIURL;
    @GetMapping("mock/customer/{customerId}")
    private ResponseEntity<OrderDTO[]> getOrdersByUser(@PathVariable Long customerId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseProducts = null;
        String url = String.format("%s/%s/orders", CUSTOMER_APIURL,customerId);
        responseProducts = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            OrderDTO[] orderDTOS = mapper.readValue(responseProducts.getBody(), OrderDTO[].class);
            return ResponseEntity.ok().body(orderDTOS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }




}
