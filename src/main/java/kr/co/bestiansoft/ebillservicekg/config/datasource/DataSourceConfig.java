package kr.co.bestiansoft.ebillservicekg.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

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