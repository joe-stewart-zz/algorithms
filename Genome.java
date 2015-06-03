public class Genome {
    public static void compress() {
        Alphabet DNA = new Alphabet("ACTG");
        String s = BinaryStdIn.readString();
        int N = s.length();
        BinaryStdOut.write(N);
        for(int i = 0; i < N; i++) {
            int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, 2);
        }
        BinaryStdOut.close();
    }
    public static void expand() {
        Alphabet DNA = new Alphabet("ACTG");
        int N = BinaryStdIn.readInt();
        for(int i = 0; i < N; i++) {
            char c = BinaryStdIn.readChar(2);
            BinaryStdOut.write(DNA.toChar(c), 8);
        }
        BinaryStdOut.close();
    }
    public static void main(String[] args) {
        if(args[0].equals("-"))
            compress();
        else if(args[0].equals("+"))
            expand();
        else
            throw new IllegalArgumentException("Illegal command line argument");
    }
}
