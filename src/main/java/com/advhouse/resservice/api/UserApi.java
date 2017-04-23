package com.advhouse.resservice.api;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Это класс для заполнения пользователя из
 * запроса клиента, вынесено в отдельный класс потому
 * что у сущности пользователя есть проблемы с валидацией,
 * то есть там если сделать @JsonIgnore для геттера свойства
 * password, то это свойство не доступно для валидации
 *
 * @author proweber1
 */
public class UserApi {
    /**
     * Имя пользователя
     */
    @Email
    @NotNull
    private String username;

    /**
     * Пароль пользователя
     */
    @NotNull
    @Length(min = 8)
    private String password;

    /**
     * Пустой конструктор для джексона
     */
    public UserApi() {
        // For jackson
    }

    /**
     * @param username Имя пользователя
     * @param password Пароль
     */
    public UserApi(String username, String password) {
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
     * @return Пароль
     */
    public String getPassword() {
        return password;
    }
}
