package ru.itis.kpfu;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Task41 {

    private static class RSA_serv{
        Set<BigInteger> dectypt = new HashSet<>();
        private RSA rsa;

        public RSA_serv(RSA rse) {
            this.rsa = rsa;
        }

        public Pair<BigInteger, BigInteger> getPKey(){
            return rsa.getPublicKey();
        }

        public BigInteger decrypt(BigInteger encrypted){
            if(dectypt.contains(encrypted)){
                return rsa.decrypt(encrypted);
            }
            dectypt.add(encrypted);
            return rsa.decrypt(encrypted);
        }
    }

    public static BigInteger messRecovery(BigInteger enc, RSA_serv rsa_serv){
        Pair<BigInteger, BigInteger> e = rsa_serv.getPKey();
        Pair<BigInteger, BigInteger> n = rsa_serv.getPKey();

        BigInteger s;

        while (true){

            s = nextRandomBigInteger(BigInteger.ONE.subtract(n.getValue()));
            if((s.mod(n.getValue())).compareTo(BigInteger.ONE) == 1){
                break;
            }
        }

        BigInteger newEncrypt = (s.modPow(e.getValue(),n.getValue()).multiply(enc)).mod(n.getValue());
        BigInteger newDec = rsa_serv.decrypt(newEncrypt);
        return newDec.multiply(s.modInverse(n.getValue())).mod(n.getValue());

    }

    private static BigInteger nextRandomBigInteger(BigInteger n){
        Random r = new Random();
        BigInteger res = new BigInteger(n.bitLength(), r);
        while(res.compareTo(n) == 1){
            res = new BigInteger(n.bitLength(), r);
        }
        return res;
    }

    public static void main(String[] args) {
        String text = "Hello world";
        byte[] byteArr = text.getBytes();
        RSA rsa = new RSA(1024);
        BigInteger enc = rsa.encrypt(new BigInteger(byteArr));
        RSA_serv serv = new RSA_serv(rsa);

        byte[] recByteArr = messRecovery(enc, serv).toByteArray();
    }
}
