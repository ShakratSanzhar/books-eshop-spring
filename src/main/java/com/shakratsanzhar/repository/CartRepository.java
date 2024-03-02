package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.Cart;
import com.shakratsanzhar.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, QuerydslPredicateExecutor<Cart>, CustomCartRepository {

    Optional<Cart> findCartByUserInCart(User user);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update CartProduct cp " +
            "set cp.quantity = :quantity " +
            "where cp.id=:cartProductId")
    void updateCartProductQuantity(Long cartProductId, Integer quantity);
}
