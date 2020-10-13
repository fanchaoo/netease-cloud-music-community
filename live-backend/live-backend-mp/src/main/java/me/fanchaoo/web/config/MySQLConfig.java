package me.fanchaoo.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan("me.fanchaoo.web.dao.mapper")
public class MySQLConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return new DruidDataSource();
	}

	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

		Map<String, String> initParams = new HashMap<>();
		initParams.put("loginUsername", "admin");
		initParams.put("loginPassword", "admin");
		bean.setInitParameters(initParams);
		return bean;
	}

	@Bean
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean<>();
		bean.setFilter(new WebStatFilter());

		Map<String, String> initParams = new HashMap<>();
		initParams.put("exclusions", "*.js,*.css,/druid/*");
		bean.setInitParameters(initParams);

		bean.setUrlPatterns(Arrays.asList("/*"));

		return bean;
	}
}
