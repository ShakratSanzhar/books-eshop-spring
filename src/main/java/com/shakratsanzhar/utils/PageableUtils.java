package com.shakratsanzhar.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public final class PageableUtils {

    public static Pageable getUnSortedPageable(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    public static Pageable getSortedPageable(Integer page, Integer size, Sort.Direction direction, String sortProperties) {
        return PageRequest.of(page, size).withSort(direction, sortProperties);
    }
}
