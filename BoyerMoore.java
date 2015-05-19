public class BoyerMoore {
    private final int R;
    private int[] right;
    private char[] pattern;
    private String pat;
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;
        right = new int[R];
        for(int c = 0; c < R; c++)
            right[c] = -1;
        for(int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }
    public BoyerMoore(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for(int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];
        right = new int[R];
        for(int c = 0; c < R; c++)
            right[c] = -1;
        for(int j = 0; j < pattern.length; j++)
            right[pattern[j]] = j;
    }
    public int search(String txt) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for(int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for(int j = M-1; j >= 0; j--) {
                if(pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if(skip == 0)
                return i;
        }
        return N;
    }
    public int search(char[] text) {
        int M = pattern.length;
        int N = text.length;
        int skip;
        for(int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for(int j = M-1; j >= 0; j--) {
                if(pattern[j] != text[i+j]) {
                    skip = Math.max(1, j - right[text[i+j]]);
                    break;
                }
            }
            if(skip == 0)
                return i;
        }
        return N;
    }
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        char[] pattern = pat.toCharArray();
        char[] text = txt.toCharArray();
        BoyerMoore bm1 = new BoyerMoore(pat);
        BoyerMoore bm2 = new BoyerMoore(pattern, 256);
        int offset1 = bm1.search(txt);
        int offset2 = bm2.search(text);
        System.out.println("text:    " + txt);
        System.out.print("pattern: ");
        for(int i = 0; i < offset1; i++)
            System.out.print(" ");
        System.out.println(pat);
        System.out.print("pattern: ");
        for(int i = 0; i < offset2; i++)
            System.out.print(" ");
        System.out.println(pat);
    }
}
