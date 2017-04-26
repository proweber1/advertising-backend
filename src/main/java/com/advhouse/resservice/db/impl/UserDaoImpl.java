package com.advhouse.resservice.db.impl;

import com.advhouse.resservice.api.LoginRepresentation;
import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.UserDao;
import io.dropwizard.hibernate.AbstractDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SessionFactory;

import java.util.Optional;

/**
 * Реализация интерфейса для управления пользователями
 *
 * @author proweber1
 */
public class UserDaoImpl extends AbstractDAO<User> implements UserDao {

    /**
     * {@inheritDoc}
     */
    public UserDaoImpl(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    public User save(final User u) {
        // Перенося сюда хэширование пароля мы решаем проблемы валидации
        final String userPassword = u.getPassword();
        u.setPassword(hashPassword(userPassword));

        return persist(u);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUserExist(final User user) {
        return currentSession()
                .createNamedQuery(User.BY_USERNAME, User.class)
                .setParameter("username", user.getUsername())
                .uniqueResultOptional()
                .isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> byUserRequest(LoginRepresentation loginRepresentation) {
        return currentSession()
                .createNamedQuery(User.FOR_AUTH, User.class)
                .setParameter("username", loginRepresentation.getUsername())
                .setParameter("password", hashPassword(loginRepresentation.getPassword()))
                .uniqueResultOptional();
    }

    /**
     * todo: Подобрать другое место для этого функционала но чтобы дублирования небыло!
     * <p>
     * Хеширует пароль пользователя
     *
     * @param password Пароль
     * @return Хешированный пароль
     */
    private String hashPassword(final String password) {
        return DigestUtils.sha256Hex(password);
    }
}
