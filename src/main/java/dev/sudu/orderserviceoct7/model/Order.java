package dev.sudu.orderserviceoct7.model;

import dev.sudu.orderserviceoct7.exception.ProductsNotFoundException;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
@Setter
@Entity
public class Order extends BaseModel {
    private List<Product> products;
    // Order   Product
    //   1        M
    //   M        1
    private Double total;
    private Long userId;
    private OrderStatus orderStatus;

    public Order() {}

    public Order(OrderBuilder orderBuilder) throws ProductsNotFoundException {
        Assert.notNull(orderBuilder.userId, "Invalid token pls login");
        if(products.isEmpty()){
            throw new ProductsNotFoundException("Product list is empty");
        }

        this.products = orderBuilder.products;
        this.userId = orderBuilder.userId;
        this.total = orderBuilder.total;
        this.orderStatus = OrderStatus.PENDING;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private List<Product> products;
        private Double total;
        private Long userId;
        private OrderStatus orderStatus;

        public OrderBuilder setProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public OrderBuilder setTotal(Double total) {
            this.total = total;
            return this;
        }

        public OrderBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public OrderBuilder setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Order build() throws ProductsNotFoundException {
            return new Order(this);
        }
    }
}
