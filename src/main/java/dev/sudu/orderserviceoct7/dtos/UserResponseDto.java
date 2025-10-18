package dev.sudu.orderserviceoct7.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private String token;
    private String email;
    private Long expiryAt;
    private Boolean isLoggedIn;
}


