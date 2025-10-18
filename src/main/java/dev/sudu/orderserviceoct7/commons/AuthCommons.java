package dev.sudu.orderserviceoct7.commons;

import dev.sudu.orderserviceoct7.dtos.UserResponseDto;
import dev.sudu.orderserviceoct7.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthCommons {
    private final RestClient restClient;
    @Value("${user.service.baseUrl}")
    private String userServiceBaseUrl;

    @Autowired
    public AuthCommons(RestClient.Builder loadBalancedRestClientBuilder) {
        this.restClient = loadBalancedRestClientBuilder.build();
    }

    public UserResponseDto validateToken(String token) {
        ResponseEntity<UserResponseDto> responseEntity = restClient.patch().uri("http://USERSERVICEOCT3/users/".concat(token)).retrieve().toEntity(UserResponseDto.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        return null;
    }

    public List<Product> getProducts(Long[] productIds) {
        ResponseEntity<Product[]> responseEntity = restClient.post().uri("http://localhost:9000/products").body(productIds).retrieve().toEntity(Product[].class);

        return Arrays.stream(responseEntity.getBody()).toList();
    }
}
