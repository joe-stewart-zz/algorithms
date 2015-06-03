public class RunLength {
    private static final int R = 256;
    private static final int lgR = 8;
    public static void compress() {
        char run = 0;
        boolean old = false;
        while(!BinaryStdIn.isEmpty()) {
            boolean b = BinaryStdIn.readBoolean();
            if(b != old) {
                BinaryStdOut.write(run, lgR);
                run = 1;
                old = !old;
            } else {
                if(run == R-1) {
                    BinaryStdOut.write(run, lgR);
                    run = 0;
                    BinaryStdOut.write(run, lgR);
                }
                run++;
            }
        }
        BinaryStdOut.write(run, lgR);
        BinaryStdOut.close();
    }
    public static void expand() {
        boolean b = false;
        while(!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(lgR);
            for(int i = 0; i < run; i++)
                BinaryStdOut.write(b);
            b = !b;
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
