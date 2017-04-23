package com.advhouse.resservice;

import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.UserDao;
import com.advhouse.resservice.db.impl.UserDaoImpl;
import com.advhouse.resservice.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 * Точка входа в adv приложение
 *
 * @author proweber1
 */
public class AdvApplication extends Application<AdvConfiguration> {
    /**
     * Бандл для работы с hibernate
     */
    private final HibernateBundle<AdvConfiguration> hibernate = new HibernateBundle<AdvConfiguration>(User.class) {
        public PooledDataSourceFactory getDataSourceFactory(AdvConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Bootstrap<AdvConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);

        // Инициализация бандла для работы с миграциями
        bootstrap.addBundle(new MigrationsBundle<AdvConfiguration>() {
            public PooledDataSourceFactory getDataSourceFactory(AdvConfiguration configuration) {
                return configuration.getDatabase();
            }
        });

        // Инициализация бандла для работы со сваггером
        bootstrap.addBundle(new SwaggerBundle<AdvConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AdvConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void run(AdvConfiguration advConfiguration, Environment environment) throws Exception {
        final UserDao userDao = new UserDaoImpl(hibernate.getSessionFactory());

        environment.jersey().register(new UsersResource(userDao));
    }

    public static void main(String[] args) throws Exception {
        new AdvApplication().run(args);
    }
}
