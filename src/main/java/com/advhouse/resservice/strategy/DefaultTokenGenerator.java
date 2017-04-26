package com.advhouse.resservice.strategy;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.core.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Calendar;
import java.util.UUID;

/**
 * Обычная стратегия для генерации токена пользователя
 *
 * @author proweber1
 */
public class DefaultTokenGenerator implements AccessTokenGeneratorStrategy {

    private final static int TOKEN_LIVE_HOURS = 24;

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessToken generate(User owner) {
        final String uuid = UUID.randomUUID().toString();
        final String accessToken = DigestUtils.sha256Hex(uuid);

        final Calendar expires = Calendar.getInstance();
        expires.add(Calendar.HOUR, TOKEN_LIVE_HOURS);

        return new AccessToken(accessToken, expires, owner);
    }
}
