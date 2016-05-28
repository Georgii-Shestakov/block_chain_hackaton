package com.s1k;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

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


            printUserAmount("user1");
            printUserAmount("user2");
            printUserAmount("user3");
            printUserAmount("user4");
            printUserAmount("user5");
            printUserAmount("user6");
            printUserAmount("user7");


        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        

        blockChain.save(BLOCK_CHAIN_FILENAME);
    }

    private static void printUserAmount(String user) throws Exception {
        HashMap<Integer, Transaction>  transactions = blockChain.getPossibleTransactionsByUser(user);
        int userAmount = 0;
        for (Transaction transaction : transactions.values()) {
            userAmount += transaction.amount;
        }
        System.out.println(String.format("%s amount: %d",user, userAmount));
    }

    public static Transaction createMasterTransaction(String source, String destination, int amount, PrivateKey privateKey) throws Exception {
        return new Transaction(0, source, destination, amount, privateKey);
    }

    public static List<Transaction> createTransaction(String source, String destination, int amount, PrivateKey privateKey) throws Exception {

        HashMap<Integer, Transaction> sourceUserTransaction = blockChain.getPossibleTransactionsByUser(source);
        List<Transaction> newTransactions = new ArrayList<>();

        for(HashMap.Entry transactionEntry : sourceUserTransaction.entrySet()) {
            Transaction transaction = (Transaction) transactionEntry.getValue();
            Integer blockHeight = (Integer) transactionEntry.getKey();

            if (transaction.amount < amount) {
                newTransactions.add(new Transaction(blockHeight, source, destination, transaction.amount, privateKey));
                amount = amount - transaction.amount;
            } else if (transaction.amount > amount) {
                newTransactions.add(new Transaction(blockHeight, source, destination, amount, privateKey));
                // give change back
                newTransactions.add(new Transaction(blockHeight, source, source, transaction.amount - amount, privateKey));
                break;
            } else {
                newTransactions.add(new Transaction(blockHeight, source, destination, amount, privateKey));
                break;
            }

        }

        return newTransactions;
    }

    private static void doTransaction(String source, String destination, int amount) throws NoSuchAlgorithmException {
        List<Transaction> transactions = createTransaction(source, destination, amount, );
        for (Transaction transaction : transactions) {
            blockChain.add(transaction);
        }
    }
}
