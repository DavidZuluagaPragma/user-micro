
































package com.pragma.usuario.micro.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.pragma.usuario.micro.aplication.dto.UserLoginDto;
import com.pragma.usuario.micro.domain.model.usuario.gateway.UserGateway;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    @Value(value = "${aws.cognito.identityPoolUrl}")
    private String identityPoolUrl;

    @Value(value = "${aws.cognito.region}")
    private String region;

    @Value(value = "${aws.cognito.issuer}")
    private String issuer;

    private static final String USERNAME = "email";

    @Autowired
    UserGateway userGateway;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(String.valueOf(getIdUser(userDetails.getUsername())))
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(getKey(secret))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            log.error("token expired");
        } catch (UnsupportedJwtException e) {
            log.error("token unsupported");
        } catch (MalformedJwtException e) {
            log.error("token malformed");
        } catch (SignatureException e) {
            log.error("bad signature");
        } catch (IllegalArgumentException e) {
            log.error("illegal args");
        }
        return false;
    }

    private Key getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    private int getIdUser(String correo){
        return userGateway.findByUsername(correo)
                .getId();
    }

    public DecodedJWT getDecodedJwt(String token) {
        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring("Bearer ".length()) : token;
        RSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider(region, identityPoolUrl);
        Algorithm algorithm = Algorithm.RSA256(keyProvider);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        verifier.verify(tokenWithoutBearer);
        return verifier.verify(tokenWithoutBearer);
    }


    public String getUserNameFromToken(String token) {
        DecodedJWT jwt = getDecodedJwt(token);
        String userName = jwt.getClaim(USERNAME).toString();
        return userName.replace("\"","");
    }

    public boolean validateToken(String token) {
        try {
            getDecodedJwt(token);
            return true;
        } catch (JWTVerificationException exception) {
            log.error("Validate token failed: " + exception.getMessage());
        }
        return false;
    }

}
