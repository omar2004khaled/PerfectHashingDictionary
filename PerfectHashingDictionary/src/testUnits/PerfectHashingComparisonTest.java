package PerfectHashingDictionary.src.testUnits;

import PerfectHashingDictionary.src.DictionaryImplementation;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;

public class PerfectHashingComparisonTest {
    private static final int WARMUP_ITERATIONS = 3;
    private static final int TEST_ITERATIONS = 5;
    private static final int[] TEST_SIZES = {10, 100, 500, 1000};
    private final Random random = new Random();

    @Test
    public void comprehensiveComparison() throws Exception {
        System.out.println("Starting comprehensive comparison of perfect hashing techniques\n");
        
        for (int size : TEST_SIZES) {
            System.out.println("\n===== Testing with size: " + size + " =====");
            
            List<String> testData = generateTestData(size);
            testImplementation("O(n)", new ArrayList<>(testData), size);
            testImplementation("O(n^2)", new ArrayList<>(testData), size);
        }
    }

    private void testImplementation(String type, List<String> testData, int size) throws Exception {
        System.out.println("\nTesting " + type + " implementation:");

        // Metrics collection
        long totalInsertTime = 0;
        long totalSearchTime = 0;

        // Create a temporary file with test data
        String tempFilePath = writeTestDataToTempFile(testData);

        // Warmup phase
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            DictionaryImplementation dict = new DictionaryImplementation(type);
            dict.batchInsert(tempFilePath);
        }

        // Measurement phase
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            DictionaryImplementation dict = new DictionaryImplementation(type);

            // Measure insertion time
            long insertStart = System.nanoTime();
            dict.batchInsert(tempFilePath);
            long insertEnd = System.nanoTime();
            totalInsertTime += (insertEnd - insertStart);

            // Measure search time
            long searchStart = System.nanoTime();
            for (String key : testData) {
                assertTrue(dict.search(key));
            }
            long searchEnd = System.nanoTime();
            totalSearchTime += (searchEnd - searchStart);

            // Verify false searches
            List<String> nonExistent = generateNonExistentData(testData, Math.min(10, size / 10));
            for (String key : nonExistent) {
                assertFalse(dict.search(key));
            }
        }

        // Calculate averages
        double avgInsertTime = (totalInsertTime / TEST_ITERATIONS) / 1_000_000.0; // ms
        double avgSearchTime = (totalSearchTime / (TEST_ITERATIONS * testData.size())) / 1_000.0; // μs per search

        // Print results
        System.out.println("Average insertion time: " + String.format("%.3f", avgInsertTime) + " ms");
        System.out.println("Average search time: " + String.format("%.3f", avgSearchTime) + " μs");

        // Test dynamic operations for smaller datasets
        if (size <= 1000) {
            testDynamicOperations(type, testData);
        }
    }

    private void testDynamicOperations(String type, List<String> testData) throws IOException {
        // Create a temporary file with the initial test data
        String tempFilePath = writeTestDataToTempFile(testData);

        DictionaryImplementation dict = new DictionaryImplementation(type);
        dict.batchInsert(tempFilePath);

        // Generate new elements
        List<String> newElements = generateNonExistentData(testData, Math.min(100, testData.size()));

        // Measure insertion
        long insertStart = System.nanoTime();
        for (String elem : newElements) {
            dict.insert(elem);
        }
        long insertEnd = System.nanoTime();
        double avgInsertTime = (insertEnd - insertStart) / (newElements.size() * 1_000.0); // μs

        // Measure search after insertion
        long searchStart = System.nanoTime();
        for (String elem : newElements) {
            assertTrue(dict.search(elem));
        }
        long searchEnd = System.nanoTime();
        double avgSearchTime = (searchEnd - searchStart) / (newElements.size() * 1_000.0); // μs

        // Measure deletion
        long deleteStart = System.nanoTime();
        for (String elem : newElements) {
            dict.delete(elem);
        }
        long deleteEnd = System.nanoTime();
        double avgDeleteTime = (deleteEnd - deleteStart) / (newElements.size() * 1_000.0); // μs

        System.out.println("Dynamic operation performance:");
        System.out.println("  Insert: " + String.format("%.2f", avgInsertTime) + " μs/op");
        System.out.println("  Search: " + String.format("%.2f", avgSearchTime) + " μs/op");
        System.out.println("  Delete: " + String.format("%.2f", avgDeleteTime) + " μs/op");
    }

    private String writeTestDataToTempFile(List<String> testData) throws IOException {
        // Create a temporary file
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("testData", ".txt");

        // Write test data to the file
        java.nio.file.Files.write(tempFile, testData);

        // Return the file path as a string
        return tempFile.toAbsolutePath().toString();
    }

    private List<String> generateTestData(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(generateRandomString(5 + random.nextInt(10)));
        }
        return data;
    }

    private List<String> generateNonExistentData(List<String> existing, int count) {
        List<String> nonExistent = new ArrayList<>();
        while (nonExistent.size() < count) {
            String candidate = generateRandomString(5 + random.nextInt(10));
            if (!existing.contains(candidate)) {
                nonExistent.add(candidate);
            }
        }
        return nonExistent;
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}