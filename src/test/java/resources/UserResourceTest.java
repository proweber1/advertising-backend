package resources;

import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.UserDao;
import com.advhouse.resservice.resources.UsersResource;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

/**
 * Этот тестовый класс проводит тесты ресурса для работы
 * с пользователями
 *
 * @author proweber1
 */
public class UserResourceTest {
    private static final UserDao dao = mock(UserDao.class);
    private static final UsersResource userResource = new UsersResource(dao);
    /**
     * Правило для тестирования ресурса
     */
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(userResource)
            .build();
    /**
     * Правило для тестирования исключений
     */
    @Rule
    public ExpectedException exp = ExpectedException.none();
    private User shamUser;

    /**
     * Перед каждым тестом делаем заглушку для вызова метода
     * save с искуственным пользователем
     */
    @Before
    public void setup() {
        shamUser = new User();
        shamUser.setId(1L);
        shamUser.setUsername("w.gusser@mail.ru");
        shamUser.setPassword("XWuiYdlj");
    }

    /**
     * После каждого теста збрасываем наш мок, для того
     * чтобы счетчик правильно работал
     */
    @After
    public void tearDown() {
        reset(dao);
    }

    /**
     * Проверка регистрации пользоватея с корректными данными, без
     * ошибок валидации
     */
    @Test
    public void registrationTest() {
        // Успешная регистрация
        when(dao.save(shamUser)).thenReturn(shamUser);
        User registration = userResource.registration(shamUser);

        Assertions.assertThat(registration).isNotNull();
        Assertions.assertThat(registration).isEqualTo(shamUser);
    }

    /**
     * Делаем регистрацию с пустыми данными и ожидаем
     * возвращения ошибок валидации
     */
    @Test
    public void validationErrorTest() {
        when(dao.save(shamUser)).thenReturn(shamUser);

        final User invalidRequest = new User();
        Response post = resources.target("/users").request().post(Entity.json(invalidRequest));

        Assertions.assertThat(post.getStatus()).isEqualTo(422);

        ValidationErrorMessage validationErrorMessage = post.readEntity(ValidationErrorMessage.class);
        Assertions.assertThat(validationErrorMessage).isNotNull();
        Assertions.assertThat(validationErrorMessage.getErrors())
                .containsOnly("password не может быть пусто", "username не может быть пусто");
    }

    /**
     * Проверяет ситуацию когда пользователь уже существует, ожидается
     * выброс bad request исключения с сообщением о том, что пользователь
     * уже существует
     */
    @Test
    public void userAlreadyExistTest() {
        exp.expect(BadRequestException.class);
        exp.expectMessage("User must be unique!");

        when(dao.isUserExist(shamUser)).thenReturn(true);
        userResource.registration(shamUser);
    }
}
