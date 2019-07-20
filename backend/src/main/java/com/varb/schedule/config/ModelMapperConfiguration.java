package com.varb.schedule.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfiguration{

    private final ModelMapperCustomize modelMapperCustomize;

    @Bean
    public ModelMapper modelMapper(){
        //return modelMapperCustomize;
        modelMapperCustomize.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        return modelMapperCustomize;
    }
}
