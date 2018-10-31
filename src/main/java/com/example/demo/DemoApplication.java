package com.example.demo;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        return new LocalSessionFactoryBuilder(dataSource).buildSessionFactory();
    }
}
