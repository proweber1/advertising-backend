package representations;

import com.advhouse.resservice.core.AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static io.dropwizard.testing.FixtureHelpers.fixture;

/**
 * Проверка модели токенов на то, что она правильно сериализуется
 * в JSON модели
 *
 * @author proweber1
 */
public class OAuth2RepresentationTest {

    /**
     * Проверяем что наша модель токенов сериализуется в JSON правильно, так
     * как я предполагаю
     *
     * @throws IOException Ошибка сериализации
     */
    @Test
    public void serializationTest() throws IOException {
        final ObjectMapper mapper = Jackson.newObjectMapper();
        // 2017-02-02 10:00:59
        final Calendar calendar = new GregorianCalendar(2017, 2, 2, 10, 0, 59);
        final AccessToken mockToken = new AccessToken("mock_access_token", calendar);

        final String tokenFixture = fixture("fixtures/oauth2.json");
        final AccessToken fixtureDeserializePerson = mapper.readValue(tokenFixture, AccessToken.class);
        final String expected = mapper.writeValueAsString(fixtureDeserializePerson);

        Assertions.assertThat(mapper.writeValueAsString(mockToken)).isEqualTo(expected);
    }
}
