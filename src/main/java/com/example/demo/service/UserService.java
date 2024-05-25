package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private String userApiURL = "http://localhost:8080";

    public UserDTO getUserByCpfAndKey(String cpf, String key){
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(userApiURL)
                    .build();

            Mono<UserDTO> user = webClient.get()
                    .uri("/user/" + cpf + "/cpf?key=" + key)
                    .retrieve()
                    .bodyToMono(UserDTO.class);

            return user.block();
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }
}
