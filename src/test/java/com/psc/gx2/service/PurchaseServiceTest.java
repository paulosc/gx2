package com.psc.gx2.service;

import com.psc.gx2.api.ClientAPI;
import com.psc.gx2.api.ProductAPI;
import com.psc.gx2.dto.PurchaseDetailResponse;
import com.psc.gx2.dto.RecommendationResponse;
import com.psc.gx2.model.Client;
import com.psc.gx2.model.Product;
import com.psc.gx2.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private ClientAPI clientAPI;

    @Mock
    private ProductAPI productAPI;

    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPurchasesSortedByValue_ShouldReturnSortedPurchases() {
        // Given
        List<Client> clients = mockClients();
        List<Product> products = mockProducts();

        when(clientAPI.getClients()).thenReturn(clients);
        when(productAPI.getProducts()).thenReturn(products);

        // When
        List<PurchaseDetailResponse> result = purchaseService.getAllPurchasesSortedByValue();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.get(0).getTotalValue() < result.get(1).getTotalValue());
    }

    @Test
    void getBiggestPurchaseOfTheYear_ShouldReturnBiggestPurchase() {
        // Given
        List<Client> clients = mockClients();
        List<Product> products = mockProducts();

        when(clientAPI.getClients()).thenReturn(clients);
        when(productAPI.getProducts()).thenReturn(products);

        // When
        PurchaseDetailResponse result = purchaseService.getBiggestPurchaseOfTheYear(2023);

        // Then
        assertNotNull(result);
        assertEquals(450.0, result.getTotalValue());
    }

    @Test
    void getBiggestPurchaseOfTheYear_ShouldThrowNotFoundException() {
        // Given
        List<Client> clients = mockClients();
        List<Product> products = mockProducts();

        when(clientAPI.getClients()).thenReturn(clients);
        when(productAPI.getProducts()).thenReturn(products);

        // When & Then
        assertThrows(ResponseStatusException.class, () -> purchaseService.getBiggestPurchaseOfTheYear(2025));
    }

    @Test
    void getLoyalCustomers_ShouldReturnTop3LoyalCustomers() {
        // Given
        List<Client> clients = mockClients();
        List<Product> products = mockProducts();

        when(clientAPI.getClients()).thenReturn(clients);
        when(productAPI.getProducts()).thenReturn(products);

        // When
        List<PurchaseDetailResponse> result = purchaseService.getLoyalCustomers();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.get(0).getTotalValue() >= result.get(1).getTotalValue());
    }

    @Test
    void getCustomerRecommendationByType_ShouldReturnRecommendations() {
        // Given
        List<Client> clients = mockClients();
        List<Product> products = mockProducts();

        when(clientAPI.getClients()).thenReturn(clients);
        when(productAPI.getProducts()).thenReturn(products);

        // When
        List<RecommendationResponse> result = purchaseService.getCustomerRecommendationByType();

        // Then
        assertEquals(2, result.size());
        assertEquals("Red", result.get(0).getType());
    }

    // Mock data
    private List<Client> mockClients() {
        Client client1 = new Client();
        client1.setName("John Doe");
        client1.setCpf("123456789");
        client1.setPurchases(Arrays.asList(
                Purchase.builder().code("P1").quantity(2).build(),
                Purchase.builder().code("P2").quantity(2).build()
        ));

        Client client2 = new Client();
        client2.setName("Jane Doe");
        client2.setCpf("987654321");
        client2.setPurchases(Collections.singletonList(
                Purchase.builder().code("P3").quantity(3).build()
        ));

        return Arrays.asList(client1, client2);
    }

    private List<Product> mockProducts() {
        Product product1 = new Product();
        product1.setCode("P1");
        product1.setPrice(50.0);
        product1.setPurchaseYear(2023);
        product1.setWineType("Red");
        product1.setVintage("2020");

        Product product2 = new Product();
        product2.setCode("P2");
        product2.setPrice(100.0);
        product2.setPurchaseYear(2022);
        product2.setWineType("White");
        product2.setVintage("2019");

        Product product3 = new Product();
        product3.setCode("P3");
        product3.setPrice(150.0);
        product3.setPurchaseYear(2023);
        product3.setWineType("Red");
        product3.setVintage("2021");

        return Arrays.asList(product1, product2, product3);
    }
}
