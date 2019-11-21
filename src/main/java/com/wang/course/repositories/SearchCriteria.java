package com.wang.course.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Common search criteria
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private String value;
}
