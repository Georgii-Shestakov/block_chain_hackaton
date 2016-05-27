package com.s1k;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static BlockChain blockChain;

    private final static String BLOCK_CHAIN_FILENAME = "./block_chain.db";

    public static void main(String[] args) {
        // write your code here
        try {
            blockChain = BlockChain.load(BLOCK_CHAIN_FILENAME);

            // first transaction
            if (blockChain.blockList.isEmpty()) {
                Transaction transaction = createMasterTransaction("master", "user1", 100);
                blockChain.add(transaction);
            }

            //add some transactions
            doTransaction("user1", "user2", 10);
            doTransaction("user1", "user3", 10);
            doTransaction("user1", "user4", 10);
            doTransaction("user4", "user5", 10);
            doTransaction("user1", "user6", 10);
            doTransaction("user1", "user7", 10);
            doTransaction("user3", "user6", 5);
            doTransaction("user3", "user7", 2);
            doTransaction("user3", "user3", 5);
            doTransaction("user4", "user1", 6);
            doTransaction("user5", "user7", 8);


            List<Transaction> user7transactions = blockChain.getPosibleTransactionsByUser("user7");
            int user7Amount = 0;
            for (Transaction transaction : user7transactions) {
                user7Amount += transaction.amount;
            }

            System.out.println(String.format("user7 amount %d", user7Amount));


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

    public static Transaction createMasterTransaction(String source, String destination, int amount) throws NoSuchAlgorithmException {
        return new Transaction(source, destination, amount);
    }

    public static List<Transaction> createTransaction(String source, String destination, int amount) throws NoSuchAlgorithmException {

        List<Transaction> sourceUserTransaction = blockChain.getPosibleTransactionsByUser(source);
        List<Transaction> newTransactions = new ArrayList<>();

        for(Transaction transaction : sourceUserTransaction) {
            if (amount == 0) //todo: may be remove this
                break;

            if (transaction.amount < amount) {
                newTransactions.add(new Transaction(source, destination, transaction.amount));
                amount = amount - transaction.amount;
            } else if (transaction.amount > amount) {
                newTransactions.add(new Transaction(source, destination, amount));
                // give change back
                newTransactions.add(new Transaction(source, source, transaction.amount - amount));
                break;
            } else {
                newTransactions.add(new Transaction(source, destination, amount));
                break;
            }
        }

        return newTransactions;
    }

    private static void doTransaction(String source, String destination, int amount) throws NoSuchAlgorithmException {
        List<Transaction> transactions = createTransaction(source, destination, amount);
        for (Transaction transaction : transactions) {
            blockChain.add(transaction);
        }
    }
}
