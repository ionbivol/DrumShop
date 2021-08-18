package org.online.shop.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.online.shop")
@EntityScan("org.online.shop.entity")
@EnableJpaRepositories("org.online.shop.repository")
public class AppConfig {
}
