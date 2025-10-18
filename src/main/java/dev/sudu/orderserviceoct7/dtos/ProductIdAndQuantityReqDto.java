package dev.sudu.orderserviceoct7.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductIdAndQuantityReqDto {
    private Long productId;
    private Integer quantity;
}
