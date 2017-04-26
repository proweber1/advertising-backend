package com.advhouse.resservice.strategy;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.core.User;

/**
 * Стратегия для генерации токенов пользователей
 *
 * @author proweber1
 */
public interface AccessTokenGeneratorStrategy {

    /**
     * Заполняет объект сущности которая хранит токен авторизации
     * и возвращает его
     *
     * @param owner Пользователь который будет владеть токеном
     * @return Сущность токена
     */
    AccessToken generate(final User owner);
}
