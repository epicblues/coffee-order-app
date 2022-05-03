package com.epicblues.coffee.server;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class EmbeddedDatabaseTestModule {

  private final static MySQLContainer mysql = new MySQLContainer<>(
      DockerImageName
          .parse("mysql:8.0.28-debian"))
      .withDatabaseName("gc-coffee")
      .withInitScript("schema.sql")
      .withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci");
  private static DataSource testDataSource = null;

  private EmbeddedDatabaseTestModule() {
  }

  public static DataSource getDataSource() {
    if (!mysql.isRunning()) {
      mysql.start();
    }
    if (testDataSource == null) {
      testDataSource = DataSourceBuilder.create()
          .type(HikariDataSource.class)
          .username(mysql.getUsername())
          .password(mysql.getPassword())
          .url(mysql.getJdbcUrl())
          .build();
    }
    return testDataSource;
  }
}
