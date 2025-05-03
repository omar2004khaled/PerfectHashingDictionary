package PerfectHashingDictionary.src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class NMethod<T> implements perfectHashing<T> {
    private ArrayList<T>[] firstLevelTable;
    private NSquareMethod<T>[] secondLevelTables;
    private int[][] universalMatrix;
    private ArrayList<T> allElements = new ArrayList<>();
    private int n;
    
    @Override
    public boolean insert(T key) {
        int index = computeFirstLevelHash(key);
        
        // Initialize first level slot if null
        if (firstLevelTable[index] == null) {
            firstLevelTable[index] = new ArrayList<>();
        }
        
        // Check if already exists
        if (allElements.contains(key)) {
            return false;
        }
        
        // Add to collections
        firstLevelTable[index].add(key);
        allElements.add(key);
        n++;
        
        // Handle second level hashing if needed
        if (firstLevelTable[index].size() > 1) {
            if (secondLevelTables[index] == null) {
                secondLevelTables[index] = new NSquareMethod<>();
            }
            // Rebuild the entire second level table for this slot
            rebuildSecondLevelTable(index);
        }
        
        return true;
    }

    private void rebuildSecondLevelTable(int index) {
        secondLevelTables[index] = new NSquareMethod<>();
        for (T element : firstLevelTable[index]) {
            secondLevelTables[index].insert(element);
        }
    }

    @Override
    public boolean search(T key) {
        int index = computeFirstLevelHash(key);
        if (firstLevelTable[index] == null || !firstLevelTable[index].contains(key)) {
            return false;
        }
        if (secondLevelTables[index] != null) {
            return secondLevelTables[index].search(key);
        }
        return true;
    }

    @Override
    public boolean delete(T key) {
        int index = computeFirstLevelHash(key);
        if (firstLevelTable[index] == null || !firstLevelTable[index].contains(key)) {
            return false;
        }
        
        firstLevelTable[index].remove(key);
        allElements.remove(key);
        n--;
        
        // Update second level table if exists
        if (secondLevelTables[index] != null) {
            if (firstLevelTable[index].size() <= 1) {
                secondLevelTables[index] = null;
            } else {
                rebuildSecondLevelTable(index);
            }
        }
        
        return true;
    }

    @Override
    public int getNumberOfRehashing() {
        int numberOfRehashing = 0;
        if (secondLevelTables != null) {
            for (NSquareMethod<T> table : secondLevelTables) {
                if (table != null) {
                    numberOfRehashing += table.getNumberOfRehashing();
                }
            }
        }
        return numberOfRehashing;
    }

    public void firstLevelHashing(int size) {
        firstLevelTable = new ArrayList[size];
        secondLevelTables = new NSquareMethod[size];
        int b = (int) Math.ceil(Math.log(size) / Math.log(2));
        universalMatrix = new int[b][64];
        randomizeMatrix();
    }

    private void randomizeMatrix() {
        Random random = new Random();
        for (int i = 0; i < universalMatrix.length; i++) {
            for (int j = 0; j < universalMatrix[0].length; j++) {
                universalMatrix[i][j] = random.nextInt(2);
            }
        }
    }

    private long computeHash(T key) {
        switch (key.getClass().getSimpleName()) {
            case "String":
                return convertStringToLong((String) key);
            case "Integer":
                return (Integer) key;
            case "Character":
                return (long) (Character) key;
            default:
                throw new IllegalArgumentException("Unsupported key type: " + key.getClass().getName());
        }
    }

    private int computeFirstLevelHash(T key) {
        long hashedKey = computeHash(key);
        int[] binaryHashed = longToBinaryArray(hashedKey);
        return multiplyBinaryVectorWithMatrix(binaryHashed);
    }

    private int[] longToBinaryArray(long number) {
        int[] binary = new int[64];
        for (int i = 0; i < 64; i++) {
            binary[63 - i] = (int) ((number >> i) & 1);
        }
        return binary;
    }

    private int multiplyBinaryVectorWithMatrix(int[] vector) {
        int[] result = new int[universalMatrix.length];
        for (int i = 0; i < universalMatrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < 64; j++) {
                sum += universalMatrix[i][j] * vector[j];
            }
            result[i] = sum % 2;
        }
        return binaryToDecimal(result);
    }

    private int binaryToDecimal(int[] bits) {
        int result = 0;
        for (int bit : bits) {
            result = (result << 1) | bit;
        }
        return result;
    }

    private long convertStringToLong(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(key.getBytes());
            BigInteger bigInt = new BigInteger(1, hashBytes);
            return bigInt.longValue();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}