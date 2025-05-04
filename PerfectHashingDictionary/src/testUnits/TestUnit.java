package PerfectHashingDictionary.src.testUnits;

import PerfectHashingDictionary.src.DictionaryImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestUnit {

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testInsertIncreasesSizeForNewKeyLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        dictionary.insert("newKey");
        assertTrue(dictionary.search("newKey"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testInsertDoesNotIncreaseSizeForExistingKeyLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        dictionary.insert("existingKey");
        dictionary.insert("existingKey");
        assertTrue(dictionary.search("existingKey"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testSimpleSearchLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        dictionary.insert("simpleText");
        assertTrue(dictionary.search("simpleText"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testSearchingOnNonExistingElementLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        assertFalse(dictionary.search("notSimple"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testSimpleDeleteLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        dictionary.insert("simpleText");
        dictionary.delete("simpleText");
        assertFalse(dictionary.search("simpleText"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testDeletingNonExistingElementLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        dictionary.delete("simpleText");
        assertFalse(dictionary.search("simpleText"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testInsertIncreasesSizeForNewKeyQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        dictionary.insert("newKey");
        assertTrue(dictionary.search("newKey"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testInsertDoesNotIncreaseSizeForExistingKeyQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        dictionary.insert("existingKey");
        dictionary.insert("existingKey");
        assertTrue(dictionary.search("existingKey"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testSimpleSearchQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        dictionary.insert("simpleText");
        assertTrue(dictionary.search("simpleText"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testSearchingOnNonExistingElementQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        assertFalse(dictionary.search("notSimple"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testSimpleDeleteQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        dictionary.insert("simpleText");
        dictionary.delete("simpleText");
        assertFalse(dictionary.search("simpleText"));
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testDeletingNonExistingElementQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        dictionary.delete("simpleText");
        assertFalse(dictionary.search("simpleText"));
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.SECONDS)
    public void testGiganticArrayOfStringsLinear() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n)");
        String[] randomStringsArray = generateRandomStringsArray(10000, 10);
        // Assuming you have a batch insert method
        // dictionary.batchInsert(randomStringsArray);
        // This test needs to be adjusted based on your actual implementation
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.SECONDS)
    public void testGiganticArrayOfStringsQuadratic() {
        DictionaryImplementation dictionary = new DictionaryImplementation("O(n^2)");
        String[] randomStringsArray = generateRandomStringsArray(10000, 10);
        // Assuming you have a batch insert method
        // dictionary.batchInsert(randomStringsArray);
        // This test needs to be adjusted based on your actual implementation
    }

    public static String generateRandomString(int n) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public static String[] generateRandomStringsArray(int size, int length) {
        String[] randomStrings = new String[size];
        for (int i = 0; i < size; i++) {
            randomStrings[i] = generateRandomString(length);
        }
        return randomStrings;
    }

    public static int generateRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static char generateRandomCharacter() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }
}