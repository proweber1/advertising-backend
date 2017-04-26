package com.advhouse.resservice.db.impl;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.db.AccessTokenDao;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Optional;

/**
 * DAO для работы с токенами авторизации пользователей
 *
 * @author proweber1
 */
public class AccessTokenDaoImpl extends AbstractDAO<AccessToken> implements AccessTokenDao {

    /**
     * {@inheritDoc}
     */
    public AccessTokenDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccessToken> find(String accessToken) {
        return currentSession().createNamedQuery(AccessToken.BY_ACCESS_TOKEN, AccessToken.class)
                .setParameter("token", accessToken)
                .uniqueResultOptional();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessToken save(AccessToken accessToken) {
        return persist(accessToken);
    }
}
