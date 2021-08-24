package com.cfgtest.services.customerservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableWebFlux
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

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

}
