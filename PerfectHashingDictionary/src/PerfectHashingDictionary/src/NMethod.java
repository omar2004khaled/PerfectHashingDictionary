package PerfectHashingDictionary.src.PerfectHashingDictionary.src;

import java.util.ArrayList;
import java.util.Random;

public class NMethod<T> implements perfectHashing<T>  {
    private ArrayList<T>[] firstLevelTable;
    private NSquareMethod<T>[] secondLevelTables;
    private int[][] universalMatrix;
    private int n;
    private int hashSize;
    private long NumberOfrehash=0;
    private int  numberofNrehash=0;
    private long [] editedMatrix;
    @Override
    public boolean insert(T key) {
        if(n>=hashSize){
            NumberOfrehash =getNumberOfRehashing();
            hashSize *=2;
            rehash(hashSize);
        }    
        int index = computeFirstLevelHash(key);
        
        if (firstLevelTable[index] == null || firstLevelTable[index].isEmpty()) {
            firstLevelTable[index] = new ArrayList<>();
        }

        if (secondLevelTables[index] == null) {
            secondLevelTables[index] = new NSquareMethod<>(); 
        }
        if (secondLevelTables[index].insert(key)){
            firstLevelTable[index].add(key);
            n++;
            return true;
        }
        else{
            return false;
        }
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
                n--;
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public int getNumberOfRehashing() {
        int NumberOfRehashing=0;
        for(NSquareMethod<T> table:secondLevelTables){
            if(table!=null)
                NumberOfRehashing +=table.getNumberOfRehashing();
        }
        NumberOfRehashing +=NumberOfrehash;
        return NumberOfRehashing;
    }

    public int getNrehash(){
        return numberofNrehash;
    }
    
    public int biggestUse(){
        int maxssize=0;
        int[] myarray =new int[11];
        
        for(ArrayList<T> ta:firstLevelTable){
            if(ta!=null){
                myarray[ta.size()]++;
                if(maxssize<ta.size())maxssize=ta.size();
            }
            
        }
        for (int i=0;i<=10;i++) System.out.println(i+"get "+myarray[i]);
        return maxssize;
    }
    public void firstLevelHashing(int size){
        hashSize=size;
        firstLevelTable = new ArrayList[size ];
        secondLevelTables = new NSquareMethod[size];
        universalMatrix = new int[(int) Math.floor(Math.log10(size) / Math.log10(2))][64];
        editedMatrix =new  long[(int) Math.floor(Math.log10(size) / Math.log10(2))];
        randomizeMatrix();
    }

    private void rehash(int size){
        numberofNrehash ++;
        ArrayList<T>[] oldElemnts= firstLevelTable.clone();
        n = 0;
        firstLevelHashing(size);
        for(ArrayList<T> oldElement:oldElemnts){
            if(oldElement!=null){
                for (T element : oldElement) {
                    insert(element);
                }
            }
        }
        
    }

    private void randomizeMatrix(){
        int row = universalMatrix.length;
        Random random = new Random();
        for(int i=0;i<row;i++){
            editedMatrix[i] = Math.abs(random.nextLong());
        }
    }

    private long computeHash(T key) {
        return (long) key.hashCode();
    }

    private int computeFirstLevelHash(T key){
        long hashedKey = computeHash(key);
        //int[] binaryHashed = longToBinaryArray(hashedKey);
        //int i =multiplyBinaryVectorWithMatrix(binaryHashed);
        int i=multiplyWithMatrix(hashedKey);
        return i;
    }

    public static int[] longToBinaryArray(long number) {
        int[] binary = new int[64]; // long has 64 bits
        for (int i = 63; i >= 0; i--) {
            binary[63 - i] = (int)((number >> i) & 1);
        }
        return binary;
    }

    public int multiplyWithMatrix(long hashedKey){
        int rows =editedMatrix.length;
        int[] result = new int[rows];
        for (int i = 0; i < rows; i++) {
            result[i]=Long.bitCount(hashedKey & editedMatrix[i]) % 2;
        }
        int result2 = 0;
        int length = rows;
        for (int i = 0; i < length; i++) {
            if (result[i] == 1) {
                result2 |= 1 << (length - 1 - i);
            }
        }
        return result2;
    }

    @Override
    public void printTable() {
        throw new UnsupportedOperationException("Unimplemented method 'printTable'");
    }
}
