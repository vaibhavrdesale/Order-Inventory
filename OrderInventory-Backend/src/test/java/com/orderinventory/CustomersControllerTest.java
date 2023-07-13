package com.orderinventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderinventory.dto.CountDto;
import com.orderinventory.dto.CustomersDto;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.ResponseDto;
import com.orderinventory.dto.ShipmentCustomDto;
import com.orderinventory.exception.CustomerNotFoundException;
import com.orderinventory.services.CustomersServices;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "sagar@gmail.com")
public class CustomersControllerTest {

	@MockBean
	private CustomersServices customerService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetCustomersByFullName() throws Exception {

		String fullName = "Tammy Bryant";
		 // Create a sample customer DTO
		CustomersDto customerDto = new CustomersDto();
		customerDto.setFullName(fullName);

		List<CustomersDto> customerDtos = Arrays.asList(customerDto);
		// Mock the behavior of the customer service
		when(customerService.getCustomersByFullName(fullName)).thenReturn(customerDtos);
		//perform get request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/name/{fullName}", fullName))

				.andExpect(status().isOk())

				.andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").value(fullName));

		 // Verify that the customer service method was called
		verify(customerService, times(1)).getCustomersByFullName(fullName);

	}

	@Test
	void testGetCustomersByEmail() throws Exception {

		String emailAddress = "tammy.bryant@internalmail";

		CustomersDto customerDto = new CustomersDto();
		customerDto.setEmailAddress(emailAddress);

		List<CustomersDto> customerDtos = Arrays.asList(customerDto);
		
		// Mock the behavior of the customer service
		when(customerService.getCustomersByEmail(emailAddress)).thenReturn(customerDtos);

		
        //perform get request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/email-{emailAddress}", emailAddress))

				.andExpect(status().isOk())

				.andExpect(MockMvcResultMatchers.jsonPath("$[0].emailAddress").value(emailAddress));

		verify(customerService, times(1)).getCustomersByEmail(emailAddress);
	}


	
	@Test
    void testSaveCustomers() throws Exception {
        // Create a sample customer DTO
        CustomersDto customerDto = new CustomersDto();
        customerDto.setCustomerId(1);
        customerDto.setEmailAddress("sanjaykamble1107@gmail.com");
        customerDto.setFullName("sanjay");
        
        ResponseDto responseDto=new ResponseDto();
        responseDto.setResponseMessage("Record saved successfully");
        // Define the expected response


        // Mock the behavior of the customer service
        when(customerService.saveCustomers(customerDto)).thenReturn(responseDto);

       
        // Perform the POST request
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDto)))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.responseMessage").value(responseDto.getResponseMessage()));


        // Verify that the customer service method was called
        verify(customerService, times(1)).saveCustomers(customerDto);
    }


	public static String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	@Test
	void testUpdateCustomer() throws Exception {

		CustomersDto customerDto = new CustomersDto();
		customerDto.setCustomerId(1);
		customerDto.setEmailAddress("sanjaykamble1107@gmail.com");
		customerDto.setFullName("sanjay");

		ResponseDto responseDto=new ResponseDto();
        responseDto.setResponseMessage("Record saved successfully");

		when(customerService.updateCustomer(customerDto)).thenReturn(responseDto);

		mockMvc.perform(put("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseMessage").value(responseDto.getResponseMessage()));
			

		verify(customerService, times(1)).updateCustomer(customerDto);
	}
	
	@Test
	public void testGetShipmentsByCustomerId() throws Exception {
		Integer customerId = 1;

		ShipmentCustomDto shipmentCustomDto = new ShipmentCustomDto();
		shipmentCustomDto.setCustomerId(customerId);

		List<ShipmentCustomDto> shipmentCustomDtos = Arrays.asList(shipmentCustomDto);
		when(customerService.getShipmentsByCustomerId(customerId)).thenReturn(shipmentCustomDtos);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customerss/{customerId}", customerId))

				.andExpect(status().isOk())

				.andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(customerId));

		verify(customerService, times(1)).getShipmentsByCustomerId(customerId);
	}

	@Test
	public void testGetOrdersBycustomerId() throws Exception {
		Integer customerId = 1;
		OrderDto orderDto = new OrderDto();
		orderDto.setCustomerId(customerId);

		List<OrderDto> orderDtos = Arrays.asList(orderDto);
		when(customerService.getOrdersByCustomerId(customerId)).thenReturn(orderDtos);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{custId}/order", customerId))

				.andExpect(status().isOk())

				.andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(customerId));

		verify(customerService, times(1)).getOrdersByCustomerId(customerId);
	}

	@Test
	public void testGetAllCustomers() throws Exception {

		CustomersDto customerDto = new CustomersDto();
		customerDto.setCustomerId(1);
		customerDto.setEmailAddress("sanjaykamble@gmail.com");
		customerDto.setFullName("sanjay");

		List<CustomersDto> customerDtos = Arrays.asList(customerDto);

		when(customerService.getAllCustomers()).thenReturn(customerDtos);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].customerId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].emailAddress").value("sanjaykamble@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").value("sanjay"));
	}
	
	@Test
	public void testGetCustomerCountByShipmentStatus() throws Exception {
		String shipmentStatus="DELIVERED";
		CountDto countDto=new CountDto();
		countDto.setShipmentStatus(shipmentStatus);
		
		when(customerService.getCustomerCountByShipmentStatus("DELIVERED")).thenReturn(countDto);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/shipment/{shipmentStatus}", shipmentStatus))

		.andExpect(status().isOk())

		.andExpect(MockMvcResultMatchers.jsonPath("$.shipmentStatus").value(shipmentStatus));
		
		verify(customerService, times(1)).getCustomerCountByShipmentStatus(shipmentStatus);
	}
	
	
	//NEGATIVE TEST CASE
	

	
	@Test
   public void testGetCustomersByFullNameNotFound() throws Exception {
		String fullName="";
   CustomersDto customerDto=new CustomersDto();
customerDto.setFullName(fullName);

  when(customerService.getCustomersByFullName(fullName)).thenThrow(new CustomerNotFoundException("No Customer Found"));
       
   mockMvc.perform(get("/api/v1/customers/name/{fullName}",fullName)).andExpect(status().isNotFound());

  verify(customerService,times(0)).getCustomersByFullName(fullName);

    }
	
	@Test
	public void testGetCustomersByEmailNotFound() throws Exception{
		String emailAddress="sanjaykamble@gamil.com";
		CustomersDto customerDto=new CustomersDto();
		customerDto.setEmailAddress(emailAddress);
		
		when(customerService.getCustomersByEmail(emailAddress)).thenThrow(new CustomerNotFoundException("No Customer Found"));
		
		mockMvc.perform(get("/api/v1/customers/email-{emailAddress}",emailAddress)).andExpect(status().isNotFound());
		
		verify(customerService,times(1)).getCustomersByEmail(emailAddress);
	}
	
	
	
	@Test
	public void testUpdateCustomerNotFound()throws Exception{
		CustomersDto customerDto=new CustomersDto();
		
		customerDto.setEmailAddress("sanjaykamble@gmail.com");
		customerDto.setFullName("sanjay");
		
		when(customerService.updateCustomer(customerDto)).thenThrow(new  CustomerNotFoundException("customer not updated"));
		
		mockMvc.perform(put("/api/v1/customers").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customerDto))).andExpect(status().isNotFound());
		
		verify(customerService,times(1)).updateCustomer(customerDto);
	}
	
	@Test
	public void testGetShipmentsByInvalidCustomerId()throws Exception{
		
		Integer customerId=1;
		ShipmentCustomDto shipmentCustomDto=new ShipmentCustomDto();
		shipmentCustomDto.setCustomerId(customerId);
		
	//	List<ShipmentCustomDto> shipmentCustomDtos=Arrays.asList(shipmentCustomDto);
		
		when(customerService.getShipmentsByCustomerId(1)).thenThrow(new CustomerNotFoundException("Shipment not found with given customer id"));
		
		mockMvc.perform(get("/api/v1/customerss/{customerId}",customerId)).andExpect(status().isNotFound());
		
		verify(customerService,times(1)).getShipmentsByCustomerId(customerId);
	}
	
	@Test
	public void testGetOrdersByInvalidcustomerId() throws Exception{
		Integer customerId=1;
		OrderDto orderDto=new OrderDto();
		orderDto.setCustomerId(customerId);
		
		when(customerService.getOrdersByCustomerId(customerId)).thenThrow(new CustomerNotFoundException("no orders found with given customerId"));
		
	mockMvc.perform(get("/api/v1/customers/{custId}/order",customerId)).andExpect(status().isNotFound());
	
	verify(customerService,times(1)).getOrdersByCustomerId(customerId);
	}
	
	@Test
	public void testGetAllCustomersNotFound()throws Exception{
		CustomersDto customersDto=new CustomersDto();
		customersDto.setCustomerId(1);
		customersDto.setEmailAddress("sanjaykamble@gmail.com");
		customersDto.setFullName("sanjay");
		
		when(customerService.getAllCustomers()).thenThrow(new CustomerNotFoundException("no customers found"));
		
		mockMvc.perform(get("/api/v1/customers")).andExpect(status().isNotFound());
		
		verify(customerService, times(1)).getAllCustomers();
	}
	
	@Test
	public void testGetCustomerCountByInvalidShipmentStatus() throws Exception{
	CountDto countDto=new CountDto();
	countDto.setCountCustomer(1);
	countDto.setShipmentStatus("NotDelivered");
	
	when(customerService.getCustomerCountByShipmentStatus("NotDelivered")).thenThrow(new CustomerNotFoundException("Invalid Shipment Status"));
	
	mockMvc.perform(get("/api/v1/customers/shipment/{shipmentStatus}","NotDelivered")).andExpect(status().isNotFound());
	
	verify(customerService,times(1)).getCustomerCountByShipmentStatus("NotDelivered");
	}
	
	@Test
    public void testSaveCustomers_InvalidInput()throws Exception{
        CustomersDto customerDto=new CustomersDto();
        customerDto.setCustomerId(1);
        customerDto.setEmailAddress(null);
        customerDto.setFullName(null);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CustomersDto>> violations = validator.validate(customerDto);
        assertThat(violations)
        .extracting(ConstraintViolation::getPropertyPath)
        .extracting(Object::toString).contains("emailAddress").contains("fullName");

        

        mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)

                .content(asJsonString(customerDto))).andExpect(status().isBadRequest());

        

        verify(customerService,times(0)).saveCustomers(customerDto);

    }
	
	
	}


