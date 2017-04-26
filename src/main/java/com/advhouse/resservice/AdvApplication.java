package com.advhouse.resservice;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.AccessTokenDao;
import com.advhouse.resservice.db.UserDao;
import com.advhouse.resservice.db.impl.AccessTokenDaoImpl;
import com.advhouse.resservice.db.impl.UserDaoImpl;
import com.advhouse.resservice.resources.OAuthResource;
import com.advhouse.resservice.resources.UsersResource;
import com.advhouse.resservice.security.OauthAuthenticator;
import com.advhouse.resservice.security.OauthAuthorizer;
import com.advhouse.resservice.security.UserPrincipal;
import com.advhouse.resservice.services.AuthService;
import com.advhouse.resservice.services.AuthServiceImpl;
import com.advhouse.resservice.strategy.DefaultTokenGenerator;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Точка входа в adv приложение
 *
 * @author proweber1
 */
public class AdvApplication extends Application<AdvConfiguration> {

    /**
     * Бандл для работы с hibernate
     */
    private final HibernateBundle<AdvConfiguration> hibernate = new HibernateBundle<AdvConfiguration>(User.class, AccessToken.class) {
        public PooledDataSourceFactory getDataSourceFactory(AdvConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    public static void main(String[] args) throws Exception {
        new AdvApplication().run(args);
    }

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
        final AccessTokenDao accessTokenDao = new AccessTokenDaoImpl(hibernate.getSessionFactory());

        final AuthService authService = new AuthServiceImpl(accessTokenDao, userDao, new DefaultTokenGenerator());

        environment.jersey().register(new UsersResource(userDao));
        environment.jersey().register(new OAuthResource(authService));

        // Регистрируем корс для всех маршрутов системы
        FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedMethods", "GET, POST, HEAD, PATCH, PUT, DELETE, OPTIONS");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Register oauth2
        registerOauth(accessTokenDao, environment);
    }

    /**
     * Регистрирует OAuth2 для возможности авторизации
     *
     * @param accessTokenDao DAO для работы с токенами
     * @param environment    Окружение приложения
     */
    private void registerOauth(AccessTokenDao accessTokenDao, Environment environment) {
        OAuthCredentialAuthFilter<UserPrincipal> bearer = new OAuthCredentialAuthFilter.Builder<UserPrincipal>()
                .setAuthenticator(new OauthAuthenticator(accessTokenDao))
                .setAuthorizer(new OauthAuthorizer())
                .setPrefix("Bearer")
                .buildAuthFilter();

        environment.jersey().register(new AuthDynamicFeature(bearer));
    }
}
