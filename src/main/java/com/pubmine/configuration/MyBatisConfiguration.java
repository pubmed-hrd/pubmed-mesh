package com.pubmine.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.pubmine.repository")
public class MyBatisConfiguration {

}
