import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ocado.basket.BasketSplitter;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {

    String absolutePathToConfigFile;
    public BasketSplitterTest() throws URISyntaxException {
        URL Resource = BasketSplitterTest.class.getResource("config");
        assertNotNull(Resource);
        absolutePathToConfigFile = Paths.get(Resource.toURI()) + "\\config.json";
    }

    @Test
    void testEmptyBasket() throws IOException {
        BasketSplitter splitter = new BasketSplitter(absolutePathToConfigFile);
        List<String> items = List.of();

        Map<String, List<String>> groups = splitter.split(items);

        assertTrue(groups.isEmpty());
    }
    @Test
    void testSingleProductBasket() throws IOException {
        BasketSplitter splitter = new BasketSplitter(absolutePathToConfigFile);
        List<String> items = List.of("Cookies Oatmeal Raisin");

        Map<String, List<String>> groups = splitter.split(items);

        assertEquals(1, groups.size());
        assertTrue(groups.containsKey("Pick-up point"));
        assertEquals(items, groups.get("Pick-up point"));
    }

    @Test
    void testDuplicatedProductsInBasket() throws IOException {
        BasketSplitter splitter = new BasketSplitter(absolutePathToConfigFile);
        List<String> items = Arrays.asList("Cookies Oatmeal Raisin", "Cookies Oatmeal Raisin");

        Map<String, List<String>> groups = splitter.split(items);

        assertEquals(1, groups.size());
        assertTrue(groups.containsKey("Pick-up point"));
        assertEquals(2, groups.get("Pick-up point").size());
        assertTrue(groups.get("Pick-up point").contains("Cookies Oatmeal Raisin"));
    }

    @Test
    void testMultipleProductsWithDifferentDeliveryOptions() throws IOException {
        BasketSplitter splitter = new BasketSplitter(absolutePathToConfigFile);
        List<String> items = Arrays.asList("Cookies Oatmeal Raisin", "Cheese Cloth", "English Muffin", "Ecolab - Medallion", "Cheese - St. Andre");

        Map<String, List<String>> groups = splitter.split(items);

        assertEquals(2, groups.size());
        assertTrue(groups.containsKey("Pick-up point"));
        assertTrue(groups.containsKey("In-store pick-up"));
        assertEquals(groups.get("Pick-up point"), Arrays.asList("Cookies Oatmeal Raisin", "Cheese Cloth", "Cheese - St. Andre"));
        assertEquals(groups.get("In-store pick-up"), Arrays.asList("English Muffin", "Ecolab - Medallion"));
    }
    @Test
    void shouldThrowExceptionWhenGivenNonexistentProduct() throws IOException {
        BasketSplitter splitter = new BasketSplitter(absolutePathToConfigFile);
        List<String> items = List.of("Nonexistent Product");

        assertThrows(IllegalArgumentException.class, () -> splitter.split(items));
    }
    @Test
    void shouldThrowExceptionWhenGivenNullProducts() throws IOException {
        BasketSplitter splitter = new BasketSplitter(absolutePathToConfigFile);

        assertThrows(NullPointerException.class, () -> splitter.split(null));
    }
}
