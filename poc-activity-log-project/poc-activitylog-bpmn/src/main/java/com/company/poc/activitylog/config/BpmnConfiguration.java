package com.company.poc.activitylog.config;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.sql.DataSource;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = {"com.company.poc.activitylog"})
public class BpmnConfiguration {

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(org.h2.Driver.class);
    dataSource.setUrl("jdbc:h2:file:./db/activity-log-1;DB_CLOSE_DELAY=-1");
    dataSource.setUsername("admin");
    dataSource.setPassword("admin");

    return dataSource;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public SpringProcessEngineConfiguration processEngineConfiguration() {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

    config.setDataSource(dataSource());
    config.setTransactionManager(transactionManager());
    config.setDatabaseSchemaUpdate("true");
    config.setHistory("audit");
    config.setJobExecutorActivate(true);
    List<ProcessEnginePlugin> processEnginePlugins = new ArrayList<>();
    processEnginePlugins.add(connectProcessEnginePlugin());

    config.setProcessEnginePlugins(processEnginePlugins);

    return config;
  }

  @Bean
  public static ProcessEnginePlugin connectProcessEnginePlugin() {
    return new ConnectProcessEnginePlugin();
  }

  @Bean
  public ProcessEngineFactoryBean processEngine() {
    ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
    factoryBean.setProcessEngineConfiguration(processEngineConfiguration());

    return factoryBean;
  }

  @Bean
  public RepositoryService repositoryService(ProcessEngine processEngine) {
    return processEngine.getRepositoryService();
  }

  @Bean
  public RuntimeService runtimeService(ProcessEngine processEngine) {
    return processEngine.getRuntimeService();
  }

  @Bean
  public TaskService taskService(ProcessEngine processEngine) {
    return processEngine.getTaskService();
  }

//  @Bean
//  public FilterRegistrationBean authenticationFilter() {
//    FilterRegistrationBean registration = new FilterRegistrationBean();
//    Filter myFilter = new ProcessEngineAuthenticationFilter();
//    registration.setFilter(myFilter);
//    registration.addUrlPatterns("/*");
//    registration.addInitParameter("authentication-provider","org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider");
//    registration.setName("camunda-auth");
//    registration.setOrder(1);
//    return registration;
//  }


}
