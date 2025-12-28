package com.example.awsapplication.dbconfig;
import com.example.awsapplication.DbSecret;
import com.example.awsapplication.service.SecretsService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.awsapplication.repo.ebook",
        entityManagerFactoryRef = "ebookEmf",
        transactionManagerRef = "ebookTx"
)
@RequiredArgsConstructor
public class EbookDbConfig {

    private final SecretsService secretsService;

    @Value("${db.secret.name}")
    private String secretName;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public DataSource ebookDataSource() {
        DbSecret secret = secretsService.getDbSecret(secretName, awsRegion);

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://" + secret.getHost() + ":" + secret.getPort() + "/ebooks");
        ds.setUsername(secret.getUsername());
        ds.setPassword(secret.getPassword());

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ebookEmf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ebookDataSource());
        emf.setPackagesToScan("com.example.awsapplication.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }

    @Bean
    public PlatformTransactionManager ebookTx() {
        return new JpaTransactionManager(ebookEmf().getObject());
    }
}
