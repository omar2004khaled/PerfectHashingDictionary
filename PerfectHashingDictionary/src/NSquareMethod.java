import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NSquareMethod<T> implements perfectHashing<T>  {

    @Override
    public boolean insert(T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean search(T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public boolean delete(T key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public int getNumberOfRehashing() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNumberOfRehashing'");
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
