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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "kr.co.bestiansoft.ebillservicekg.**.repository", sqlSessionFactoryRef = "kgstSqlSessionFactory")
public class KgstDataSourceConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name = "kgstSqlSessionFactory")
	public SqlSessionFactory kgstSqlSessionFactory(@Qualifier("kgstDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// importance!
//        sessionFactoryBean.setTypeAliasesPackage("com.example.demo.login.vo");
//        sessionFactoryBean.setTypeHandlersPackage("com.example.demo.login.vo");
		sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
		sessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));

		return sessionFactoryBean.getObject();
	}

	@Bean(name = "kgstSqlSessionTemplate")
	public SqlSessionTemplate kgstSqlSessionTemplate(
			@Qualifier("kgstSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Primary
	public DataSourceTransactionManager kgstTransactionManager(@Qualifier("kgstDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}