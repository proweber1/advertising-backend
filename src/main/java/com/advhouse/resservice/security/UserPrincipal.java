package com.advhouse.resservice.security;

import com.advhouse.resservice.core.User;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * Принципал для авторизации пользователя
 *
 * @author proweber1
 */
public class UserPrincipal implements Principal {

    /**
     * Пользователь который пытается запросить данные
     */
    private User user;

    UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * Возвращает того от чьего имени будет происходить операция
     *
     * @return Имя пользователя
     */
    @Override
    public String getName() {
        return user.getUsername();
    }

    /**
     * @return Авторизированный пользователь
     */
    public User getUser() {
        return user;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
