package fr.mspr.webshop;

import fr.mspr.webshop.controller.CustomerController;
import fr.mspr.webshop.data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class MockCustomerReadingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerController customerController;

    @Value("${app.apikey}")
    private String APIKEY;

    private CustomerDTO expectedCustomerWith_Id9_Index5;

    @BeforeEach
    public void setUp() {
        expectedCustomerWith_Id9_Index5 = CustomerDTO.builder()
                .id(9L)
                .name("Patsy Koelpin")
                .firstName("Vilma")
                .lastName("Gleichner")
                .username("Rylee17")
                .address(Adress.builder()
                        .postalCode("29582-1470")
                        .city("Glenview")
                        .build())
                .company(Company.builder()
                        .companyName("Mann - Klein")
                        .build())
                .createdAt(LocalDateTime.parse("2023-02-20T13:21:34.908"))
                .orders(List.of(
                        OrderDTO.builder()
                                .createdAt(LocalDateTime.parse("2023-02-19T22:36:57.004"))
                                .id(9L)
                                .customerId(9L)
                                .build(),
                        OrderDTO.builder()
                                .createdAt(LocalDateTime.parse("2023-02-20T10:08:35.226"))
                                .id(59L)
                                .customerId(9L)
                                .build()
                ))
                .build();
    }
    @Test
    public void getCustomer_returnsCustomer() throws Exception {
        //THEN
        mockMvc.perform(get("/api/webshop/customer/mock/get/" + expectedCustomerWith_Id9_Index5.getId())
                        .header("APIKEY",APIKEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedCustomerWith_Id9_Index5.getId()))
                .andExpect(jsonPath("$.name").value(expectedCustomerWith_Id9_Index5.getName()))
//                .andExpect(jsonPath("$.createdAt").value(expectedCustomerWith_Id9_Index5.getCreatedAt().toString()))
                .andExpect(jsonPath("$.address.postalCode").value(expectedCustomerWith_Id9_Index5.getAddress().getPostalCode()))
                .andExpect(jsonPath("$.address.city").value(expectedCustomerWith_Id9_Index5.getAddress().getCity()))
                .andExpect(jsonPath("$.company.companyName").value(expectedCustomerWith_Id9_Index5.getCompany().getCompanyName()))
                .andExpect(jsonPath("$.orders[0].id").value(expectedCustomerWith_Id9_Index5.getOrders().get(0).getId()))
                .andExpect(jsonPath("$.orders[1].id").value(expectedCustomerWith_Id9_Index5.getOrders().get(1).getId()));
    }

    @Test
    public void getCustomers_returnsListOfCustomers() throws Exception {
        //THEN
        mockMvc.perform(get("/api/webshop/customer/mock/list")
                        .header("APIKEY",APIKEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[5].id").value(expectedCustomerWith_Id9_Index5.getId()))
                .andExpect(jsonPath("$.content[5].name").value(expectedCustomerWith_Id9_Index5.getName()))
//                .andExpect(jsonPath("$.createdAt").value(expectedCustomerWith_Id9_Index5.getCreatedAt().toString()))
                .andExpect(jsonPath("$.content[5].address.postalCode").value(expectedCustomerWith_Id9_Index5.getAddress().getPostalCode()))
                .andExpect(jsonPath("$.content[5].address.city").value(expectedCustomerWith_Id9_Index5.getAddress().getCity()))
                .andExpect(jsonPath("$.content[5].company.companyName").value(expectedCustomerWith_Id9_Index5.getCompany().getCompanyName()))
                .andExpect(jsonPath("$.content[5].orders[0].id").value(expectedCustomerWith_Id9_Index5.getOrders().get(0).getId()))
                .andExpect(jsonPath("$.content[5].orders[1].id").value(expectedCustomerWith_Id9_Index5.getOrders().get(1).getId()));

    }
}
