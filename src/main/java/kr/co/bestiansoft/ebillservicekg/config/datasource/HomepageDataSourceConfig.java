package kr.co.bestiansoft.ebillservicekg.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "kr.co.bestiansoft.ebillservicekg.**.repository2", sqlSessionFactoryRef = "homepageSqlSessionFactory")
public class HomepageDataSourceConfig {

	@Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "homepageSqlSessionFactory")
    public SqlSessionFactory homepageSqlSessionFactory(@Qualifier("homepageDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // importance!
//        sessionFactoryBean.setTypeAliasesPackage("com.example.demo.homepage.vo");
//        sessionFactoryBean.setTypeHandlersPackage("com.example.test.util.typehandler");
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper2/**/*.xml"));

        return sessionFactoryBean.getObject();
    }

    @Bean(name = "homepageSqlSessionTemplate")
    public SqlSessionTemplate homepageSqlSessionTemplate(
            @Qualifier("homepageSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}