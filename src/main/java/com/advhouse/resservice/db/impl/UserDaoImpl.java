package com.advhouse.resservice.db.impl;

import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.UserDao;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Реализация интерфейса для управления пользователями
 *
 * @author proweber1
 */
public class UserDaoImpl extends AbstractDAO<User> implements UserDao {
    /**
     * {@inheritDoc}
     */
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    public User save(User u) {
        return persist(u);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUserExist(User user) {
        return currentSession().createNamedQuery(User.BY_USERNAME, User.class)
                .setParameter("username", user.getUsername())
                .uniqueResultOptional()
                .isPresent();
    }
}
