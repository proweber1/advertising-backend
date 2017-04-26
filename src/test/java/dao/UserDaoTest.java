package dao;

import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.UserDao;
import com.advhouse.resservice.db.impl.UserDaoImpl;
import io.dropwizard.testing.junit.DAOTestRule;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Тестируем работу с таблицей пользователей через
 * UserDAO интерфейс
 *
 * @author proweber1
 */
public class UserDaoTest {

    /**
     * Правило для тестирования взаимодействия с базой данных
     */
    @Rule
    public DAOTestRule db = DAOTestRule.newBuilder()
            .addEntityClass(User.class)
            .build();

    /**
     * Наш UserDAO который мы будем тестировать
     */
    private UserDao userDao;

    /**
     * Перед каждым тестом собираем наш userDao
     */
    @Before
    public void setUp() {
        userDao = new UserDaoImpl(db.getSessionFactory());
    }

    /**
     * Проверяем что пользователи сохраняются, для этого пишем пользователя
     * в базу данных в памяти H2 и проверяем появился ли у нее ID, а дальше
     * еще проверяем что пароль тоже верен
     */
    @Test
    public void saveUserTest() {
        final String password = "XWuiYdlj";

        User savedUser = db.inTransaction(() -> userDao.save(new User("w.gusser@mail.ru", password)));
        Assertions.assertThat(savedUser.getId()).isNotNull().isEqualTo(1);
        final String testPassword = DigestUtils.sha256Hex(password);
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(testPassword);
    }

    /**
     * Проверяем, что метод isExist работает правильно, сначала
     * проверяем ситуацию когда одинаковых пользователей нет
     */
    @Test
    public void isExistMethodTest() {
        final User user = new User("w.gusser@mail.ru", "XWuiYdlj");
        boolean isUserExist = db.inTransaction(() -> userDao.isUserExist(user));
        Assertions.assertThat(isUserExist).isEqualTo(false);

        // Создаем теперь пользователя
        db.inTransaction(() -> userDao.save(user));
        isUserExist = db.inTransaction(() -> userDao.isUserExist(user));
        Assertions.assertThat(isUserExist).isEqualTo(true);
    }
}
