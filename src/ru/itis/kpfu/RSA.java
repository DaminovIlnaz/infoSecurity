package ru.itis.kpfu;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.Map;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

import static java.math.BigInteger.ONE;

public class RSA {

    private final static SecureRandom random = new SecureRandom();
    private static BigInteger x = BigInteger.ZERO;
    private static BigInteger y = BigInteger.ZERO;
    private static BigInteger z = BigInteger.ZERO;
    private static BigInteger SYMBIT = BigInteger.valueOf(3);

    private Pair<BigInteger, BigInteger> publicKey;

    public Pair<BigInteger, BigInteger> getPublicKey() {
        return publicKey;
    }

    public RSA(int keySize) {

        while (gcd(SYMBIT,x) != BigInteger.ONE) {
            BigInteger p = BigInteger.probablePrime(keySize/2, random);
            BigInteger q = BigInteger.probablePrime(keySize/2, random);
            x = lcm(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE));
            y = p.multiply(q);
        }

        z = SYMBIT.modInverse(x);
        publicKey = new Pair<>(SYMBIT, y);
    }

    public BigInteger encrypt(BigInteger i){
        return i.modPow(SYMBIT, y);
    }

    private BigInteger decrypt(BigInteger i){
        return  i.modPow(z, y);
    }

    private static BigInteger gcd(BigInteger n, BigInteger m) {
        return n.gcd(m);
    }

    private static BigInteger lcm(BigInteger n, BigInteger m){
        return (n.divide(gcd(n,m)).multiply(m));
    }

    public static void main(String[] args) {
        RSA rsa = new RSA(512);
        String text = "Hello World";
        byte[] textBytes = text.getBytes();

        BigInteger encrypted = rsa.encrypt(new BigInteger(textBytes));
        BigInteger decrypted = rsa.decrypt(encrypted);

        System.out.println(encrypted);
        System.out.println(decrypted);
    }

}
