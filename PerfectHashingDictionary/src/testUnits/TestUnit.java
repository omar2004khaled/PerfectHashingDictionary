package PerfectHashingDictionary.src.testUnits;
import PerfectHashingDictionary.src.DictionaryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestUnit {
    private DictionaryImplementation dictionary;

    @BeforeEach
    void setUp() {
        // Initialize the dictionary with O(n) method for testing
        dictionary = new DictionaryImplementation("O(n)");
    }

    @Test
    void testInsert() {
        // Test inserting a new string
        dictionary.insert("test1");
        assertTrue(dictionary.search("test1"), "The string 'test1' should be found after insertion.");

        // Test inserting a duplicate string
        dictionary.insert("test1");
        assertTrue(dictionary.search("test1"), "The duplicate string 'test1' should still be found.");
    }

    @Test
    void testDelete() {
        // Insert a string and then delete it
        dictionary.insert("test2");
        dictionary.delete("test2");
        assertFalse(dictionary.search("test2"), "The string 'test2' should not be found after deletion.");

        // Test deleting a non-existent string
        dictionary.delete("nonExistent");
        assertFalse(dictionary.search("nonExistent"), "The string 'nonExistent' should not be found.");
    }

    @Test
    void testSearch() {
        // Test searching for an existing string
        dictionary.insert("test3");
        assertTrue(dictionary.search("test3"), "The string 'test3' should be found.");

        // Test searching for a non-existent string
        assertFalse(dictionary.search("notInDictionary"), "The string 'notInDictionary' should not be found.");
    }
}
