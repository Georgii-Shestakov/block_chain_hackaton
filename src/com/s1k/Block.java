package com.s1k;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kelaris on 27/05/16.
 */
public class Block implements Serializable {

    public int height;
    public String hash;
    // for testing we just create only one transaction in block
    // later it can be changed to transactions list
    public Transaction transaction;

    public Block(Transaction transaction, String lastBlockHash, int height) throws NoSuchAlgorithmException {
        this.transaction = transaction;
        writeHash(lastBlockHash);
        this.height = height;
    }

    public String generateHash(String prevBlockHash) throws NoSuchAlgorithmException {
        return Main.getHash(prevBlockHash + transaction.hash);
    }

    private void writeHash(String prevBlockHash) throws NoSuchAlgorithmException {
        hash = generateHash(prevBlockHash);
    }
}

