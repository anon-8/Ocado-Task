import com.ocado.basket.ConfigLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {
    String absolutePathToConfigFile;
    String absolutePathToInvalidConfigFile;
    String absolutePathToDuplicateDeliveryMethodForItemConfigFile;
    public ConfigLoaderTest() throws URISyntaxException {
        URL resource = ConfigLoaderTest.class.getResource("config");
        assertNotNull(resource);
        absolutePathToConfigFile = Paths.get(resource.toURI()) + "\\config.json";
        absolutePathToInvalidConfigFile = Paths.get(resource.toURI()) + "\\invalidConfig.json";
        absolutePathToDuplicateDeliveryMethodForItemConfigFile = Paths.get(resource.toURI()) + "\\duplicateDeliveryMethodForItemConfig.json";
    }
    @Test
    void shouldThrowExceptionWhenGivenInvalidConfigFile() {

        assertThrows(IllegalArgumentException.class, () -> ConfigLoader.parseConfigAndGetDeliveryOptions(absolutePathToInvalidConfigFile));
    }
    @Test
    void shouldThrowExceptionWhenGivenConfigFileItemHasDuplicateDeliveryOptions() {

        assertThrows(IllegalArgumentException.class, () -> ConfigLoader.parseConfigAndGetDeliveryOptions(absolutePathToDuplicateDeliveryMethodForItemConfigFile));
    }
    @Test
    void shouldLoadValidConfigCorrectly() throws IOException {
        Map<String, List<String>> deliveryOptions = ConfigLoader.parseConfigAndGetDeliveryOptions(absolutePathToConfigFile);

        assertEquals(10, deliveryOptions.size());
        assertTrue(deliveryOptions.containsKey("Chocolate - Unsweetened"));
        assertEquals(deliveryOptions.get("Chocolate - Unsweetened"), Arrays.asList("In-store pick-up", "Parcel locker", "Same day delivery", "Pick-up point", "Courier", "Express Collection", "Mailbox delivery", "Next day shipping"));
    }

}
