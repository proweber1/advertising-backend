package com.advhouse.resservice.security;

import io.dropwizard.auth.Authorizer;

/**
 * Авторизация пользователя в системе, проверка на доступ к ресурсу
 *
 * @author proweber1
 */
public class OauthAuthorizer implements Authorizer<UserPrincipal> {

    @Override
    public boolean authorize(UserPrincipal principal, String role) {
        return true;
    }
}
