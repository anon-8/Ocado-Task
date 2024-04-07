package com.ocado.basket;

import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
public class ConfigLoader extends JsonLoader {
    public static @NotNull Map<String, List<String>> parseConfigAndGetDeliveryOptions(String absolutePathToConfigFile) throws IOException {

        JsonNode config = getObjectFromJson(absolutePathToConfigFile);

        validateItemsAmount(config);

        Map<String, List<String>> deliveryConfig = new HashMap<>();
        Set<String> seenItems = new HashSet<>();
        Set<String> seenDeliveryOptions = new HashSet<>();
        config.fields().forEachRemaining(entry -> {
            String itemName = entry.getKey();
            JsonNode itemDeliveryOptions = entry.getValue();

            validateAmountOfUniqueDeliveryOptions(itemDeliveryOptions, seenDeliveryOptions);

            validateDeliveryOptionsFormat(itemDeliveryOptions, itemName);

            List<String> deliveryOptions = new ArrayList<>();
            itemDeliveryOptions.forEach(deliveryOption -> deliveryOptions.add(deliveryOption.asText()));
            validateUniqueDeliveryOptionsForItem(deliveryOptions, itemName);
            deliveryConfig.put(itemName, deliveryOptions);

        });
        return deliveryConfig;
    }


    private static void validateAmountOfUniqueDeliveryOptions(JsonNode itemDeliveryOptions, Set<String> seenDeliveryOptions) {
        itemDeliveryOptions.forEach(deliveryOption -> seenDeliveryOptions.add(deliveryOption.asText()));
        if(seenDeliveryOptions.size() > 10){
            throw new IllegalArgumentException("Too many unique delivery options");
        }
    }
    private static void validateUniqueDeliveryOptionsForItem(List<String> deliveryOptions, String itemName) {
        Set<String> optionSet = new HashSet<>(deliveryOptions);
        if (optionSet.size() < deliveryOptions.size()) {
            throw new IllegalArgumentException("Duplicates in delivery options of item: " + itemName);
        }
    }
    private static void validateItemsAmount(@NotNull JsonNode config) {
        if(config.size() > 1000 || config.isEmpty()){
            throw new IllegalArgumentException("Provided object is too big or empty");
        }
    }
    private static void validateDeliveryOptionsFormat(@NotNull JsonNode itemDeliveryOptions, String itemName) {
        if (!itemDeliveryOptions.isArray()) {
            throw new IllegalArgumentException("Value for product: " + itemName + " is not a list.");
        }
    }
}
