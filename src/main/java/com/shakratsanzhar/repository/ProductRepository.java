package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    List<Product> findAllByNameContainingIgnoreCase(String name);

    Optional<Product> findProductByNameEquals(String name);

    @Query(nativeQuery = true, value = "SELECT p.remaining_amount FROM Product p WHERE p.id=:productId")
    Integer findQuantityOfProduct(Long productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Product p " +
            "set p.remainingAmount = :quantity " +
            "where p.id=:productId")
    void updateProductQuantity(Long productId, Integer quantity);
}
