package com.training.identity.config.security;

import com.training.identity.security.LocalKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtDecoderConfig {
    @Bean
    public JwtDecoder jwtDecoder(LocalKeyProvider keyProvider, @Value("${app.jwt.issuer}") String issuer, @Value("${app.jwt.audience}") String audience) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(keyProvider.publicKey()).build();
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                new JwtIssuerValidator(issuer),
                new JwtAudienceValidator(audience),
                new JwtTimestampValidator()
        ));
        return decoder;
    }
}
