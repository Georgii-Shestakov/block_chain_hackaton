package com.s1k;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.text;

/**
 * Created by kelaris on 27/05/16.
 */
public class BlockChain implements Serializable {

    public static final String ZERO_BLOCK_HASH = "Hi there, I am the very first block";

    public List<Block> blockList;

    private boolean validateBlock(Block block) throws NoSuchAlgorithmException {
        String realHash = block.generateHash(getLastBlockHash());

        return block.hash.equals(realHash);
    }

    public boolean add(Transaction transaction) throws NoSuchAlgorithmException {

        Block block = new Block(transaction, getLastBlockHash(), blockList.size());

        if (validateBlock(block)) {
            blockList.add(block);
            System.out.println(String.format("Adding new block. source: %s destination: %s amount %d",
                    transaction.sourceUser, transaction.destinationUser, transaction.amount));
            return true;
        } else {
            System.out.println("Block validation failed");
            return false;
        }
    }

    public String getLastBlockHash() {
        return blockList.isEmpty() ? ZERO_BLOCK_HASH : blockList.get(blockList.size() - 1).hash;
    }

    public boolean validate() throws NoSuchAlgorithmException {
        for (int i = blockList.size() - 1; i <= 0; i--) {
            if (!validateBlock(blockList.get(i))) {
                return false;
            }
        }

        return true;
    }

    public HashMap<Integer, Transaction> getPossibleTransactionsByUser(String user) throws Exception {

        HashMap<Integer, Transaction> userDestinationTransactions = new HashMap<>();
        List<Transaction> userSourceTransactions = new ArrayList<>();

        for (Block block : blockList) {
            if (block.transaction.destinationUser.equals(user)) {
                userDestinationTransactions.put(block.height, block.transaction);
            }
            if (block.transaction.sourceUser.equals(user)) {
                userSourceTransactions.add(block.transaction);
            }
        }

        //remove all sended transactions
        for (Transaction transaction : userSourceTransactions) {
            if (userDestinationTransactions.containsKey(transaction.sourceTransactionBlockHeight)) {
                userDestinationTransactions.remove(transaction.sourceTransactionBlockHeight);
            }
        }

        for (HashMap.Entry transactionEntry : userDestinationTransactions.entrySet()) {
            String signingDataForVerify = ((Transaction) transactionEntry.getValue()).sourceUser + ((Transaction) transactionEntry.getValue()).amount;
            if (!SignData.verifySig(Sha.hash256byteArray(signingDataForVerify),((Transaction)transactionEntry.getKey()).publicKey, Sha.hash256byteArray(((Transaction)transactionEntry.getKey()).signature))) {
                userDestinationTransactions.remove(transactionEntry.getKey());
            }
        }

        return userDestinationTransactions;
    }


    public void save(String filename) {
        ObjectOutput objectOutput;
        try {
            File file = new File(filename);
            objectOutput = new ObjectOutputStream(new FileOutputStream(file));
            objectOutput.writeObject(this);
            objectOutput.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static BlockChain load(String filename) throws NoSuchAlgorithmException {
        ObjectInput objectInput;
        BlockChain blockChain = null;
        try {
            File file = new File(filename);
            if (file.exists()) {
                objectInput = new ObjectInputStream(new FileInputStream(file));

                blockChain = (BlockChain) objectInput.readObject();
                objectInput.close();
            }
        } catch (Exception e) {e.printStackTrace();}

        if (blockChain == null || ! blockChain.validate()) {
            blockChain = new BlockChain();
            blockChain.blockList = new ArrayList<>();
        }

        return blockChain;
    }
}
