package com.s1k;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static java.awt.SystemColor.text;

/**
 * Created by kelaris on 27/05/16.
 */
public class BlockChain {

    public List<Block> blockList;

    public boolean add(Block block) {
        Block lastBlock = blockList.get(blockList.size() - 1);

        return true;
    }

    private boolean validate() {

        return true;
    }
}
