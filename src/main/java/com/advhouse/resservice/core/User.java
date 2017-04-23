package com.advhouse.resservice.core;

import com.advhouse.resservice.api.UserApi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Сущность пользователя, является центральной сущностью
 * системы, на нее завязываются все остальные
 *
 * @author proweber1
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.BY_USERNAME, query = "from User u WHERE username = :username")
})
public final class User {
    public static final String BY_USERNAME = "user.queries.byUsername";

    /**
     * Ключевое поле пользователя, это так же Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя, используется для авторизации, является
     * так же email'ом
     */
    @NotEmpty
    @Email
    @Column(unique = true)
    private String username;

    /**
     * Пароль пользователя, обязательное поле, хешируется в SHA-256, согласно
     * стандарту безопасности, пароль должен быть длиное не менее 8 символов
     */
    @NotEmpty
    @Length(min = 8)
    @JsonIgnore
    private String password;

    /**
     * Конструктор для работы джексона
     */
    public User() {
        // Jackson
    }

    /**
     * @param username Имя пользователя
     * @param password Пароль
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    /**
     * @return Primary key пользователя
     */
    public Long getId() {
        return id;
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
     * @param id Уникальный ID пользователя
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param username Имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password Пароль пользователя
     */
    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    /**
     * Создает и заполняет сущность пользователя из пользователя из запроса клиента
     *
     * @param userApi Пользователь из запроса клиента
     * @return Сущность пользователя
     */
    public static User valueOf(UserApi userApi) {
        return new User(userApi.getUsername(), userApi.getPassword());
    }
}
