import com.ocado.basket.JsonLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonLoaderTest {
    String absolutePathToEmptyJsonFile;
    String absolutePathToInvalidJsonFile;
    String absolutePathToValidJsonFile;
    public JsonLoaderTest() throws URISyntaxException {
        URL resource = JsonLoaderTest.class.getResource("jsonLoader");
        assertNotNull(resource);
        absolutePathToEmptyJsonFile = Paths.get(resource.toURI()) + "\\empty.json";
        absolutePathToInvalidJsonFile = Paths.get(resource.toURI()) + "\\invalid.json";
        absolutePathToValidJsonFile = Paths.get(resource.toURI()) + "\\valid.json";
    }

    @Test
    void shouldThrowExceptionWhenGivenEmptyOrNonexistentFile() {

        assertThrows(IOException.class, () -> JsonLoader.getObjectFromJson(absolutePathToEmptyJsonFile));
        assertThrows(IOException.class, () -> JsonLoader.getObjectFromJson("\\"));
    }
    @Test
    void shouldThrowExceptionWhenGivenInvalidJsonObject() {

        assertThrows(IOException.class, () -> JsonLoader.getObjectFromJson(absolutePathToInvalidJsonFile));
    }
}
