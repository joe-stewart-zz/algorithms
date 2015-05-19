import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
    private String pat;
    private long patHash;
    private int M;
    private long Q;
    private int R;
    private long RM;
    public RabinKarp(int R, char[] pattern) {
        throw new UnsupportedOperationException("Operation not supported yet");
    }
    public RabinKarp(String pat) {
        this.pat = pat;
        R = 256;
        M = pat.length();
        Q = longRandomPrime();
        RM = 1;
        for(int i = 1; i <= M-1; i++)
            RM = (R * RM) % Q;
        patHash = hash(pat, M);
    }
    private long hash(String key, int M) {
        long h = 0;
        for(int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }
    private boolean check(String txt, int i) {
        for(int j = 0; j < M; j++)
            if(pat.charAt(j) != txt.charAt(i + j))
                return false;
        return true;
    }
    private boolean check(int i) {
        return true;
    }
    public int search(String txt) {
        int N = txt.length();
        if(N < M)
            return N;
        long txtHash = hash(txt, M);
        if((patHash == txtHash) && check(txt, 0))
            return 0;
        for(int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM*txt.charAt(i-M) % Q) % Q;
            txtHash = (txtHash*R + txt.charAt(i)) % Q;
            int offset = i - M + 1;
            if((patHash == txtHash) && check(txt, offset))
                return offset;
        }
        return N;
    }
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);
        System.out.println("text:    " + txt);
        System.out.print("pattern: ");
        for(int i = 0; i < offset; i++)
            System.out.print(" ");
        System.out.println(pat);
    }
}
