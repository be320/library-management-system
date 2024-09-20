package com.system.library.service;


import com.system.library.exception.UserNotAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {


    @Value("${jwt.validity}")
    private long tokenValidityTimeInSeconds = 0;



    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("OWpzZnN0bngwbnBscGc3cHRpeTJpbjd4ajh5cnc1YzV4dm0xMTdycjc5ZDFkdXJrMnVnZnByMTllazFjeHA2NngxbjlqZDMxMzhnZmNxbnowcWd6a2ZoZXEwb3FsZTJ6ZHlybmtiZnlkM3lwZjhmbG1wODZubGs1bnR4cHdra2o=");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> claims, String subject) {

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityTimeInSeconds * 1000))
                .signWith(getSigningKey()).compact();
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public void checkUserAuthorization(String userRequestingData){
        Claims claims = getAllClaimsFromToken(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        String userLoggedIn = claims.getSubject();
        if (!userRequestingData.equals(userLoggedIn))
            throw new UserNotAuthorizedException();
    }

    public List<String> extractRoles(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("roles", List.class);  // 'roles' is assumed to be a claim in the token
    }

    public String getUsernameFromToken(){
        Claims claims = getAllClaimsFromToken(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return claims.getSubject();
    }

}
