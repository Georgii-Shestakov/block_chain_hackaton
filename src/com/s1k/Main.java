package com.s1k;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        // write your code here


    }

    public static String getHash(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return new String(hash);
    }

    public Transaction createNewTransaction(String source, String destination, float amount) throws NoSuchAlgorithmException {
        return new Transaction(source, destination, amount);
    }

    public Block createNewBlock(Transaction transaction) {
        //return new Block(transaction, BlockChain.getLastBlockHash(), BlockChain.getLastBlockId() + 1);
        return null;
    }
}
