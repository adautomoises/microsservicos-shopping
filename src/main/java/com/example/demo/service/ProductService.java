package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private String productApiURL = "http://localhost:8081";

    public ProductDTO getProductByIdentifier(String productIdentifier){
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(productApiURL)
                    .build();

            Mono<ProductDTO> product = webClient.get()
                    .uri("/produto/" + productIdentifier)
                    .retrieve()
                    .bodyToMono(ProductDTO.class);

            return product.block();
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

}
