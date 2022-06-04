package com.zakharenko.cloud_storage.cloud_storage.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.zakharenko.cloud_storage.cloud_storage*")
@EnableJpaRepositories(basePackages = "com.zakharenko.cloud_storage.cloud_storage.repository*")
@EnableWebMvc
@EnableTransactionManagement
public class CloudStorageConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private Environment env;

    @Value("${driverClass}")
    private String driverClass;

    @Value("${dbuser}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${jdbcUrl}")
    private String jdbcUrl;


    @Autowired
    public CloudStorageConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/icon/**").addResourceLocations("classpath:/static/icon/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/sass/**").addResourceLocations("classpath:/static/sass/");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        factory.setPackagesToScan(env.getRequiredProperty("packagesToScan"));
        Properties props = new Properties();
        props.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        props.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        props.put("hibernate.ddl-auto", env.getRequiredProperty("hibernate.ddl-auto"));
        factory.setJpaProperties(props);
        return factory;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

}
