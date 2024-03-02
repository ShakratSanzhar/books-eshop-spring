package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.domain.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderCreateEditDto {

    Long userId;
    OrderStatus status;
    Integer price;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime closedAt;
}
