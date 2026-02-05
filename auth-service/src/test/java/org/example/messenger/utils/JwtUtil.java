package org.example.messenger.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

@ExtendWith(MockitoExtension.class)
public class JwtUtil {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    private String name;

    private String token;

    @BeforeEach
    public void setValue() {
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "2y8duqie8fjrk4i8ssifj339difjei8s");
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtLifetime", Duration.ofMillis(3600000));
        this.name = "Alex";
        this.token = jwtTokenUtil.generationToken(name);
    }

    // генерация
    @Test
    public void successGenerationToken() {
        Assertions.assertNotNull(token);
    }

    // извлечение имени
    @Test
    public void successExtractionName() {
        Assertions.assertEquals(name, jwtTokenUtil.extractUsername(token));
    }

    // истекший срок годности токена
    @Test
    public void expiredToken() {
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtLifetime", Duration.ofMillis(-1000));
        String expiredToken = jwtTokenUtil.generationToken(name);
        Assertions.assertThrows(ExpiredJwtException.class,() -> jwtTokenUtil.validateToken(expiredToken,name));
    }

    // тест неправильной сигнатуры
    @Test
    public void wrongSignature(){
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "Wrongy8duqie8fjrk4i8ssifj339difjei8s");
        jwtTokenUtil.generationToken(name);
        Assertions.assertThrows(SignatureException.class,() -> jwtTokenUtil.validateToken(token,name));


    }
}
