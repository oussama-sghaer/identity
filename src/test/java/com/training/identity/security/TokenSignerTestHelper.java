package com.training.identity.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;

public class TokenSignerTestHelper {
    private final RSAPrivateKey privateKey;
    public TokenSignerTestHelper(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    public String generateSignedToken(String subject,  String issuer,  String audience, Instant expiration, String scope) throws JOSEException {
        var claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .audience(audience)
                .expirationTime(Date.from(expiration))
                .claim("scope", scope)
                .build();
        var signedToken = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256),claims);
        signedToken.sign(new RSASSASigner(privateKey));
        return signedToken.serialize();

    }
}
