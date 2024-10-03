package biz.picosoft.demo.Workflow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig {

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> flowableConfigurer() {
        return engineConfiguration -> {
            // Customize the ObjectMapper used by Flowable
            ObjectMapper objectMapper = engineConfiguration.getObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            // You can also customize other Flowable configurations if needed

        };
    }

}
