package representations;

import com.advhouse.resservice.core.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * Тест который проверяет сериализацию и десериализацию
 * класса пользователя
 *
 * @author proweber1
 */
public class UserRepresentationTest {
    private static ObjectMapper MAPPER;
    private static User user;

    /**
     * Перед всеми тестами создаем общую модель пользователя и инициализируем
     * маппер, чтоб не дублировать его нигде
     */
    @BeforeClass
    public static void beforeAll() {
        // Инициализация пользователя
        user = new User();
        user.setId(1L);
        user.setUsername("w.gusser@mail.ru");

        // Инициализация маппера
        MAPPER = Jackson.newObjectMapper();
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Проверяем что модель пользователя сериализуется правильно
     *
     * @throws Exception Ошибка обработки JSON'а
     */
    @Test
    public void serializesToJSON() throws Exception {
        final String userFixture = fixture("fixtures/user.json");
        final User fixtureDeserializePerson = MAPPER.readValue(userFixture, User.class);

        final String expected = MAPPER.writeValueAsString(fixtureDeserializePerson);

        Assertions.assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }

    /**
     * Проверяем что наш класс User правильно десериализуется из JSON в
     * нашу сущность пользователя
     *
     * @throws Exception Ошибка десериализации из JSON'а
     */
    @Test
    public void deserializesFromJSON() throws Exception {
        final User fixture = MAPPER.readValue(fixture("fixtures/user.json"), User.class);
        Assertions.assertThat(fixture).isEqualTo(user);
    }
}
