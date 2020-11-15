package com.roy.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.security.PrivateKey;

/**
 * description：
 * author：dingyawu
 * date：created in 19:46 2020/11/15
 * history:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private Long userId;
    private String name;
}
