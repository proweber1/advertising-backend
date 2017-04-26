package com.advhouse.resservice.security;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.db.AccessTokenDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

/**
 * Авторизация пользователя через OAuth2
 *
 * @author proweber1
 */
public class OauthAuthenticator implements Authenticator<String, UserPrincipal> {

    private final AccessTokenDao dao;

    /**
     * @param dao DAO для работы с токенами авторизации
     */
    public OauthAuthenticator(AccessTokenDao dao) {
        this.dao = dao;
    }

    /**
     * Аутентификация пользователя с помощью access token'а
     *
     * @param s Токен доступа
     * @return Принципал пользователя
     * @throws AuthenticationException Ошибка аутентификации
     */
    @Override
    public Optional<UserPrincipal> authenticate(String s) throws AuthenticationException {
        Optional<AccessToken> accessToken = dao.find(s);
        if (!accessToken.isPresent()) {
            Optional.empty();
        }
        return null;
    }
}
