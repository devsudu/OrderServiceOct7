package dev.sudu.orderserviceoct7.dtos;

import dev.sudu.orderserviceoct7.model.Order;
import dev.sudu.orderserviceoct7.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private List<Product> products;
    private String orderStatus;
    private Double total;
    private Long userId;

    public static OrderResponseDto from(Order order) {
        if(order == null) return null;

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setProducts(order.getProducts());
        orderResponseDto.setOrderStatus(order.getOrderStatus().toString());
        orderResponseDto.setTotal(order.getTotal());
        return orderResponseDto;
    }
}
