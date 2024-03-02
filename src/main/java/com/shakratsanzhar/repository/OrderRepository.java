package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.Order;
import com.shakratsanzhar.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order>, CustomOrderRepository {

    @Query("select o from Order o where o.userInOrder.id=:userId order by o.createdAt desc")
    List<Order> findAllByUserId(Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Order o " +
            "set o.status = :status," +
            "o.closedAt =:closedAt " +
            "where o.id = :orderId")
    void confirmPayment(Long orderId, OrderStatus status, LocalDateTime closedAt);
}
