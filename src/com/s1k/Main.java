package com.s1k;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    private static BlockChain blockChain;

    private final static String BLOCK_CHAIN_FILENAME = "./block_chain.db";

    private static String[] users = {"user1", "user2", "user3"};
    private static float[] balance = { 10.0f, 15.5f, 23.1f };


    public static void main(String[] args) {
        // write your code here
        try {
            blockChain = BlockChain.load(BLOCK_CHAIN_FILENAME);

            //Transaction transaction = createNewTransaction(users[new Random().nextInt(2)], users[new Random().nextInt(2)], new Random().nextInt(10));

            // first transaction
            if (blockChain.blockList.isEmpty()) {
                Transaction transaction = createNewTransaction("master", "user1", 100);
                blockChain.add(createNewBlock(transaction));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        

        blockChain.save(BLOCK_CHAIN_FILENAME);
    }

    public static String getHash(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return new String(hash);
    }

    public static Transaction createNewTransaction(String source, String destination, float amount) throws NoSuchAlgorithmException {
        return new Transaction(source, destination, amount);
    }

    public static Block createNewBlock(Transaction transaction) throws NoSuchAlgorithmException {
        return new Block(transaction, blockChain.getLastBlockHash(), blockChain.getLastBlockId() + 1);
    }

    /*public static List<Transaction> getTransactionsByUser(String user) {

    }*/
}
