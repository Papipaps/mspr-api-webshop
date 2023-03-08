package fr.mspr.webshop;

import fr.mspr.webshop.controller.OrderController;
import fr.mspr.webshop.data.OrderDTO;
import fr.mspr.webshop.data.ProductDTO;
import fr.mspr.webshop.data.ProductDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class MockOrderReadingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderController orderController;

    private ProductDTO expectedProductWithId5;
    private ProductDTO expectedProductWithId55;

    private OrderDTO order5;
    private OrderDTO order5_2;

    @Value("${app.apikey}")
    private String APIKEY;

    @BeforeEach
    public void setUp() {


        order5 = OrderDTO.builder()
                .createdAt(LocalDateTime.parse("2023-02-19T14:01:50.652"))
                .id(5L)
                .customerId(5L)
                .build();
        order5_2 = OrderDTO.builder()
                .createdAt(LocalDateTime.parse("2023-02-20T11:08:47.866"))
                .id(55L)
                .customerId(5L)
                .build();
    }

    @Test
    public void getOrdersWithId5_returnsOrdersSuccessfully() throws Exception {

        //THEN
        mockMvc.perform(get("/api/webshop/order/mock/customer/5")
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(order5.getId()))
                .andExpect(jsonPath("$.[0].customerId").value(order5.getCustomerId()))
                .andExpect(jsonPath("$.[0].createdAt").value(order5.getCreatedAt().toString()))

                .andExpect(jsonPath("$.[1].id").value(order5_2.getId()))
                .andExpect(jsonPath("$.[1].customerId").value(order5_2.getCustomerId()))
                .andExpect(jsonPath("$.[1].createdAt").value(order5_2.getCreatedAt().toString()));
    }


}
