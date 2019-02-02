package me.foodbag.hello.db;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages = "me.foodbag.hello.persistence.repository")
@EnableTransactionManagement
public class SessionFactory {
  private static final Config config = ConfigFactory.load().getConfig("database");
  private static final String JDBC_URL_PATTERN = config.getString("host");
  private static final String DB_PASS = config.getString("password");
  private static final String DB_USER = config.getString("user");
  private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

  /**
   * MariaDb kannan luonti
   *
   * @return palauttaa uuden HikariDataSource instanssin
   */
  @Bean(destroyMethod = "close")
  @Primary
  public DataSource dataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setDriverClassName(JDBC_DRIVER);
    hikariDataSource.setJdbcUrl(JDBC_URL_PATTERN);
    hikariDataSource.setUsername(DB_PASS);
    hikariDataSource.setPassword(DB_USER);
    return hikariDataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("me.foodbag.hello.persistence");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    em.setJpaProperties(jpaProperties());
    return em;
  }

  /** Hibernate asetukset JPA:lle */

  @Bean
  private static Properties jpaProperties() {
    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
    return hibernateProperties;
  }

}
