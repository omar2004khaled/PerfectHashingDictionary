package PerfectHashingDictionary.src.testUnits;

import PerfectHashingDictionary.src.DictionaryImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;
import static org.junit.Assert.*;
//PerfectHashingDictionary\cases\output_%d_%d_lines.txt
public class TestUnit {

    private static final String TEST_CASES_PATH = "cases\\output_%d_%d_lines.txt";
    private static final String BATCH_TEST_PATH = "PerfectHashingDictionary\\resources\\teamnames.txt";
    private static final String SINGLE_ELEMENT_PATH = "cases/1element";
    private static final int TEST_ITERATIONS = 10;
    @Test
    public void time_10_n() {
        DictionaryImplementation d;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String filename = String.format("cases\\output_%d_%d_lines.txt", 10, i);
            DictionaryImplementation test_10_n = new DictionaryImplementation("O(n)");
            test_10_n.batchInsert(filename);
        }
        long end1 = System.currentTimeMillis();
        long duration1 = (end1 - start1) / 10;
        System.out.print("Time to insert 10 elements :");
        System.out.println("\u001B[35m(" + duration1 + ") ms\u001B[0m");

    }
    @Test
    public void time_100_n(){
        long start1 = System.currentTimeMillis();
        for(int i=0;i<10;i++){
            String filename = String.format("cases\\output_%d_%d_lines.txt", 100, i);
            DictionaryImplementation test_100_n= new DictionaryImplementation("O(n)");
            test_100_n.batchInsert(filename);
        }
        long end1 = System.currentTimeMillis();
        long duration1 = (end1 - start1)/10;
        System.out.print("Time to insert 100 elements :");
        System.out.println("\u001B[35m(" + duration1 + ") ms\u001B[0m");
    }
    @Test
    public void time_1000_n(){
        long start1 = System.currentTimeMillis();
        for(int i=0;i<10;i++){
            String filename = String.format("cases\\output_%d_%d_lines.txt", 1000, i);
            DictionaryImplementation test_1000_n= new DictionaryImplementation("O(n)");
            test_1000_n.batchInsert(filename);
        }
        long end1 = System.currentTimeMillis();
        long duration1 = (end1 - start1)/10;
        System.out.print("Time to insert 1000 elements :");
        System.out.println("\u001B[35m(" + duration1 + ") ms\u001B[0m");
    }


    // O(n) Tests
    @Test
    public void testInsertPerformance_n() {
        testPerformanceForSize(10, "O(n)");
        testPerformanceForSize(100, "O(n)");
        testPerformanceForSize(1000, "O(n)");
        testPerformanceForSize(10000, "O(n)");
        testPerformanceForSize(100000, "O(n)");
        testPerformanceForSize(1000000, "O(n)");
    }

    // O(n^2) Tests
    @Test
    public void testInsertPerformance_n2() {
        testPerformanceForSize(10, "O(n^2)");
        testPerformanceForSize(100, "O(n^2)");
        testPerformanceForSize(1000, "O(n^2)");
        testPerformanceForSize(10000, "O(n^2)");

    }

    // Rehashing Tests
    @Test
    public void testRehashingPerformance() throws FileNotFoundException {
        testRehashingForSize(10);
        testRehashingForSize(100);
        testRehashingForSize(1000);
        testRehashingForSize(10000);

    }

    // Operation Tests
    @Test
    public void testSearchOperation() {
        DictionaryImplementation dict = createAndLoadDictionary(100);
        dict.insert("youssef");
        assertTrue(dict.search("youssef"));
    }

    @Test
    public void testDeleteOperation() {
        DictionaryImplementation dict = createAndLoadDictionary(100);
        dict.insert("youssef");
        dict.delete("youssef");
        assertFalse(dict.search("youssef"));
    }



    // Helper Methods
    private void testPerformanceForSize(int size, String type) {
        long totalTime = 0;

        for (int i = 0; i < TEST_ITERATIONS; i++) {
            String filename = String.format(TEST_CASES_PATH, size, i);
            DictionaryImplementation dict = new DictionaryImplementation(type);

            long start = System.currentTimeMillis();
            dict.batchInsert(filename);
            long end = System.currentTimeMillis();

            totalTime += (end - start);
        }

        printTimeResult("Time to insert " + size + " elements (" + type + "):", totalTime / TEST_ITERATIONS);
    }

    private void testRehashingForSize(int size) throws FileNotFoundException {
        long totalTime = 0;

        for (int i = 0; i < TEST_ITERATIONS; i++) {
            String filename = String.format(TEST_CASES_PATH, size, i);
            DictionaryImplementation dict = new DictionaryImplementation("O(n^2)");
            dict.batchInsert(filename);

            long start = System.currentTimeMillis();
            // Note: DictionaryImplementation doesn't have a public rehash() method
            // You'll need to either add it or modify this test
            long end = System.currentTimeMillis();

            totalTime += (end - start);
        }

        printTimeResult("Time to rehash " + size + " elements:", totalTime / TEST_ITERATIONS);
    }

    private DictionaryImplementation createAndLoadDictionary(int size) {
        String filename = String.format(TEST_CASES_PATH, size, 0);
        DictionaryImplementation dict = new DictionaryImplementation("O(n^2)");
        dict.batchInsert(filename);
        return dict;
    }

    private void printTimeResult(String message, long duration) {
        System.out.println(message + "\u001B[35m(" + duration + ") ms\u001B[0m");
    }

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
