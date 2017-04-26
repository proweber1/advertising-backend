package com.advhouse.resservice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Это запрос для авторизации пользователя, принимает
 * логин и пароль
 *
 * @author proweber1
 */
public class LoginRepresentation {

    /**
     * Имя пользователя, является в данной реализации емейлом
     */
    @NotEmpty
    @Email
    private String username;

    /**
     * Пароль пользователя, минимум 8 символов по спецификации
     * безопасности веб проектов
     */
    @NotEmpty
    @Length(min = 8)
    private String password;

    public LoginRepresentation() {
        // Соглашение по разработки бинов
    }

    /**
     * Данный конструктор используется для создания модели из
     * JSON схемы
     *
     * @param username Имя пользователя
     * @param password Пароль
     */
    @JsonCreator
    public LoginRepresentation(@JsonProperty("username") String username,
                               @JsonProperty("password") String password) {

        this.username = username;
        this.password = password;
    }

    /**
     * @return Имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRepresentation that = (LoginRepresentation) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
