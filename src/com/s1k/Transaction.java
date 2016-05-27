package com.s1k;

/**
 * Created by kelaris on 27/05/16.
 */
public class Transaction {

    public int sourceBlockId; //this is source transaction id, but we have only 1 transaction in one block
    public String hash;

    public String sourceUser;
    public String destinationUser;
    public float amount;

    public void generateHash() {
        hash = "";
    }
}
