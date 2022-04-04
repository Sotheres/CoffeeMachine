package com.chertov.coffeemachine.testcontainers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSqlTestContainer extends PostgreSQLContainer<PostgreSqlTestContainer> {

//    public PostgreSqlTestContainer(@Value("${POSTGRES_IMAGE}") String dockerImage) {
//        super(dockerImage);
//    }

    private static final String POSTGRES_IMAGE = "postgres:14.2";
    private static PostgreSqlTestContainer container;

    public static PostgreSqlTestContainer getInstance() {
        if (container == null) {
            container =  new PostgreSqlTestContainer(POSTGRES_IMAGE);
        }

        return  container;
    }

    private PostgreSqlTestContainer(String dockerImageName) {
        super(dockerImageName);
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", this.getJdbcUrl());
        System.setProperty("spring.datasource.username", this.getUsername());
        System.setProperty("spring.datasource.password", this.getPassword());
        System.setProperty("spring.datasource.database-name", this.getDatabaseName());
    }
}
