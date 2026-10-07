package com.training.identity.security;

import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SignedTokenTest {
    @Autowired
    JwtDecoder jwtDecoder;
    @Autowired
    LocalKeyProvider localKeyProvider;
    @Test
    public void should_generate_and_verify_signed_token(@Value("${app.jwt.issuer}") String issuer,@Value("${app.jwt.audience}") String audience ) throws JOSEException, MalformedURLException {
        var privateKey = localKeyProvider.privateKey();
        var tokenSigner = new TokenSignerTestHelper(privateKey);
        var expiration = Instant.now().plusSeconds(3600);
        String signedToken = tokenSigner.generateSignedToken("subject",issuer, audience, expiration, "scope");
        var decodedToken = jwtDecoder.decode(signedToken);

        assertEquals("subject", decodedToken.getSubject());
       
        assertEquals( "https://identity-demo.com", issuer != null ? issuer : "");

        assertEquals("audience", Objects.requireNonNull(decodedToken.getAudience()).getFirst());
        assertEquals("scope", decodedToken.getClaim("scope"));

    }
    @Test
    public void should_not_validate_signed_token_with_wrong_audience(@Value("${app.jwt.issuer}") String issuer ) throws JOSEException, MalformedURLException {
        var privateKey = localKeyProvider.privateKey();
        var tokenSigner = new TokenSignerTestHelper(privateKey);
        var audience = "identity";
        var expiration = Instant.now().plusSeconds(3600);
        String signedToken = tokenSigner.generateSignedToken("subject",issuer, audience, expiration, "scope");

        assertThrows(JwtValidationException.class,()-> jwtDecoder.decode(signedToken));
    }
    @Test
    public void should_not_validate_signed_token_with_wrong_issuer(@Value("${app.jwt.audience}") String audience ) throws JOSEException, MalformedURLException {
        var privateKey = localKeyProvider.privateKey();
        var tokenSigner = new TokenSignerTestHelper(privateKey);
        var issuer = "https://identity-demo.net";
        var expiration = Instant.now().plusSeconds(3600);
        String signedToken = tokenSigner.generateSignedToken("subject",issuer, audience, expiration, "scope");


        assertThrows(JwtValidationException.class,()-> jwtDecoder.decode(signedToken));
    }
    @Test
    public void should_not_validate_signed_token_with_expired_token(@Value("${app.jwt.issuer}") String issuer,@Value("${app.jwt.audience}") String audience )  throws JOSEException, MalformedURLException {
        var privateKey = localKeyProvider.privateKey();
        var tokenSigner = new TokenSignerTestHelper(privateKey);
        var expiration = Instant.now().minusSeconds(60);
        String signedToken = tokenSigner.generateSignedToken("subject",issuer, audience, expiration, "scope");
        

        assertThrows(JwtValidationException.class,()-> jwtDecoder.decode(signedToken));
    }
}
