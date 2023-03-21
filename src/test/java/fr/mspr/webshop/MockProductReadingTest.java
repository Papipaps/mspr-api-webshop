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
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        long id = 6;
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
    }

    @Test
    public void getProduct_returnsProduct() throws Exception {

        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/api/webshop/product/mock/get/" + expectedProduct.getId())
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andReturn();
        // THEN
        String response = mvcResult.getResponse().getContentAsString();
        assertFalse(response.isEmpty(), "Response should not be empty");
    }

    @Test
    public void getProducts_returnsListOfProducts() throws Exception {

        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/api/webshop/product/mock/list")
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andReturn();
        // THEN
        String response = mvcResult.getResponse().getContentAsString();
        assertFalse(response.isEmpty(), "Response should not be empty");
    }

    @Test
    public void getProducts_givenCustomer6AndOrder6_returnsListOfProductsBasedOnOrder() throws Exception {
        //THEN
        MvcResult mvcResult = mockMvc.perform(get("/api/webshop/product/mock/customer/6/order/6")
                        .header("APIKEY", APIKEY))
                .andExpect(status().isOk())
                .andReturn();
        // THEN
        String response = mvcResult.getResponse().getContentAsString();
        assertFalse(response.isEmpty(), "Response should not be empty");
    }
}
