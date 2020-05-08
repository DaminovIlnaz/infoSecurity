package ru.itis.kpfu;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Task40 {

    private static BigInteger helper(Integer i){
        return (root(3, new BigDecimal(i))).toBigInteger();
    }

    private static BigDecimal root(Integer x, BigDecimal y){
        BigDecimal a = new BigDecimal( Math.pow(Double.parseDouble(String.valueOf(y)), 1.0/x));

        BigDecimal n = new BigDecimal(x);

    }

    private static BigDecimal scale(BigDecimal x, Integer d){

    }

    private static Integer err(Double x){

    }

    private static BigInteger attack(List<Pair<BigInteger, BigInteger>> text){

    }

    public static void main(String[] args) {

    }

}
