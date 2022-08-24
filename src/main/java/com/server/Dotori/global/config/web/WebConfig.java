package com.server.Dotori.global.config.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.server.Dotori.global.config.interceptor.Interceptor;
import com.server.Dotori.global.config.xss.HTMLCharacterEscapes;
import io.swagger.models.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Interceptor interceptor;

    @Override //CORS 설정
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:3000", "http://localhost:5000",
                        "https://www.dotori-gsm.com", "https://dotori-gsm.com", "https://server.dotori-gsm.com") // local, dotori-domain
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
     *  매 요청마다 request 와 response 를 체크하는 인터셉터 등록
     * @author 노경준
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**");
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
