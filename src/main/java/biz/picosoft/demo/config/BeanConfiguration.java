package biz.picosoft.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {

    @Autowired
    private Environment environment;

    @Primary
    @Bean(name = "db")
    public DataSource dataSource() throws NamingException {
        String jdbcUrl = environment.getProperty("jdbc.url");
        if (jdbcUrl != null && !jdbcUrl.isEmpty())
            return (DataSource) new JndiTemplate().lookup(jdbcUrl);
        else {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(environment.getProperty("spring.datasource.driverClassName"));
            dataSourceBuilder.url(environment.getProperty("spring.datasource.url"));
            dataSourceBuilder.username(environment.getProperty("spring.datasource.username"));
            dataSourceBuilder.password(environment.getProperty("spring.datasource.password"));
            return dataSourceBuilder.build();
//            return null;
        }
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("db") DataSource ds) {
        return new JdbcTemplate(ds);
    }

}