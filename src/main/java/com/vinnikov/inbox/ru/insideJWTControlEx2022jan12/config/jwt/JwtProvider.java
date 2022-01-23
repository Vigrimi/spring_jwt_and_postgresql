package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider { // jwt utils происходит генерация токена

    @Value("$(jwt.secret)") // взять из апп.проп
    private String jwtSecret;

    public String generateToken(String login) {
        // срок действия токена один день
        Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        // сборка токена
        return Jwts.builder()
                .setSubject(login) // в токен записывает данные: name: "имя отправителя"
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // проверка валидности токена
    public boolean validateToken(String token) {
        boolean result = false;
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            result = true;
        } catch (Exception e) {
            System.out.println("invalid token");
        }
        return result;
    }

    // получить логин пользователя, записанный в токен
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /*public Authentication getAuthentication(String token)
    {
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(getLoginFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }*/
}
