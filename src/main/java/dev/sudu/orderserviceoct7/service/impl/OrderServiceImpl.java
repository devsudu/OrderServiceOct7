package dev.sudu.orderserviceoct7.service.impl;

import dev.sudu.orderserviceoct7.commons.AuthCommons;
import dev.sudu.orderserviceoct7.dtos.ProductIdAndQuantityReqDto;
import dev.sudu.orderserviceoct7.dtos.UserResponseDto;
import dev.sudu.orderserviceoct7.exception.InvalidTokenException;
import dev.sudu.orderserviceoct7.exception.ProductsNotFoundException;
import dev.sudu.orderserviceoct7.model.Order;
import dev.sudu.orderserviceoct7.model.OrderStatus;
import dev.sudu.orderserviceoct7.model.Product;
import dev.sudu.orderserviceoct7.repositories.OrderRepository;
import dev.sudu.orderserviceoct7.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private AuthCommons authCommons;
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(String token, List<ProductIdAndQuantityReqDto> productIds) throws ProductsNotFoundException, InvalidTokenException {
        if(productIds.isEmpty()){
            throw new ProductsNotFoundException("Products not present to process the order");
        }
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();
        if(userId == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }

//        Long[] productIdList = productIds.stream().mapToLong(ProductIdAndQuantityReqDto::getProductId);
//        long[] productIdList = productIds.stream()
//                .mapToLong(ProductIdAndQuantityReqDto::getProductId)
//                .toArray();

        Long[] productIdList = productIds.stream()
                .map(ProductIdAndQuantityReqDto::getProductId)
                .toArray(Long[]::new);

        List<Product> productList = authCommons.getProducts(productIdList);

        if(productList.isEmpty()) {
            throw new IllegalArgumentException("Product ids is empty");
        }

        Double orderTotal = productList.stream()
                .reduce(0.0,
                        (currentTotal, nextProduct) -> {
                            if (nextProduct.getSellingPrice() >= 0) {
                                return currentTotal + nextProduct.getSellingPrice();
                            }
                            return currentTotal + nextProduct.getMrp();
                        },
                        Double::sum);

//        Double orderTotal = productList.stream().mapToDouble(Product::getSellingPrice).sum();

        Order orderToSave = Order.builder()
                .setUserId(userId)
                .setTotal(orderTotal)
                .setProducts(productList)
                .setOrderStatus(OrderStatus.PENDING)
                .build();
        return orderRepository.save(orderToSave);

    }

    @Override
    public Order getOrderById(String token, Long orderId) throws InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();
        if(userId == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }

        Optional<Order> fetchedOrder = orderRepository.findById(orderId);
        if(fetchedOrder.isEmpty()){
            throw new IllegalArgumentException("Order not found");
        }
        return fetchedOrder.get();
    }

    @Override
    public Order cancelOrder(String token, Long orderId) throws InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();
        if(userId == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }

        Order fetchedOrder = getOrderById(orderId);
        fetchedOrder.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(fetchedOrder);
    }

    public UserResponseDto verifyToken(String token) throws InvalidTokenException {
        return authCommons.validateToken(token);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }
}
