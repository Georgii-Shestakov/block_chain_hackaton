package com.s1k;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by kelaris on 27/05/16.
 */
public class Transaction implements Serializable {

    public int sourceTransactionBlockHeight; //this is source transaction height, but we have only 1 transaction in one block
    public String hash;
    public String sourceUser;
    public String destinationUser;
    public int amount;

    public Transaction(int sourceTransactionBlockHeight,
                       String sourceUser,
                       String destinationUser,
                       int amount) throws NoSuchAlgorithmException {
        this.sourceTransactionBlockHeight = sourceTransactionBlockHeight;
        this.sourceUser = sourceUser;
        this.destinationUser = destinationUser;
        this.amount = amount;
        writeHash();
    }

    public String generateHash() throws NoSuchAlgorithmException {
        return Sha.hash256(sourceUser+destinationUser+amount);
    }

    public void writeHash() throws NoSuchAlgorithmException {
        hash = generateHash();
    }

    public boolean validate() throws NoSuchAlgorithmException {
        return generateHash().equals(hash);
    }
}