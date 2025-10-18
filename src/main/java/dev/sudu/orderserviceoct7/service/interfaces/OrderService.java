package dev.sudu.orderserviceoct7.service.interfaces;

import dev.sudu.orderserviceoct7.dtos.ProductIdAndQuantityReqDto;
import dev.sudu.orderserviceoct7.exception.InvalidTokenException;
import dev.sudu.orderserviceoct7.exception.ProductsNotFoundException;
import dev.sudu.orderserviceoct7.model.Order;
import dev.sudu.orderserviceoct7.model.Product;

import java.util.List;

public interface OrderService {
    Order createOrder(String token, List<ProductIdAndQuantityReqDto> productIds) throws ProductsNotFoundException, InvalidTokenException;

    Order getOrderById(String token, Long orderId) throws InvalidTokenException;

    Order cancelOrder(String token, Long orderId) throws InvalidTokenException;
}
