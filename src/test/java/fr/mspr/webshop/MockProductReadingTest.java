package fr.mspr.webshop;

import fr.mspr.webshop.controller.ProductController;
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
@WebMvcTest(ProductController.class)
public class MockProductReadingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @Value("${app.apikey}")
    private String APIKEY;

    private ProductDTO expectedProduct;
    private ProductDTO expectedProductWithId5;
    private ProductDTO expectedProductWithId55;

    @BeforeEach
    public void setUp() {
        long id = 1;
        LocalDateTime of = LocalDateTime.of(2023, 2, 19, 13, 42, 19);
        expectedProduct = ProductDTO.builder()
                .id(id)
                .name("Rex Bailey")
                .createdAt(of)
                .stock(12059)
                .details(new ProductDetails(
                        659.00F,
                        "The Nagasaki Lander is the trademarked name of several series of" +
                                " Nagasaki sport bikes, that started with the 1984 ABC800J",
                        "red"))
                .build();

        expectedProductWithId5 = ProductDTO.builder()
                .id(5L)
                .name("Mr. Felix Homenick DDS")
                .createdAt(LocalDateTime.parse("2023-02-19T17:59:22.648"))
                .stock(49045)
                .details(new ProductDetails(
                        445.00F,
                        "Andy shoes are designed to keeping in mind durability as well as trends," +
                                " the most stylish range of shoes & sandals",
                        "mint green"))
                .build();
        expectedProductWithId55 = ProductDTO.builder()
                .id(55L)
                .name("Joan Rowe")
                .createdAt(LocalDateTime.parse("2023-02-20T06:49:56.880"))
                .stock(94135)
                .details(new ProductDetails(
                        714.00F,
                        "Andy shoes are designed to keeping in mind durability" +
                                " as well as trends, the most stylish range of shoes & sandals",
                        "fuchsia"))
                .build();
    }

    @Test
    public void getProduct_returnsProduct() throws Exception {

        //THEN
        mockMvc.perform(get("/api/webshop/product/mock/get/" + expectedProduct.getId())
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedProduct.getId()))
                .andExpect(jsonPath("$.name").value(expectedProduct.getName()))
//                .andExpect(jsonPath("$.createdAt").value(expectedProduct.getCreatedAt().toString()))
                .andExpect(jsonPath("$.stock").value(expectedProduct.getStock()))
                .andExpect(jsonPath("$.details.price").value(expectedProduct.getDetails().getPrice()))
                .andExpect(jsonPath("$.details.color").value(expectedProduct.getDetails().getColor()))
                .andExpect(jsonPath("$.details.description").value(expectedProduct.getDetails().getDescription()));
    }

    @Test
    public void getProducts_returnsListOfProducts() throws Exception {

        //THEN
        mockMvc.perform(get("/api/webshop/product/mock/list")
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(expectedProduct.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedProduct.getName()))
//                .andExpect(jsonPath("$.content[0].createdAt").value(expectedProduct.getCreatedAt().toString()))
                .andExpect(jsonPath("$.content[0].stock").value(expectedProduct.getStock()))
                .andExpect(jsonPath("$.content[0].details.price").value(expectedProduct.getDetails().getPrice()))
                .andExpect(jsonPath("$.content[0].details.color").value(expectedProduct.getDetails().getColor()))
                .andExpect(jsonPath("$.content[0].details.description").value(expectedProduct.getDetails().getDescription()));
    }

    @Test
    public void getProducts_returnsListOfProductsBasedOnOrders() throws Exception {
        //THEN
        mockMvc.perform(get("/api/webshop/product/mock/customer/5/order/5")
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(expectedProductWithId5.getId()))
                .andExpect(jsonPath("$.[0].name").value(expectedProductWithId5.getName()))
                .andExpect(jsonPath("$.[0].stock").value(expectedProductWithId5.getStock()))
                .andExpect(jsonPath("$.[0].details.price").value(expectedProductWithId5.getDetails().getPrice()))
                .andExpect(jsonPath("$.[0].details.color").value(expectedProductWithId5.getDetails().getColor()))
                .andExpect(jsonPath("$.[0].details.description").value(expectedProductWithId5.getDetails().getDescription()))

                .andExpect(jsonPath("$.[1].id").value(expectedProductWithId55.getId()))
                .andExpect(jsonPath("$.[1].name").value(expectedProductWithId55.getName()))
                .andExpect(jsonPath("$.[1].stock").value(expectedProductWithId55.getStock()))
                .andExpect(jsonPath("$.[1].details.price").value(expectedProductWithId55.getDetails().getPrice()))
                .andExpect(jsonPath("$.[1].details.color").value(expectedProductWithId55.getDetails().getColor()))
                .andExpect(jsonPath("$.[1].details.description").value(expectedProductWithId55.getDetails().getDescription()));
    }
}
