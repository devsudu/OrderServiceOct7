package dev.sudu.orderserviceoct7.repositories;

import dev.sudu.orderserviceoct7.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
