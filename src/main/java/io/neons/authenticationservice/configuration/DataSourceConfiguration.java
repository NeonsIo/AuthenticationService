package io.neons.authenticationservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean(name = "dataSourceAuthorization")
    public DataSource dataSourceAuthorization() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(this.dbUrl);
        dataSource.setUsername(this.dbUser);
        dataSource.setPassword(this.dbPassword);

        return dataSource;
    }
}
