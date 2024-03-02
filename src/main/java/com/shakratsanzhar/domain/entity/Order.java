package com.shakratsanzhar.domain.entity;

import com.shakratsanzhar.domain.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"userInOrder", "orderProducts"})
@ToString(exclude = {"userInOrder", "orderProducts"})
@Entity
@Table(name = "orders")
@Slf4j
public class Order implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userInOrder;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer price;

    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @Builder.Default
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();
}

