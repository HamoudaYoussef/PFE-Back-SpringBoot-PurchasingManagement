package biz.picosoft.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
    "biz.picosoft.demo.repository",
    "biz.picosoft.demo.Workflow.repository",
    "biz.picosoft.demo.config.logging.repository"},
    entityManagerFactoryRef = "externDSEmFactory", transactionManagerRef = "externDSTransactionManager")

public class JRDBConfig {


    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties externDSProperties() {
        return new DataSourceProperties();
    }

//       @Primary
//        @Bean
//        public DataSource externDS(@Qualifier("externDSProperties") DataSourceProperties externDSProperties) {
//           System.out.println("database connection:::");
//            return externDSProperties.initializeDataSourceBuilder().build();
//        }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean externDSEmFactory(@Qualifier("db") DataSource externDS, EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        return builder.dataSource(externDS).packages(
            "biz.picosoft.demo.domain",
            "biz.picosoft.demo.Workflow.domain",
                "biz.picosoft.demo.config.logging.domain"
        ).persistenceUnit("externDS").properties(properties).build();
    }

    @Primary
    @Bean(name = "externDSTransactionManager")
    public PlatformTransactionManager externDSTransactionManager(@Qualifier("externDSEmFactory") EntityManagerFactory externDSEmFactory) {
        return new JpaTransactionManager(externDSEmFactory);
    }

    @Primary
    @Bean(name = "jdbcDatasourceExternService")
    @Autowired
    public JdbcTemplate createJdbcTemplate_ProfileService(@Qualifier("db") DataSource externDS) {
        return new JdbcTemplate(externDS);
    }

}







