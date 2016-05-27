package com.s1k;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static java.awt.SystemColor.text;

/**
 * Created by kelaris on 27/05/16.
 */
public class BlockChain implements Serializable {

    public static final String ZERO_BLOCK_HASH = "Hi there, I am the very first block";

    public List<Block> blockList;

    public boolean add(Block block) throws NoSuchAlgorithmException {

        if (validateBlock(block)) {
            blockList.add(block);
            return true;
        } else {
            return false;
        }
    }

    public String getLastBlockHash() {
        return blockList.isEmpty() ? ZERO_BLOCK_HASH : blockList.get(blockList.size() - 1).hash;
    }

    public int getLastBlockId() {
        return blockList.size() - 1;
    }

    private boolean validateBlock(Block block) throws NoSuchAlgorithmException {
        String realHash = block.generateHash(getLastBlockHash());

        return block.hash.equals(realHash);
    }

    public boolean validate() throws NoSuchAlgorithmException {
        for (int i = blockList.size() - 1; i <= 0; i--) {
            if (!validateBlock(blockList.get(i))) {
                return false;
            }
        }

        return true;
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
        }

        return blockChain;
    }
}
