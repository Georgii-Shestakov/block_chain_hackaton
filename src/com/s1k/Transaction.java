package com.s1k;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kelaris on 27/05/16.
 */
public class Transaction implements Serializable {

    public int id;
    public int sourceTransactionId; //this is source transaction id, but we have only 1 transaction in one block
    public String hash;
    public String sourceUser;
    public String destinationUser;
    public float amount;

    public Transaction(String sourceUser, String destinationUser, float amount) throws NoSuchAlgorithmException {
        this.sourceUser = sourceUser;
        this.destinationUser = destinationUser;
        this.amount = amount;
        generateHash();
    }

    public String generateHash() throws NoSuchAlgorithmException {
        return Main.getHash(sourceUser+destinationUser+amount);
    }

    public void writeHash() throws NoSuchAlgorithmException {
        hash = generateHash();
    }

    public boolean validate() throws NoSuchAlgorithmException {
        return generateHash().equals(hash);
    }
}