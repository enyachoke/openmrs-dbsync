package org.openmrs.eip.app.db.sync.sender.config;

import org.openmrs.eip.app.db.sync.config.ReceiverEncryptionProperties;
import org.openmrs.eip.app.db.sync.config.SenderEncryptionProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration(exclude = ArtemisAutoConfiguration.class)
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManager",
        basePackages = {"org.openmrs.eip.app.db.sync.repository"}
)
@EntityScan("org.openmrs.eip.app.db.sync.entity")
@ComponentScan({
        "org.openmrs.eip.app.db.sync.service",
        "org.openmrs.eip.app.db.sync.mapper",
        "org.openmrs.eip.app.db.sync.camel"
})
public class TestConfig {

    @Value("${spring.datasource.dialect}")
    private String hibernateDialect;

    @Value("${spring.datasource.ddlAuto}")
    private String ddlAuto;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "pgp.receiver")
    public ReceiverEncryptionProperties receiverEncryptionProperties() {
        return new ReceiverEncryptionProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "pgp.sender")
    public SenderEncryptionProperties senderProperties() {
        return new SenderEncryptionProperties();
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager(final EntityManagerFactoryBuilder builder,
                                                                final DataSource dataSource) {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect", hibernateDialect);
        props.put("hibernate.hbm2ddl.auto", ddlAuto);

        return builder
                .dataSource(dataSource)
                .packages("org.openmrs.eip.app.db.sync.entity")
                .persistenceUnit("openmrs")
                .properties(props)
                .build();
    }
}
