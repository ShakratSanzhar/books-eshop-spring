package com.shakratsanzhar.domain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {

    NOT_PAID,
    PAID;

    public static Optional<OrderStatus> find(String status) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(status))
                .findFirst();
    }
}
