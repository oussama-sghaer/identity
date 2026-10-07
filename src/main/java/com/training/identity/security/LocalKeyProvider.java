package com.training.identity.security;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class LocalKeyProvider {
    private final KeyPair keyPair;
    public LocalKeyProvider() throws NoSuchAlgorithmException {
        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        keyPair = generator.generateKeyPair();
    }
    public RSAPublicKey publicKey() {
        return (RSAPublicKey) keyPair.getPublic();
    }
    public RSAPrivateKey privateKey() {
        return (RSAPrivateKey) keyPair.getPrivate();
    }
}
