package com.advhouse.resservice.security;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.AccessTokenDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

/**
 * Авторизация пользователя через OAuth2
 *
 * @author proweber1
 */
public class OauthAuthenticator implements Authenticator<String, UserPrincipal> {
    private AccessTokenDao accessTokenDao;

    public OauthAuthenticator(final AccessTokenDao accessTokenDao) {
        this.accessTokenDao = accessTokenDao;
    }

    /**
     * Аутентификация пользователя с помощью access token'а
     *
     * @param s Токен доступа
     * @return Принципал пользователя
     * @throws AuthenticationException Ошибка аутентификации
     */
    @UnitOfWork
    @Override
    public Optional<UserPrincipal> authenticate(String s) throws AuthenticationException {
        final Optional<AccessToken> accessToken = accessTokenDao.find(s);
        if (accessToken.isPresent()) {
            final User user = accessToken.get().getUser();
            return Optional.of(new UserPrincipal(user));
        }

        return Optional.empty();
    }
}
