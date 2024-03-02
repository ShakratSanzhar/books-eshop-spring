package com.shakratsanzhar.utils;

public interface PredicateBuilder<R, T> {

    R build(T requestFilter);
}
