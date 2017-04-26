package dao;

import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.AccessTokenDao;
import com.advhouse.resservice.db.impl.AccessTokenDaoImpl;
import io.dropwizard.testing.junit.DAOTestRule;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

/**
 * Тестирование DAO для работы с access token'ами
 *
 * @author proweber1
 */
public class AccessTokenDaoTest {

    /**
     * Правило для тестирования сущностей базы данных
     */
    @Rule
    public DAOTestRule db = DAOTestRule.newBuilder()
            .addEntityClass(AccessToken.class)
            .addEntityClass(User.class)
            .build();

    private AccessTokenDao accessTokenDao;
    private AccessToken model;

    /**
     * Перед каждым тестом пересоздаем dao
     */
    @Before
    public void setUp() {
        accessTokenDao = new AccessTokenDaoImpl(db.getSessionFactory());

        final String token = UUID.randomUUID().toString();
        final User user = new User("proweber1@yandex.ru", "XWuiYdlj");
        model = new AccessToken(token, Calendar.getInstance(), user);
    }

    /**
     * Проверяем что наше DAO может че-нить сохранять
     */
    @Test
    public void saveTest() {
        final AccessToken dao = db.inTransaction(() -> accessTokenDao.save(model));
        Assertions.assertThat(dao.getId()).isNotNull().isEqualTo(1);
    }

    /**
     * Пытаемся найти access token
     */
    @Test
    public void findTest() {
        // Сначала создаем токен
        final AccessToken accessToken = db.inTransaction(() -> accessTokenDao.save(model));
        long id = accessToken.getId();

        Optional<AccessToken> findedToken = accessTokenDao.find(model.getAccessToken());
        Assertions.assertThat(findedToken.isPresent()).isTrue();
        Assertions.assertThat(findedToken.get().getId()).isEqualTo(id);
    }
}
