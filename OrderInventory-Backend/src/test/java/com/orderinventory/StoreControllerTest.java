package com.orderinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderinventory.controller.StoreController;
import com.orderinventory.dto.StoresDto;
import com.orderinventory.services.StoreService;

@RunWith(SpringRunner.class)
//@WebMvcTest(StoreController.class)
@SpringBootTest(classes=OrderInventoryApplication.class)
@AutoConfigureMockMvc
public class StoreControllerTest {
	
	 @Autowired
	    private ObjectMapper objectMapper;

	    @Mock
	    private StoreService storeService;
	    
	    @InjectMocks
	    private StoreController storeController;
	
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
    }


   

    @Test
    public void testAddStore() throws Exception {
        // Set up test data and inputs
        StoresDto inputDto = new StoresDto();
        inputDto.setStoreId(1);
        inputDto.setStoreName("Test Store");
        inputDto.setWebAddress("https://www.example.com");
        inputDto.setPhysicalAddress("123 Test Street, Test City");
        inputDto.setLatitude(new BigDecimal("37.7749"));
        inputDto.setLongitude(new BigDecimal("-122.4194"));
        inputDto.setLogo("base64encodedLogoData"); // Replace with actual base64-encoded logo data
        inputDto.setLogoMimeType("image/png");
        inputDto.setLogoFilename("logo.png");
        inputDto.setLogoCharset("UTF-8");
        inputDto.setLogoLastUpdated(LocalDate.now());

        String expectedMessage = "Record Created Successfully";
        when(storeService.saveStore(inputDto)).thenReturn(expectedMessage);

        // Invoke the method being tested
        MvcResult result = mockMvc.perform(post("/api/v1/store/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert the result
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(expectedMessage, responseBody);
        
     // Verify the interactions with the storeServiceMock
        verify(storeService, times(1)).saveStore(inputDto);
//        verifyNoMoreInteractions(storeService);

    }
    
    
    
    
    @Test
    public void testUpdateWebAddress() throws Exception {
        // Mock the storeService behavior
        StoresDto updatedStoreDto = new StoresDto();
        updatedStoreDto.setStoreId(1);
        updatedStoreDto.setStoreName("My Store");
        updatedStoreDto.setWebAddress("http://example.com");
        when(storeService.updateWebAddress(anyInt(), anyString())).thenReturn(updatedStoreDto);

        // Test the endpoint
        mockMvc.perform(put("/api/v1/store/webaddress")
                .param("storeId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("http://example.com")) // Removed the extra set of quotes here
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(1))
                .andExpect(jsonPath("$.storeName").value("My Store"))
                .andExpect(jsonPath("$.webAddress").value("http://example.com"));

        // Verify that storeService.updateWebAddress was called with the expected arguments
        verify(storeService, times(1)).updateWebAddress(1, "http://example.com");
    }
    
    
    @Test
    public void testGetStoreById() throws Exception {
        // Mock the storeService behavior
        StoresDto store = new StoresDto();
        store.setStoreId(1);
        store.setStoreName("My Store");
        when(storeService.getStoreById(1)).thenReturn(store);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/store/storeid/{storeId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(1))
                .andExpect(jsonPath("$.storeName").value("My Store"));
        
        // Verify that storeService.getStoreById was called with the expected argument
        verify(storeService, times(1)).getStoreById(1);
    }
    
    @Test
    public void testGetAllStores() throws Exception {
        // Mock the storeService behavior
        List<StoresDto> stores = Arrays.asList(
                createStoreDto(1, "Store 1"),
                createStoreDto(2, "Store 2")
        );
        when(storeService.getAllStores()).thenReturn(stores);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/store/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].storeId").value(1))
                .andExpect(jsonPath("$[0].storeName").value("Store 1"))
                .andExpect(jsonPath("$[1].storeId").value(2))
                .andExpect(jsonPath("$[1].storeName").value("Store 2"));

        // Verify that storeService.getAllStores was called
        verify(storeService, times(1)).getAllStores();
    }

    private StoresDto createStoreDto(Integer storeId, String storeName) {
        StoresDto dto = new StoresDto();
        dto.setStoreId(storeId);
        dto.setStoreName(storeName);
        return dto;
    }

}



