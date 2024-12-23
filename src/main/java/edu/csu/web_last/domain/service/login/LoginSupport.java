package edu.csu.web_last.domain.service.login;

import edu.csu.web_last.domain.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class LoginSupport {
    private final String key = "webLastInCsuChangshaYueluasdfdsafvdsvsdfgsdfsdfgaswfsd";
//    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    private final Integer expiration = 60 * 60 * 1000;
//    private final Integer expiration = 0;
    public Boolean verify(User user, String password) {

        return password.equals(user.getPassword());
    }

    public Boolean loginCheck(String jwt) {
       try {
           Claims claims = Jwts.parser()
                   .setSigningKey(key)
                   .parseClaimsJws(jwt)
                   .getBody();
       } catch (Exception e) {
           return false;
       }
        return true;
    }

    public String remember(String username) {
        return Jwts.builder()
                .claim("username", username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String remember(String username, int i) {
        return Jwts.builder()
                .claim("username", username)
                .setExpiration(new Date(System.currentTimeMillis() + i))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
