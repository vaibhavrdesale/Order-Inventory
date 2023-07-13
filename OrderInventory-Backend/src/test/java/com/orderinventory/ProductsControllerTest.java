package com.orderinventory;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.orderinventory.dto.ProductsDto;
import com.orderinventory.exception.ProductsNotFoundException;
import com.orderinventory.services.ProductServices;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "vaibhav@gmail.com")
public class ProductsControllerTest {

	@MockBean
	private ProductServices productServices;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSaveProduct() throws Exception {
		ProductsDto productsDto = new ProductsDto();
		productsDto.setProductId(12);
		productsDto.setProductName("Campus Shoes");
		productsDto.setUnitPrice(BigDecimal.valueOf(1));
		productsDto.setProductDetails("size-7,color-black");
		
		List<ProductsDto> orderDtos = Arrays.asList(productsDto);

		when(productServices.saveProduct(productsDto)).thenReturn(productsDto);

		mockMvc.perform(
				post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(productsDto)))
				.andExpect(status().isOk());

	}

	@Test
	void testUpdateProduct() throws Exception {
		// Create a sample ProductsDto object
		ProductsDto existingProduct = new ProductsDto();
		existingProduct.setProductId(1);
		existingProduct.setProductName("camera");
		existingProduct.setUnitPrice(BigDecimal.valueOf(100.0));
		existingProduct.setProductDetails("nikon");

		ProductsDto updatedProduct = new ProductsDto();
		updatedProduct.setProductId(1);
		updatedProduct.setProductName("mobile");
		updatedProduct.setUnitPrice(BigDecimal.valueOf(10.0));
		updatedProduct.setProductDetails("iphone");

		when(productServices.updateProduct(updatedProduct)).thenReturn(updatedProduct);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(updatedProduct))).andExpect(status().isOk());
				
	}

	@Test
	void testGetProductById() throws Exception {
		Integer productsId = 1;
		ProductsDto productsDto = new ProductsDto();
		productsDto.setProductId(1);

		when(productServices.getProductById(productsId)).thenReturn(productsDto);

		mockMvc.perform(get("/api/v1/products/{productId}", productsId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.productId", is(1)));

		verify(productServices, times(1)).getProductById(productsId);

	}

	@Test
	void testGetAllProducts() throws Exception {
		ProductsDto productsDto = new ProductsDto();
		productsDto.setProductId(12);
		productsDto.setProductName("Campus Shoes");
		productsDto.setUnitPrice(BigDecimal.valueOf(1));
		productsDto.setProductDetails("size-7,color-black");

		List<ProductsDto> productsDtos = Arrays.asList(productsDto);

		when(productServices.getAllProducts()).thenReturn(productsDtos);

		mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productId").value(12))
				.andExpect(jsonPath("$[0].productName").value("Campus Shoes"))
				.andExpect(jsonPath("$[0].unitPrice").value(BigDecimal.valueOf(1)))
				.andExpect(jsonPath("$[0].productDetails").value("size-7,color-black"));

		verify(productServices, times(1)).getAllProducts();
	}

	@Test
	void testGetProductsByName() throws Exception {

		String productsName = "Tammy Bryant";
		ProductsDto productsDto = new ProductsDto();
		productsDto.setProductName(productsName);

		List<ProductsDto> productsDtos = Arrays.asList(productsDto);

		when(productServices.getProductsByName(productsName)).thenReturn(productsDtos);

		mockMvc.perform(get("/api/v1/productname/{productName}", productsName)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productName").value(productsName));

		verify(productServices, times(1)).getProductsByName(productsName);
	}

	@Test
	void testGetByUnitPriceBetween() throws Exception {

		double minPrice = 10;
		double maxPrice = 30;

		ProductsDto productsDto = new ProductsDto();

		productsDto.setUnitPrice(new BigDecimal(25));

		List<ProductsDto> productsDtos = Arrays.asList(productsDto);

		when(productServices.findByUnitPriceBetween(minPrice, maxPrice)).thenReturn(productsDtos);

		mockMvc.perform(get("/api/v1/products/unitprice?min={minPrice}&max={maxPrice}", minPrice, maxPrice))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].unitPrice").value(new BigDecimal(25)));

		verify(productServices, times(1)).findByUnitPriceBetween(minPrice, maxPrice);
	}

	@Test
	void testGetSortedProducts() throws Exception {
		List<ProductsDto> expectedProducts = Arrays.asList(
				new ProductsDto(12, "camera", new BigDecimal(20), "Nikon Camera"),
				new ProductsDto(1, "mobile", new BigDecimal(30), "iphone X"));

		Mockito.when(productServices.getSortedProducts(Mockito.any())).thenReturn(expectedProducts);

		// Assert
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/products/sort").param("field", "productName").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
						.value(expectedProducts.get(0).getProductName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].productName")
						.value(expectedProducts.get(1).getProductName()));

	}

	// Negative test case for get product by id controller method
	@Test
	void testGetProductByIdNotFound() throws Exception {
		Integer productId = 1;

		ProductsDto productsDto = new ProductsDto();
		productsDto.setProductId(productId);

		when(productServices.getProductById(productId)).thenThrow(new ProductsNotFoundException("No Product Found!"));

		mockMvc.perform(get("/api/v1/products/{productId}", productId)).andExpect(status().isNotFound());

		verify(productServices, times(1)).getProductById(productId);

	}

	// Negative test case for get product by name controller method
	@Test
	void testGetProductsByNameNotFound() throws Exception {
		String productName = "camera";

		ProductsDto productsDto = new ProductsDto();
		productsDto.setProductName(productName);

		when(productServices.getProductsByName(productName))
				.thenThrow(new ProductsNotFoundException("No Product Found!"));

		mockMvc.perform(get("/api/v1/products/productname/{productName}", productName))
				.andExpect(status().isNotFound());

	}

	// Negative test case for get All products controller method
	@Test
	void  testGetAllProductsNotFound() throws Exception {
		
		when(productServices.getAllProducts()).thenThrow(new ProductsNotFoundException("No Product Found!"));
		
		mockMvc.perform(get("/api/v1/products")).andExpect(status().isNotFound());

        verify(productServices, times(1)).getAllProducts();
	}

	// Negative test case when products not found between price range
	@Test
	void testGetByUnitPriceBetweenNotFound() throws Exception {
		double minPrice = 1;
		double maxPrice = 5;

		when(productServices.findByUnitPriceBetween(minPrice, maxPrice))
				.thenThrow(new ProductsNotFoundException("Products not find between this unit price range"));

		mockMvc.perform(get("/api/v1/products/unitprice?min={minPrice}&max={maxPrice}", minPrice, maxPrice))
				.andExpect(status().isNotFound());

		verify(productServices, times(1)).findByUnitPriceBetween(minPrice, maxPrice);

	}

	// Negative test case for sorting method api
	@Test
	void testGetSortedProductsNotFound() throws Exception{
		
		when(productServices.getSortedProducts(Mockito.any()))
        .thenThrow(new ProductsNotFoundException("Products not found to sort"));

		mockMvc.perform(MockMvcRequestBuilders
		        .get("/api/v1/products/sort").param("field", "productName").accept(MediaType.APPLICATION_JSON))
		        .andExpect(MockMvcResultMatchers.status().isNotFound());
				     
	}

	@Test
	void testSaveProductWithEmptyProductName() throws Exception {
	    ProductsDto productsDto = new ProductsDto();
	    productsDto.setProductId(12);
	    productsDto.setProductName("");
	    productsDto.setUnitPrice(BigDecimal.valueOf(1));
	    productsDto.setProductDetails("size-7,color-black");

	    mockMvc.perform(post("/api/v1/products")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(productsDto)))
	            .andExpect(status().isBadRequest());

	    verify(productServices, times(0)).saveProduct(productsDto);
	}

	//Negative test case for while updating product
	@Test
	@Disabled
	void testUpdateProductNotFound() throws Exception {
	    ProductsDto productsDto = new ProductsDto();
	    productsDto.setProductName("Campus Shoes");
	    productsDto.setUnitPrice(BigDecimal.valueOf(1));
	    productsDto.setProductDetails("size-7,color-black");

	    when(productServices.updateProduct(productsDto))
	            .thenThrow(new ProductsNotFoundException("No product found for this id"));

	    mockMvc.perform(put("/api/v1/products")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(productsDto)))
	            .andExpect(status().isNotFound());

	    verify(productServices, times(1)).updateProduct(productsDto);
	}


	public static String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
