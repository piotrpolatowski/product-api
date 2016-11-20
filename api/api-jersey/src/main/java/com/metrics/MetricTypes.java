package com.metrics;

import lombok.Getter;

public enum MetricTypes {
    API_PRODUCT_LIST_REQUESTS,
    API_PRODUCT_ERRORS;

    @Getter
    private final String metricName = name().toLowerCase().replace("_", ".");

}
