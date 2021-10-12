package com.server.Dotori.config.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.server.Dotori.config.xss.HTMLCharacterEscapes;
import io.swagger.models.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override //CORS 설정
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:5000", "http://localhost:3000",
                        "https://localhost:8080", "https://localhost:5000", "https://localhost:3000") // local, docker, dev-client
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()
                )
                .maxAge(3600);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(htmlEscapingConverter());
    }

    /**
     *  MappingJackson2HttpMessageConverter 를 커스터마이징 하여
     *  응답 객체 이스케이프 문자 설정
     * @return 커스텀 설정이 적용된 컨버터
     * @author 배태현
     */
    @Bean
    public HttpMessageConverter htmlEscapingConverter() {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes()); //  xss 처리 문자 세팅
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
        htmlEscapingConverter.setObjectMapper(objectMapper);
        return htmlEscapingConverter;
    }
}
