package com.s1k;

/**
 * Created by kelaris on 27/05/16.
 */
public class Block {
    public int id;
    public String hash;
    public Transaction transaction;

    public void generateHash() {
        hash = "";
    }
}

