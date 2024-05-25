package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    public static MockWebServer mockWebServer;
    @InjectMocks
    private ProductService productService;

    @Test
    void testGetProductByIdentifier() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPreco(1000F);
        productDTO.setProductIdentifier("prod-identifier");

        ObjectMapper objectMapper = new ObjectMapper();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(productDTO))
                .addHeader("Content-Type", "application/json"));
        productDTO = productService.getProductByIdentifier("prod-identifier");

        Assertions.assertEquals(1000F, productDTO.getPreco());
        Assertions.assertEquals("prod-identifier", productDTO.getProductIdentifier());
    }

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseURL = String.format("http://localhost:%s", mockWebServer.getPort());
        ReflectionTestUtils.setField(productService, "productApiURL", baseURL);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}
