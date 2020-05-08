package ru.itis.kpfu;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.log10;

public class Task40 {

    private static BigInteger helper(BigInteger i){
        return (root(3, new BigDecimal(i))).toBigInteger();
    }

    private static BigDecimal root(Integer x, BigDecimal y){
        BigDecimal a = new BigDecimal( Math.pow(Double.parseDouble(String.valueOf(y)), 1.0/x));

        BigDecimal n = new BigDecimal(x);
        BigDecimal m = scale(y, 2);
        MathContext mc = new MathContext(2 + y.precision());

        Double t = Double.parseDouble(String.valueOf(y.ulp()))/(Double.parseDouble(String.valueOf(y)) * 2 * x);

        while(true){
            BigDecimal c = m.divide(a.pow(x-1), mc);
            c = a.subtract(c);
            MathContext p = new MathContext(c.precision());
            c = c.divide(n, p);
            a = a.subtract(c);
            if(abs(Double.parseDouble(String.valueOf(c))) / Double.parseDouble(String.valueOf(a)) < t){
                break;
            }
        }
        return a.round(new MathContext(err(t)));
    }

    private static BigDecimal scale(BigDecimal x, Integer y){
        return x.setScale(y + x.scale());
    }

    private static Integer err(Double x){
        return 1 + Integer.parseInt(String.valueOf(log10(abs(0.5 / x))));
    }

    private static BigInteger attack(List<Pair<BigInteger, BigInteger>> text){

        Pair<BigInteger, BigInteger> r0 = text.get(0);
        Pair<BigInteger, BigInteger> r1 = text.get(1);
        Pair<BigInteger, BigInteger> r2 = text.get(2);

        BigInteger k0 = r1.getValue().multiply(r2.getValue());
        BigInteger k1 = r0.getValue().multiply(r2.getValue());
        BigInteger k2 = r0.getValue().multiply(r1.getValue());

        BigInteger g0 = r0.getKey().multiply(k0).multiply(k0.modInverse(r0.getValue()));
        BigInteger g1 = r1.getKey().multiply(k1).multiply(k1.modInverse(r1.getValue()));
        BigInteger g2 = r2.getKey().multiply(k2).multiply(k2.modInverse(r2.getValue()));

        BigInteger res = (g0.add(g1).add(g2)).mod((r0.getValue().multiply(r1.getValue()).multiply(r2.getValue())));

        return helper(res);
    }

    public static void main(String[] args) {
        String str = "hello world";
        byte[] strBytes = str.getBytes();

        Pair<BigInteger, BigInteger> strEnc = new Pair<>(null,null);
        for (int i = 0; i < 3; i++) {
            RSA rsa = new RSA(1024);
            strEnc = new Pair<>(rsa.encrypt(new BigInteger(strBytes)), rsa.getPublicKey().getValue());
        }

        System.out.println(strEnc.getKey() + "   " + strEnc.getValue());
    }

}
