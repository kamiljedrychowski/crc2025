//package com.crc.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ConfigurationProperties("spring")
//public class SpringJdbcConfig {
//
//    @Value("${datasource.username}")
//    private String username;
//
//    @Value("${datasource.password}")
//    private String password;
//
//
//    @Bean
//    public DataSource postgresDatasource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5400/postgres");
//        dataSource.setSchema("testtest");
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
//}
