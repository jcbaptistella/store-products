package com.cayena.storeproducts.config;

import com.cayena.storeproducts.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProductMapper product() {
        return Mappers.getMapper(ProductMapper.class);
    }
}
