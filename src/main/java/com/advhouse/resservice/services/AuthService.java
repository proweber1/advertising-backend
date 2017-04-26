package com.advhouse.resservice.services;

import com.advhouse.resservice.api.LoginRepresentation;
import com.advhouse.resservice.core.AccessToken;

import javax.security.auth.login.LoginException;

/**
 * Этот сервис работает с авторизацией клиента в системе
 *
 * @author proweber1
 */
public interface AuthService {

    /**
     * Этот метод помогает нам авторизировать пользователя и привязать
     * к нему токен
     *
     * @param loginRepresentation Запрос входа
     * @return Токен авторизации
     * @throws LoginException Ошибка при авторизации
     */
    AccessToken login(LoginRepresentation loginRepresentation) throws LoginException;
}
