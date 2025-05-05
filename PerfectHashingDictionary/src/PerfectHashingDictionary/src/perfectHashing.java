package PerfectHashingDictionary.src.PerfectHashingDictionary.src;

public interface perfectHashing<T> {
    public boolean insert(T key);
    public boolean search(T key);
    public boolean delete(T key);
    public int getNumberOfRehashing();
    public void printTable();
}