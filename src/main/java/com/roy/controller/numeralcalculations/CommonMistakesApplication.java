package com.roy.controller.numeralcalculations;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        System.out.println("right");
        right();
    }



    private static void right() {
        BigDecimal num1 = new BigDecimal("3.35");
        BigDecimal num2 = num1.setScale(1, BigDecimal.ROUND_DOWN);
        System.out.println(num2);
        BigDecimal num3 = num1.setScale(1, BigDecimal.ROUND_HALF_UP);
        System.out.println(num3);
    }
}

