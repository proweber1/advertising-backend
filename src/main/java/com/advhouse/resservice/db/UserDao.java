package com.advhouse.resservice.db;

import com.advhouse.resservice.api.LoginRepresentation;
import com.advhouse.resservice.core.User;

import java.util.Optional;

/**
 * Управление пользователями, стандартный CRUD
 *
 * @author proweber1
 */
public interface UserDao {

    /**
     * Обеспечивает постоянство пользователя
     *
     * @param u Сущность пользователя
     * @return Сохраненный пользователь
     */
    User save(User u);

    /**
     * Проверка на то, что пользователь не существует
     *
     * @param user Сущность пользователя
     * @return Булево
     */
    boolean isUserExist(User user);

    /**
     * Пытается найти пользователя по авторизационным данным
     *
     * @param loginRepresentation Форма входа пользователя
     * @return Опционал пользователя
     */
    Optional<User> byUserRequest(final LoginRepresentation loginRepresentation);
}
