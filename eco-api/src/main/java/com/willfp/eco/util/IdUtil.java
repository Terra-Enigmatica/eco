package com.willfp.eco.util;

import org.bukkit.NamespacedKey;

import java.text.Normalizer;
import java.util.Locale;

public final class IdUtil {
    private IdUtil() {}

    /**
     * Полная нормализация ID под Bukkit (ASCII only).
     */
    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        String result = input.toLowerCase(Locale.ENGLISH);

        // 🔹 Транслитерация кириллицы
        result = transliterate(result);

        // 🔹 Убираем диакритику (é → e и т.д.)
        result = Normalizer.normalize(result, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 🔹 Заменяем разделители
        result = result
                .replace(" ", "_")
                .replace("-", "_")
                .replace(".", "_");

        // 🔹 Оставляем только допустимые символы
        result = result.replaceAll("[^a-z0-9_/.-]", "_");

        // 🔹 Убираем дубли _
        result = result.replaceAll("_+", "_");

        // 🔹 Обрезка
        if (result.length() > 100) {
            result = result.substring(0, 100);
        }

        // 🔹 Защита от пустого результата
        if (result.isEmpty()) {
            result = "id";
        }

        return result;
    }

    /**
     * Безопасное создание NamespacedKey
     */
    public static NamespacedKey key(String input) {
        return NamespacedKey.minecraft(normalize(input));
    }

    /**
     * Транслитерация кириллицы → латиница
     */
    private static String transliterate(String input) {
        return input
                .replace("а", "a").replace("б", "b").replace("в", "v")
                .replace("г", "g").replace("д", "d").replace("е", "e")
                .replace("ё", "e").replace("ж", "zh").replace("з", "z")
                .replace("и", "i").replace("й", "y").replace("к", "k")
                .replace("л", "l").replace("м", "m").replace("н", "n")
                .replace("о", "o").replace("п", "p").replace("р", "r")
                .replace("с", "s").replace("т", "t").replace("у", "u")
                .replace("ф", "f").replace("х", "h").replace("ц", "ts")
                .replace("ч", "ch").replace("ш", "sh").replace("щ", "sch")
                .replace("ы", "y").replace("э", "e").replace("ю", "yu")
                .replace("я", "ya");
    }
}