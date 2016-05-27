package com.s1k;

import java.security.NoSuchAlgorithmException;

/**
 * Created by kelaris on 27/05/16.
 */
public class Block {
    public int id;
    public String hash;
    public Transaction transaction;
    public String generateHash(String prevBlockHash) throws NoSuchAlgorithmException {
        return Main.getHash(prevBlockHash + transaction.hash);
    }
    private void writeHash(String prevBlockHash) throws NoSuchAlgorithmException {
        hash = generateHash(prevBlockHash);
    }
}

