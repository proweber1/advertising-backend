package com.advhouse.resservice.services;

import com.advhouse.resservice.api.LoginRepresentation;
import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.AccessTokenDao;
import com.advhouse.resservice.db.UserDao;
import com.advhouse.resservice.strategy.AccessTokenGeneratorStrategy;

import javax.security.auth.login.LoginException;
import java.util.Optional;

/**
 * Сервис для авторизации пользователя в системе по логину
 * и паролю
 *
 * @author proweber1
 */
public class AuthServiceImpl implements AuthService {

    private final static String USER_NOT_FOUND_MESSAGE = "Неверный логин или пароль";

    private final AccessTokenDao accessTokenDao;
    private final UserDao userDao;
    private final AccessTokenGeneratorStrategy accessTokenGenerator;

    /**
     * @param accessTokenDao DAO для работы с токенами
     * @param userDao        DAO для работы с пользователями
     */
    public AuthServiceImpl(final AccessTokenDao accessTokenDao,
                           final UserDao userDao,
                           final AccessTokenGeneratorStrategy accessTokenGenerator) {

        this.accessTokenDao = accessTokenDao;
        this.userDao = userDao;
        this.accessTokenGenerator = accessTokenGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessToken login(LoginRepresentation loginRepresentation) throws LoginException {
        Optional<User> userOptional = userDao.byUserRequest(loginRepresentation);
        User user = userOptional.orElseThrow(() -> new LoginException(USER_NOT_FOUND_MESSAGE));

        final AccessToken accessToken = accessTokenGenerator.generate(user);
        accessTokenDao.save(accessToken);

        return accessToken;
    }
}
