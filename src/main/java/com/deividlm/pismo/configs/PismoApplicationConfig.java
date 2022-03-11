package com.deividlm.pismo.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PismoApplicationConfig {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }


}
