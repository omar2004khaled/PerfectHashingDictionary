package PerfectHashingDictionary.src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class NMethod<T> implements perfectHashing<T>  {
    private ArrayList<T>[] firstLevelTable;
    private NSquareMethod<T>[] secondLevelTables;
    private int[][] universalMatrix;
    private ArrayList<T> AllElements = new ArrayList<>();
    private int n;
    
    @Override
    public boolean insert(T key) {
        int index = computeFirstLevelHash(key);
        if (firstLevelTable[index] == null || firstLevelTable[index].isEmpty()) {
            firstLevelTable[index] = new ArrayList<>();
            firstLevelTable[index].add(key);
            AllElements.add(key);
            n++;
            return true;
        }
        int size = firstLevelTable[index].size();
        if (AllElements.contains(key))
            return false;
        AllElements.add(key);
        n++;
        secondLevelTables[index].insert(key);

        return true;
    }

    @Override
    public boolean search(T key) {
        int index = computeFirstLevelHash(key);
        if (firstLevelTable[index] == null || !firstLevelTable[index].contains(key))
            return false;
        if (secondLevelTables[index] != null) {
            return secondLevelTables[index].search(key);
        }
        return true;
    }

    @Override
    public boolean delete(T key) {
        int index = computeFirstLevelHash(key);
        if (firstLevelTable[index] == null || !firstLevelTable[index].contains(key))
            return false;
        if (secondLevelTables[index] != null) {
            firstLevelTable[index].remove(key);
            if (secondLevelTables[index].delete(key)) {
                AllElements.remove(key);
                n--;
                return true;
            }
            return false;
        }
        firstLevelTable[index].remove(key);
        AllElements.remove(key);
        n--;
        return true;
    }

    @Override
    public int getNumberOfRehashing() {
        int NumberOfRehashing=0;
        for(NSquareMethod<T> table:secondLevelTables){
            NumberOfRehashing +=table.getNumberOfRehashing();
        }
        return NumberOfRehashing;
    }

    public void firstLevelHashing(int size){
        firstLevelTable = new ArrayList[size ];
        secondLevelTables = new NSquareMethod[size];
        universalMatrix = new int[(int) Math.floor(Math.log10(size) / Math.log10(2))][64];
        randomizeMatrix();
    }


    private void randomizeMatrix(){
        int row = universalMatrix.length;
        int col = universalMatrix[0].length;
        Random random = new Random();
        for(int i = 0 ; i < row ; ++i){
            for(int j = 0 ; j < col ; ++j){
                universalMatrix[i][j] = random.nextInt(2);
            }
        }
    }

    private long computeHash(T key) {
        // Hashing logic based on key type
        switch (key.getClass().getSimpleName()) {
            case "String" :
                return convertStringToLong((String) key);
            case "Integer" : 
                return (Integer) key;
            case "Character" :
                return(long) (Character) key;
            default : 
                throw new IllegalArgumentException("Unsupported key type: " + key.getClass().getName());
        }
    }

    private int computeFirstLevelHash(T key){
        long hashedKey = computeHash(key);
        int[] binaryHashed = longToBinaryArray(hashedKey);
        int i =multiplyBinaryVectorWithMatrix(binaryHashed);
        return i;
    }

    public static int[] longToBinaryArray(long number) {
        int[] binary = new int[64]; // long has 64 bits
        for (int i = 63; i >= 0; i--) {
            binary[63 - i] = (int)((number >> i) & 1);
        }
        return binary;
    }

    public int  multiplyBinaryVectorWithMatrix(int[] vector) {
        int rows = universalMatrix.length;
        int[] result = new int[rows];
        for (int i = 0; i < rows; i++) {
            int sum = 0;
            for (int j = 0; j < 64; j++) {
                sum += universalMatrix[i][j] * vector[j];
            }
        result[i] = sum % 2; // binary result
        }
        int result2 = 0;
        int length = rows;
        for (int i = 0; i < length; i++) {
            if (result[i] == 1) {
                result2 |= 1L << (length - 1 - i);
            }
        }
        return result2;
    }

    private long convertStringToLong(String key){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(key.getBytes());
            BigInteger bigInt = new BigInteger(1, hashBytes);
            return bigInt.longValue();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
