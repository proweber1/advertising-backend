package com.advhouse.resservice.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Это сущность которая хранить токены доступа к пользователю, авторизация
 * пользователя осуществляется через эти самые токены
 * <p>
 * todo: Этот класс обрастает конструкторами, возможно нужно завести Builder
 *
 * @author proweber1
 */
@Entity
@Table(name = "access_tokens")
@NamedQueries({
        @NamedQuery(name = AccessToken.BY_ACCESS_TOKEN, query = "from AccessToken a WHERE accessToken = :token")
})
public class AccessToken {

    public final static String BY_ACCESS_TOKEN = "accesstoken.queries.byaccesstoken";

    /**
     * Это ключевое и уникальное поле ID, primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Это токен который используется для атворизации пользователя
     * в системе
     */
    @Column(name = "access_token", unique = true, nullable = false)
    private String accessToken;

    /**
     * Это пользователь которому принаджлежит данный токен
     */
    @OneToOne
    @Cascade(CascadeType.ALL)
    private User user;

    /**
     * Дата истечения токена
     */
    @Column(name = "expires_in", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar expiresIn;

    public AccessToken() {

    }

    /**
     * @param accessToken Токен пользователя
     * @param expiresIn   Дата истечения
     */
    public AccessToken(final String accessToken, final Calendar expiresIn) {
        this(accessToken, expiresIn, null);
    }

    /**
     * @param accessToken Токен
     * @param expiresIn   Дата истечения токена
     * @param user        Владелец токена
     */
    public AccessToken(final String accessToken, final Calendar expiresIn, final User user) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    /**
     * @return Первичный ключ токенов
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * @param id Уникальный первичный ключ
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Токен доступа
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken Токен доступа
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return Владелец токена
     */
    @JsonIgnore
    public User getUser() {
        return user;
    }

    /**
     * @param user Владелец токена
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Дата истечения токена
     */
    public Calendar getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param expiresIn Дата истечения токена
     */
    public void setExpiresIn(Calendar expiresIn) {
        this.expiresIn = expiresIn;
    }
}
