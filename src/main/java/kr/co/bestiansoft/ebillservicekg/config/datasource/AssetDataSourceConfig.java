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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {
	    "kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository",
	    "kr.co.bestiansoft.ebillservicekg.asset.bzenty.repository",
	    "kr.co.bestiansoft.ebillservicekg.asset.equip.repository",
	    "kr.co.bestiansoft.ebillservicekg.asset.install.repository",
	    "kr.co.bestiansoft.ebillservicekg.asset.manual.repository",
	    "kr.co.bestiansoft.ebillservicekg.asset.faq.repository"
	}, sqlSessionFactoryRef = "assetSqlSessionFactory")
public class AssetDataSourceConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name = "assetSqlSessionFactory")
	public SqlSessionFactory assetSqlSessionFactory(@Qualifier("assetDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// importance!
//        sessionFactoryBean.setTypeAliasesPackage("com.example.demo.login.vo");
//        sessionFactoryBean.setTypeHandlersPackage("com.example.demo.login.vo");
		sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
		sessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:mapper3/asset/**/*.xml"));

		return sessionFactoryBean.getObject();
	}

	@Bean(name = "assetSqlSessionTemplate")
	public SqlSessionTemplate assetSqlSessionTemplate(
			@Qualifier("assetSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public DataSourceTransactionManager assetTransactionManager(@Qualifier("assetDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}