package fr.mspr.webshop;

import fr.mspr.webshop.controller.CustomerController;
import fr.mspr.webshop.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void getRequest_invalidAPIKEY_BADREQUEST() throws Exception {
		//THEN
		mockMvc.perform(get("/api/webshop/customer/mock/get/")
						.header("APIKEY","invalid"))
				.andExpect(status().isUnauthorized());
	}

}
