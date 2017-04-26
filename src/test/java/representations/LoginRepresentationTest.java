package representations;

import com.advhouse.resservice.api.LoginRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * Тестирование сериализации формы логина, обратно она не
 * серриализуется, по-этому смотрим только дессериализацию
 *
 * @author proweber1
 */
public class LoginRepresentationTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private static final LoginRepresentation login = new LoginRepresentation("proweber1@yandex.ru", "XWuiYdlj");

    /**
     * Проверяем что десерриализация работает правильно
     */
    @Test
    public void deserializationTest() throws IOException {
        final LoginRepresentation loginForm = MAPPER.readValue(fixture("fixtures/login.json"), LoginRepresentation.class);
        Assertions.assertThat(loginForm).isEqualTo(login);
    }
}
