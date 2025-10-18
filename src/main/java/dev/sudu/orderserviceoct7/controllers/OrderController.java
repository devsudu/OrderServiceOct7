package dev.sudu.orderserviceoct7.controllers;

import dev.sudu.orderserviceoct7.dtos.OrderResponseDto;
import dev.sudu.orderserviceoct7.dtos.ProductIdAndQuantityReqDto;
import dev.sudu.orderserviceoct7.exception.InvalidTokenException;
import dev.sudu.orderserviceoct7.exception.ProductsNotFoundException;
import dev.sudu.orderserviceoct7.model.Order;
import dev.sudu.orderserviceoct7.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestHeader HttpHeaders headers, List<ProductIdAndQuantityReqDto> productIds) throws ProductsNotFoundException, InvalidTokenException {
        String token = headers.getFirst("token");
        Order order = orderService.createOrder(token, productIds);
        return new ResponseEntity<>(
                OrderResponseDto.from(order),
                null,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<OrderResponseDto> getOrderById(@RequestHeader HttpHeaders headers, Long orderId) throws InvalidTokenException {
        String token = headers.getFirst("token");
        Order order = orderService.getOrderById(token, orderId);
        return new ResponseEntity<>(
                OrderResponseDto.from(order),
                null,
                HttpStatus.OK
        );
    }

    @PatchMapping
    public ResponseEntity<OrderResponseDto> cancelOrder(@RequestHeader HttpHeaders headers, Long orderId) throws InvalidTokenException {
        String token = headers.getFirst("token");
        Order order = orderService.cancelOrder(token, orderId);
        return new ResponseEntity<>(
                OrderResponseDto.from(order),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/demo")
    ResponseEntity<Boolean> demoApi() {
        return new ResponseEntity<>(
                true,
                null,
                HttpStatus.OK
        );
    }
}
