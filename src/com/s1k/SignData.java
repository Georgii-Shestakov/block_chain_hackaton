package com.s1k;

import java.security.*;

/**
 * Created by kelaris on 27/05/16.
 */
public class SignData {

    public static byte[] signData(byte[] data, PrivateKey key) throws Exception {
        Signature signer = Signature.getInstance("SHA1withDSA");
        signer.initSign(key);
        signer.update(data);
        return (signer.sign());
    }

    public static boolean verifySig(byte[] data, PublicKey key, byte[] sig) throws Exception {
        Signature signer = Signature.getInstance("SHA1withDSA");
        signer.initVerify(key);
        signer.update(data);
        return (signer.verify(sig));

    }

    public static KeyPair generateKeyPair(long seed) throws Exception {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
        SecureRandom rng = SecureRandom.getInstance("SHA1PRNG", "SUN");
        rng.setSeed(seed);
        keyGenerator.initialize(1024, rng);

        return (keyGenerator.generateKeyPair());
    }
}
