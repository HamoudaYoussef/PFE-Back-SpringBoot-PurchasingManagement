package biz.picosoft.demo.Workflow.config;

import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.job.service.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "org.flowable.engine.repository", entityManagerFactoryRef = "internDSEmFactory", transactionManagerRef = "internDSTransactionManager")
public class FlowableDBConfig {

    @Autowired
    @Qualifier("processEngine")
    private ProcessEngine processEngine;

    @Bean
    public LocalContainerEntityManagerFactoryBean internDSEmFactory(@Qualifier("flowabledb") DataSource internDS, EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.order_ inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.default_schema", "public");

        return builder
            .dataSource(internDS)
            .packages("org.flowable.engine")
            .persistenceUnit("internDS")
            .properties(properties)
            .build();
    }

    @Bean(name = "internDSTransactionManager")
    public PlatformTransactionManager internDSTransactionManager(@Qualifier("internDSEmFactory") EntityManagerFactory internDSEmFactory) {
        return new JpaTransactionManager(internDSEmFactory);
    }

    @Bean(name = "jdbcDatasourceInternService")
    @Autowired
    public JdbcTemplate createJdbcTemplate_ProfileService(@Qualifier("flowabledb") DataSource internDS) {
        return new JdbcTemplate(internDS);
    }

    @Bean
    public ProcessEngineConfigurationImpl processEngineConfiguration() {
        DefaultAsyncJobExecutor demoAsyncJobExecutor = new DefaultAsyncJobExecutor();
        demoAsyncJobExecutor.setCorePoolSize(10);
        demoAsyncJobExecutor.setMaxPoolSize(50);
        demoAsyncJobExecutor.setKeepAliveTime(10000);
        demoAsyncJobExecutor.setMaxAsyncJobsDuePerAcquisition(50);
        demoAsyncJobExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(5000);
        demoAsyncJobExecutor.setAsyncJobAcquisitionEnabled(true);
        demoAsyncJobExecutor.setTimerJobAcquisitionEnabled(true);
        demoAsyncJobExecutor.setSecondsToWaitOnShutdown(10);
        demoAsyncJobExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(5000);
        demoAsyncJobExecutor.setDefaultQueueSizeFullWaitTimeInMillis(5000);

        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
        processEngineConfiguration.setEnableDatabaseEventLogging(true);
        processEngineConfiguration.setEnableHistoricTaskLogging(true);
        processEngineConfiguration.setEnableLogSqlExecutionTime(true);
           processEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);
        processEngineConfiguration.setAsyncExecutorActivate(true);
        processEngineConfiguration.setAsyncHistoryEnabled(true);
        processEngineConfiguration.setAsyncExecutorAsyncJobAcquisitionEnabled(true);
        processEngineConfiguration.setAsyncExecutorTimerJobAcquisitionEnabled(true);
        processEngineConfiguration.setAsyncHistoryExecutorResetExpiredJobsEnabled(true);
        processEngineConfiguration.setAsyncExecutorCorePoolSize(10);
        processEngineConfiguration.setAsyncExecutorMaxPoolSize(50);
        processEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(5000);
        processEngineConfiguration.setAsyncExecutorThreadKeepAliveTime(10000);
        processEngineConfiguration.setAsyncExecutorDefaultAsyncJobAcquireWaitTime(5000);
        processEngineConfiguration.setAsyncExecutorDefaultQueueSizeFullWaitTime(5000);

        processEngineConfiguration.setAsyncExecutor(demoAsyncJobExecutor);
        processEngineConfiguration.buildProcessEngine();
        return processEngineConfiguration;
    }

}
