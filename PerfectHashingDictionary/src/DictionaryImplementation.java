package PerfectHashingDictionary.src;

import PerfectHashingDictionary.src.PerfectHashingDictionary.src.NMethod;
import PerfectHashingDictionary.src.PerfectHashingDictionary.src.NSquareMethod;
import PerfectHashingDictionary.src.PerfectHashingDictionary.src.perfectHashing;
import java.io.IOException;

public class DictionaryImplementation {
    private final perfectHashing<String> dictionary;

    public DictionaryImplementation(String typeD) {
        if (typeD.equals("O(n)")) {
            dictionary = new NMethod<>();
            ((NMethod<String>) dictionary).firstLevelHashing(4); // Initialize with initial size
        } else if (typeD.equals("O(n^2)")) {
            dictionary = new NSquareMethod<>();
        } else {
            throw new IllegalArgumentException("Invalid dictionary type.");
        }
    }

    public synchronized void insert(String toInsert) {
        boolean res = dictionary.insert(toInsert);
        if(res) {
            System.out.println("(" + toInsert + ")" + "\u001B[32m Successfully INSERTED ✅\u001B[0m");
            if(dictionary instanceof NSquareMethod && ((NSquareMethod<String>) dictionary).getNumberOfRehashing() > 0) {
                System.out.println("\u001B[33m Table Rehashed\u001B[0m");
            }
            //dictionary.printTable();
            //System.out.println(dictionary.getNumberOfRehashing());
        } else {
            System.out.println("\u001B[33m Already Exist\u001B[0m ");
        }
    }

    public synchronized void delete(String toDelete) {
        if (dictionary.delete(toDelete)) {
            System.out.println("(" + toDelete + ")" + "\u001B[32m Successfully DELETED ✅\u001B[0m");
        } else {
            System.out.println("(" + toDelete + ")" + "\u001B[31m Not found ❌\u001B[0m");
        }
    }

    public boolean search(String toSearch) {
        if (dictionary.search(toSearch)) {
            System.out.println("\u001B[32m Found in dictionary ✅\u001B[0m");
            return true;
        } else {
            System.out.println("\u001B[31m Not found in dictionary ❌\u001B[0m");
            return false;
        }
    }

    public void batchInsert(String fileToInsert) {
        try {
            // For simplicity, we'll read the file and insert line by line
            // In a real implementation, you might want to optimize this
            java.nio.file.Path path = java.nio.file.Paths.get(fileToInsert);
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            
            int insertedCount = 0;
            int failedCount = 0;
            int rehashCount = 0;
            
            for (String line : lines) {
                boolean result = dictionary.insert(line.trim());
                if (result) {
                    insertedCount++;
                    if (dictionary instanceof NSquareMethod) {
                        NSquareMethod<String> nsq = (NSquareMethod<String>) dictionary;
                        if (nsq.getNumberOfRehashing() > rehashCount) {
                            rehashCount = nsq.getNumberOfRehashing();
                        }
                    }
                } else {
                    failedCount++;
                }
            }
            
            System.out.println("Inserted successfully: " + insertedCount + " strings.");
            if (failedCount != 0) {
                System.out.println("Failed to insert: " + failedCount + " strings.");
            }
            if (rehashCount != 0) {
                System.out.println("Table Rehashed: " + rehashCount + " time(s).");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void batchDelete(String fileToDelete) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(fileToDelete);
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            
            int deletedCount = 0;
            int failedCount = 0;
            
            for (String line : lines) {
                boolean result = dictionary.delete(line.trim());
                if (result) {
                    deletedCount++;
                } else {
                    failedCount++;
                }
            }
            
            System.out.println("Deleted successfully: " + deletedCount + " strings.");
            if (failedCount != 0) {
                System.out.println("Failed to delete: " + failedCount + " strings.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}