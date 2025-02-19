package kr.co.bestiansoft.ebillservicekg.config.datasource;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Bean
    @ConfigurationProperties(prefix = "spring.datasource.kgst")
    public DataSource kgstDataSource() {
        return DataSourceBuilder.create().build();
    }
	
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.homepage")
    public DataSource homepageDataSource() {
        return DataSourceBuilder.create().build();
    }

}