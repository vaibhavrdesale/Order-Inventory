package com.orderinventory;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.orderinventory.controller.InventoryController;
import com.orderinventory.dto.CustomInventoryDto;
import com.orderinventory.dto.CustomersDto;
import com.orderinventory.dto.InventoryDetailStoreIdDto;
import com.orderinventory.dto.InventoryDto;
import com.orderinventory.dto.InventoryWithShipmentsDto;
import com.orderinventory.dto.ProductDto;
import com.orderinventory.dto.ShipmentStatusCountDTO;
import com.orderinventory.dto.StoresDto;
import com.orderinventory.services.InventoryService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderInventoryApplication.class)
@AutoConfigureMockMvc
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }
    
    
    @Test
    public void testGetAllInventory() throws Exception {
        // Mock the inventoryService behavior
        List<InventoryDto> inventoryList = Arrays.asList(
                createInventoryDto(1L, "Product 1", 1, "Store 1"),
                createInventoryDto(2L, "Product 2", 2, "Store 2")
        );
        when(inventoryService.getAllInventory()).thenReturn(inventoryList);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].inventoryId").value(1))
                .andExpect(jsonPath("$[0].productData.productName").value("Product 1"))
                .andExpect(jsonPath("$[0].productInventory").value(1))
                .andExpect(jsonPath("$[0].storeData.storeName").value("Store 1"))
                .andExpect(jsonPath("$[1].inventoryId").value(2))
                .andExpect(jsonPath("$[1].productData.productName").value("Product 2"))
                .andExpect(jsonPath("$[1].productInventory").value(2))
                .andExpect(jsonPath("$[1].storeData.storeName").value("Store 2"));

        // Verify that inventoryService.getAllInventory was called
        verify(inventoryService, times(1)).getAllInventory();
    }
    
    private InventoryDto createInventoryDto(Long inventoryId, String productName, int productInventory, String storeName) {
        InventoryDto dto = new InventoryDto();
        dto.setInventoryId(inventoryId);

        ProductDto productDto = new ProductDto();
        productDto.setProductName(productName);
        dto.setProductData(productDto);

        dto.setProductInventory(productInventory);

        StoresDto storesDto = new StoresDto();
        storesDto.setStoreName(storeName);
        dto.setStoreData(storesDto);

        return dto;
    }
    
    
    @Test
    public void testGetInventoryByStoreId() throws Exception {
        // Mock the inventoryService behavior
        List<InventoryDetailStoreIdDto> inventoryDetails = Arrays.asList(
                createInventoryDetailStoreIdDto(1L, "Product 1", "Store 1", "Order 1"),
                createInventoryDetailStoreIdDto(2L, "Product 2", "Store 2", "Order 2")
        );
        when(inventoryService.getInventoryDetailsByStoreId(anyInt())).thenReturn(inventoryDetails);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/inventory/store/{storeId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].inventoryId").value(1))
                .andExpect(jsonPath("$[0].productData.productName").value("Product 1"))
                .andExpect(jsonPath("$[0].storeData.storeName").value("Store 1"))
                .andExpect(jsonPath("$[0].orderStatus").value("Order 1"))
                .andExpect(jsonPath("$[1].inventoryId").value(2))
                .andExpect(jsonPath("$[1].productData.productName").value("Product 2"))
                .andExpect(jsonPath("$[1].storeData.storeName").value("Store 2"))
                .andExpect(jsonPath("$[1].orderStatus").value("Order 2"));

        // Verify that inventoryService.getInventoryDetailsByStoreId was called with the expected argument
        verify(inventoryService, times(1)).getInventoryDetailsByStoreId(1);
    }

    private InventoryDetailStoreIdDto createInventoryDetailStoreIdDto(
            Long inventoryId, String productName, String storeName, String orderStatus) {
        InventoryDetailStoreIdDto dto = new InventoryDetailStoreIdDto();
        dto.setInventoryId(inventoryId);

        ProductDto productDto = new ProductDto();
        productDto.setProductName(productName);
        dto.setProductData(productDto);

        StoresDto storesDto = new StoresDto();
        storesDto.setStoreName(storeName);
        dto.setStoreData(storesDto);

        dto.setOrderStatus(orderStatus);

        return dto;
    }
    
    
    @Test
    public void testGetInventoryWithShipmentsByDeliveryAddress() throws Exception {
        // Mock the inventoryService behavior
        List<InventoryWithShipmentsDto> inventoryList = Arrays.asList(
                createInventoryWithShipmentsDto(1L, "Product 1", "Store 1", 1),
                createInventoryWithShipmentsDto(2L, "Product 2", "Store 2", 2)
        );
        when(inventoryService.getInventoryWithShipmentsByDeliveryAddress(anyString())).thenReturn(inventoryList);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/inventory/shipment")
                .param("deliveryAddress", "Address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].inventoryId").value(1))
                .andExpect(jsonPath("$[0].productData.productName").value("Product 1"))
                .andExpect(jsonPath("$[0].storeData.storeName").value("Store 1"))
                .andExpect(jsonPath("$[0].productInventory").value(1))
                .andExpect(jsonPath("$[1].inventoryId").value(2))
                .andExpect(jsonPath("$[1].productData.productName").value("Product 2"))
                .andExpect(jsonPath("$[1].storeData.storeName").value("Store 2"))
                .andExpect(jsonPath("$[1].productInventory").value(2));

        // Verify that inventoryService.getInventoryWithShipmentsByDeliveryAddress was called with the expected argument
        verify(inventoryService, times(1)).getInventoryWithShipmentsByDeliveryAddress("Address");
    }

    private InventoryWithShipmentsDto createInventoryWithShipmentsDto(
            Long inventoryId, String productName, String storeName, int productInventory) {
        InventoryWithShipmentsDto dto = new InventoryWithShipmentsDto();
        dto.setInventoryId(inventoryId);

        ProductDto productDto = new ProductDto();
        productDto.setProductName(productName);
        dto.setProductData(productDto);

        StoresDto storesDto = new StoresDto();
        storesDto.setStoreName(storeName);
        dto.setStoreData(storesDto);

        dto.setProductInventory(productInventory);

        return dto;
    }



    @Test
    public void testGetInventoryByOrderId() throws Exception {
        // Mock the inventoryService behavior
        CustomInventoryDto inventoryDto = createCustomInventoryDto(1L, "Product 1", "Store 1", "Customer 1");
        when(inventoryService.getInventoryByOrderId(anyInt())).thenReturn(inventoryDto);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/inventory/{orderId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.productData.productName").value("Product 1"))
                .andExpect(jsonPath("$.storeData.storeName").value("Store 1"))
                .andExpect(jsonPath("$.customerData.fullName").value("Customer 1"));

        // Verify that inventoryService.getInventoryByOrderId was called with the expected argument
        verify(inventoryService, times(1)).getInventoryByOrderId(1);
    }

    private CustomInventoryDto createCustomInventoryDto(Long inventoryId, String productName, String storeName, String customerName) {
        CustomInventoryDto dto = new CustomInventoryDto();
        dto.setInventoryId(inventoryId);

        ProductDto productDto = new ProductDto();
        productDto.setProductName(productName);
        dto.setProductData(productDto);

        StoresDto storesDto = new StoresDto();
        storesDto.setStoreName(storeName);
        dto.setStoreData(storesDto);

        CustomersDto customersDto = new CustomersDto();
        customersDto.setFullName(customerName);
        dto.setCustomerData(customersDto);

        return dto;
    }
    
    
    
    @Test
    public void testCountProductsByShipmentStatus() throws Exception {
        // Mock the inventoryService behavior
        List<ShipmentStatusCountDTO> counts = Arrays.asList(
                createShipmentStatusCountDTO("Status 1", 2),
                createShipmentStatusCountDTO("Status 2", 3)
        );
        when(inventoryService.countProductsByShipmentStatus(anyString())).thenReturn(counts);

        // Test the endpoint
        mockMvc.perform(get("/api/v1/shipment/count")
                .param("shipmentStatus", "Status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].shipmentStatus").value("Status 1"))
                .andExpect(jsonPath("$[0].count").value(2))
                .andExpect(jsonPath("$[1].shipmentStatus").value("Status 2"))
                .andExpect(jsonPath("$[1].count").value(3));

        // Verify that inventoryService.countProductsByShipmentStatus was called with the expected argument
        verify(inventoryService, times(1)).countProductsByShipmentStatus("Status");
    }

    private ShipmentStatusCountDTO createShipmentStatusCountDTO(String shipmentStatus, Integer count) {
        ShipmentStatusCountDTO dto = new ShipmentStatusCountDTO();
        dto.setShipmentStatus(shipmentStatus);
        dto.setCount(count);
        return dto;
    }
    
    
    
//    @Test
//    public void testGetInventoryDetailsByOrderId() throws Exception {
//        // Mock the inventoryService behavior
//        List<InventoryDetailsDto> inventoryDetails = Arrays.asList(
//                createInventoryDetailsDto("Product 1", new BigDecimal("10.00"), "Store 1", "http://store1.com", "Address 1", "Status 1", new BigDecimal("5.0")),
//                createInventoryDetailsDto("Product 2", new BigDecimal("20.00"), "Store 2", "http://store2.com", "Address 2", "Status 2", new BigDecimal("10.0"))
//        );
//        when(inventoryService.getInventoryDetailsByOrderId(anyInt())).thenReturn(inventoryDetails);
//
//        // Test the endpoint
//        mockMvc.perform(get("/api/v1/inventory/details/{orderId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].productData.productName").value("Product 1"))
//                .andExpect(jsonPath("$[0].unitPrice").value(10.00))
//                .andExpect(jsonPath("$[0].storeData.storeName").value("Store 1"))
//                .andExpect(jsonPath("$[0].storeData.storeUrl").value("http://store1.com"))
//                .andExpect(jsonPath("$[0].deliveryAddress").value("Address 1"))
//                .andExpect(jsonPath("$[0].orderStatus").value("Status 1"))
//                .andExpect(jsonPath("$[0].orderTotal").value(5.0))
//                .andExpect(jsonPath("$[1].productData.productName").value("Product 2"))
//                .andExpect(jsonPath("$[1].unitPrice").value(20.00))
//                .andExpect(jsonPath("$[1].storeData.storeName").value("Store 2"))
//                .andExpect(jsonPath("$[1].storeData.storeUrl").value("http://store2.com"))
//                .andExpect(jsonPath("$[1].deliveryAddress").value("Address 2"))
//                .andExpect(jsonPath("$[1].orderStatus").value("Status 2"))
//                .andExpect(jsonPath("$[1].orderTotal").value(10.0));
//
//        // Verify that inventoryService.getInventoryDetailsByOrderId was called with the expected argument
//        verify(inventoryService, times(1)).getInventoryDetailsByOrderId(1);
//    }
//
//
//    private InventoryDetailsDto createInventoryDetailsDto(
//            String productName, BigDecimal unitPrice, String storeName, String webAddress,
//            String physicalAddress, String shipmentStatus, BigDecimal totalAmount) {
//        InventoryDetailsDto dto = new InventoryDetailsDto();
//        dto.setProductName(productName);
//        dto.setUnitPrice(unitPrice);
//        dto.setStoreName(storeName);
//        dto.setWebAddress(webAddress);
//        dto.setPhysicalAddress(physicalAddress);
//        dto.setShipmentStatus(shipmentStatus);
//        dto.setTotalAmount(totalAmount);
//        return dto;
//    }
//




    

}

