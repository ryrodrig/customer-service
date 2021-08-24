package com.cfgtest.services.customerservice.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
public class CustomerServiceConfig {

//    @Bean
//    @Primary
//    public ObjectMapper objectMapper() {
//        JavaTimeModule module = new JavaTimeModule();
//        module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        return new ObjectMapper()
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
//                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
//                .registerModule(module);
//    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat("yyyy-MM-dd");
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        };
    }


    @Bean
    Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper) {
        return new Jackson2JsonEncoder(mapper);
    }

    @Bean
    Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper) {
        return new Jackson2JsonDecoder(mapper);
    }

//    define webFluxConfigurer to manipulated JSON response.
    @Bean
    WebFluxConfigurer webFluxConfigurer(Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder) {
        return new WebFluxConfigurer() {
            @Override
            public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
                configurer.defaultCodecs().jackson2JsonEncoder(encoder);
                configurer.defaultCodecs().jackson2JsonDecoder(decoder);
            }
        };
    }

}
