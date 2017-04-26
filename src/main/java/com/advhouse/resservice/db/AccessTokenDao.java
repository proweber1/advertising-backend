package com.advhouse.resservice.db;

import com.advhouse.resservice.core.AccessToken;

import java.util.Optional;

/**
 * DAO для управления токенами пользователя
 *
 * @author proweber1
 */
public interface AccessTokenDao {

    /**
     * Пытается найти токен в базе данных
     *
     * @param accessToken Токен авторизации
     * @return Опционал токена
     */
    Optional<AccessToken> find(String accessToken);

    /**
     * Сохраняет токен в базе данных
     *
     * @param accessToken Сущность токена
     * @return Сохраненная сущность
     */
    AccessToken save(final AccessToken accessToken);
}
