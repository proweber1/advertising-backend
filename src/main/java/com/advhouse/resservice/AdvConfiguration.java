package com.advhouse.resservice;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Конфигурация rest сервиса
 */
class AdvConfiguration extends Configuration {

    /**
     * Конфигурация для подключения к базе данных
     */
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    /**
     * Конфигурация сваггера для документации апи
     */
    @Valid
    @NotNull
    private SwaggerBundleConfiguration swagger = new SwaggerBundleConfiguration();

    DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    public SwaggerBundleConfiguration getSwagger() {
        return swagger;
    }

    public void setSwagger(SwaggerBundleConfiguration swagger) {
        this.swagger = swagger;
    }
}
