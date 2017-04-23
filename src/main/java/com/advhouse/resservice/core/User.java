package com.advhouse.resservice.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;

/**
 * Сущность пользователя, является центральной сущностью
 * системы, на нее завязываются все остальные
 *
 * @author proweber1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonSerialize
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
    @Length(min = 8, max = 128)
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
    @JsonCreator
    public User(@JsonProperty("username") final String username,
                @JsonProperty("password") final String password) {

        this.username = username;
        this.password = password;
    }

    /**
     * @return Primary key пользователя
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id Уникальный ID пользователя
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username Имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Пароль пользователя
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * @param password Пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param o Объект с которым мы будем сравниваться
     * @return Результат сравнения двух объектов
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    /**
     * @return хеш код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    /**
     * Генерирует строковое представление класса, чтобы можно было
     * легко читать в консоле например при тестировании
     *
     * @return Строковое представление
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
