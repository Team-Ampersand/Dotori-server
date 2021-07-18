package com.server.Dotori.security.jwt;

import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.security.authentication.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secretKey}")
    private String secretKey;

//    public final static long TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24;  //하루를 accessToken 만료 기간으로 잡는다
//    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24 * 210; //7개월을 refreshToken 만료 기간으로 잡는다.

    // *** Test Code *** //
    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60;  // 1분을 accessToken 만료 기간으로 잡는다
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 120; // 1시간을 refreshToken 만료 기간으로 잡는다.

    private final MyUserDetails myUserDetails;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().
                map(s -> new SimpleGrantedAuthority(s.getAuthority())).
                filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALIDATION_SECOND);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(){
        Claims claims = Jwts.claims().setSubject(null);

        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_VALIDATION_SECOND);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }else{
            return null;
        }
    }

    public String resolveRefreshToken(HttpServletRequest req){
        String refreshToken = req.getHeader("RefreshToken");
        if(refreshToken != null){
            return refreshToken.substring(7);
        } else {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date()); //
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            return false;
        }
    }
}
