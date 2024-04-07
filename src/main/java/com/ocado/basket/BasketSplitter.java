package com.ocado.basket;

import java.io.IOException;
import java.util.*;

public class BasketSplitter {
    private final Map<String, List<String>> deliveryOptions;

    public BasketSplitter(String absolutePathToConfigFile) throws IOException {
        this.deliveryOptions = ConfigLoader.parseConfigAndGetDeliveryOptions(absolutePathToConfigFile);
    }
    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> itemsMap = getItemsDeliveriesMap(items);
        List<String> ranking = getRanking(itemsMap);
        return getDeliveryGroups(items, ranking);
    }

    private Map<String, List<String>> getDeliveryGroups(List<String> items, List<String> ranking) {
        Map<String, List<String>> deliveryGroups = new HashMap<>();
        items.forEach(item -> ranking.stream()
                .filter(option -> deliveryOptions.get(item).contains(option))
                .findFirst()
                .ifPresent(option ->
                        deliveryGroups.computeIfAbsent(option, k -> new ArrayList<>()).add(item)));
        return deliveryGroups;
    }

    private static List<String> getRanking(Map<String, List<String>> itemsMap) {
        List<String> ranking = new ArrayList<>();
        Map<String, List<String>> remainingItemsMap = new HashMap<>(itemsMap);

        while (!remainingItemsMap.isEmpty()) {
            Map<String, Integer> deliveryOptionCount = getDeliveryOptionsCount(remainingItemsMap);
            if (!deliveryOptionCount.isEmpty()) {
                String prevailingOption = Collections.max(deliveryOptionCount.entrySet(), Map.Entry.comparingByValue()).getKey();
                ranking.add(prevailingOption);
                remainingItemsMap.entrySet().removeIf(entry -> entry.getValue().contains(prevailingOption));
            } else {
                break;
            }
        }
        return ranking;
    }
    private static Map<String, Integer> getDeliveryOptionsCount(Map<String, List<String>> itemsMap) {
        Map<String, Integer> deliveryOptionCount = new HashMap<>();
        for (List<String> options : itemsMap.values()) {
            if (options != null) {
                for (String option : options) {
                    deliveryOptionCount.put(option, deliveryOptionCount.getOrDefault(option, 0) + 1);
                }
            }
        }
        return deliveryOptionCount;
    }

    private Map<String, List<String>> getItemsDeliveriesMap(List<String> items) {
        Map<String, List<String>> itemsDeliveriesMap = new HashMap<>();
        for (String item : items) {
            validateItem(item);
            itemsDeliveriesMap.put(item, deliveryOptions.get(item));
        }
        return itemsDeliveriesMap;
    }
    private void validateItem(String item) {
        if (!deliveryOptions.containsKey(item)) {
            throw new IllegalArgumentException("Item '" + item + "' does not exist in the configuration.");
        }
    }
}
