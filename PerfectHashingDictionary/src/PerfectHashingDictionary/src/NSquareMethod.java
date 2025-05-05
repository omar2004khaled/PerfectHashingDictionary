package PerfectHashingDictionary.src.PerfectHashingDictionary.src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class NSquareMethod<T> implements perfectHashing<T>  {
    private ArrayList<T>[] table;
    private int n ; 
    private int m = 0; // current number of inserted elements
    private final int u = 63; // max length for key representation
    private int[][] hashMatrix;
    private int numberOfRehashing = 0;
    double LOAD_FACTOR = 0.75;

    public NSquareMethod() {
        n = 4;
        table = new ArrayList[n * n];
        generateNewMatrix();
    }

    private void generateNewMatrix() {
        int b = (int) Math.ceil(Math.log(n * n) / Math.log(2));
        hashMatrix = new int[b][u];
        Random rand = new Random();
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < u; j++) {
                hashMatrix[i][j] = rand.nextBoolean() ? 1 : 0;
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

    private long convertStringToLong(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(key.getBytes());
            BigInteger bigInt = new BigInteger(1, hashBytes);
            return bigInt.longValue() & Long.MAX_VALUE; // keep it positive
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int[] toBinary(long value, int length) {
        int[] bits = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            bits[i] = (int) (value & 1);
            value >>= 1;
        }
        return bits;
    }

    private int hashIndex(T key) {
        long keyLong = computeHash(key);
        int[] keyBits = toBinary(keyLong, u);
        int[] result = new int[hashMatrix.length];

        for (int i = 0; i < hashMatrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < u; j++) {
                sum += hashMatrix[i][j] * keyBits[j];
            }
            result[i] = sum % 2;
        }

        return binaryToDecimal(result);  //index in hashtable in decimal
    }

    private int binaryToDecimal(int[] bits) {
        int result = 0;
        for (int bit : bits) {
            result = (result << 1) | bit;
        }
        return result;
    }

    @Override
    public boolean insert(T key) {
        if (search(key)) return false;

        if ((double) (m) / (n * n) >= LOAD_FACTOR) {
            n *= 2;  // Double n
            rehash();
        }

        while (true) {
            int index = hashIndex(key);
            if (table[index] == null) {
                table[index] = new ArrayList<>();
                table[index].add(key);
                m++;
                return true;
            } else if (computeHash(table[index].get(0)) == computeHash(key)) {
                return false; // same key
            } else {
                n *= 2;
                rehash();
            }
        }
    }

    private void rehash() {
        numberOfRehashing++;
        ArrayList<T> allKeys = new ArrayList<>();
        for (ArrayList<T> slot : table) {
            if (slot != null) {
                allKeys.addAll(slot);
            }
        }
    
        allKeys.add(null); // reserve slot for the new key
    
        boolean success;
        do {
            success = true;
            generateNewMatrix();
            table = new ArrayList[n * n];
    
            for (T key : allKeys) {
                if (key == null) continue;
                int index = hashIndex(key);
                if (table[index] == null) {
                    table[index] = new ArrayList<>();
                    table[index].add(key);
                } else {
                    success = false;
                    break;
                }
            }
        } while (!success);
    }

    @Override
    public boolean search(T key) {
        int index = hashIndex(key);
        return table[index] != null && computeHash(table[index].get(0)) == computeHash(key);
    }

    @Override
    public boolean delete(T key) {
        int index = hashIndex(key);
        if (table[index] != null && computeHash(table[index].get(0)) == computeHash(key)) {
            table[index] = null;
            m--;
            return true;
        }
        return false;
    }

    @Override
    public int getNumberOfRehashing() {
        return numberOfRehashing;
    }

    @Override
    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print("Index " + i + ": ");
            if (table[i] != null) {
                for (T key : table[i]) {
                    System.out.print(key + " ");
                }
            }
            System.out.println();
        }
    }
}