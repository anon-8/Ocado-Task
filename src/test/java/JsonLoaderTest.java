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
    String absolutePathToJsonList;
    String absolutePathToJsonObject;
    public JsonLoaderTest() throws URISyntaxException {
        URL resource = JsonLoaderTest.class.getResource("jsonLoader");
        assertNotNull(resource);
        absolutePathToEmptyJsonFile = Paths.get(resource.toURI()) + "\\empty.json";
        absolutePathToJsonList = Paths.get(resource.toURI()) + "\\list.json";
        absolutePathToJsonObject = Paths.get(resource.toURI()) + "\\object.json";
    }
    @Test
    void shouldThrowExceptionWhenGivenEmptyOrNonexistentFile() {

        assertThrows(IOException.class, () -> JsonLoader.getObjectFromJson(absolutePathToEmptyJsonFile));
    }
    @Test
    void shouldThrowExceptionWhenGivenInvalidJsonObject() {

        assertThrows(IOException.class, () -> JsonLoader.getObjectFromJson(absolutePathToJsonList));
    }
}
