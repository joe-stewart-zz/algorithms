public class HexDump {
    public static void main(String[] args) {
        int BYTES_PER_LINE = 16;
        if(args.length == 1)
            BYTES_PER_LINE = Integer.parseInt(args[0]);
        int i;
        for(i = 0; !BinaryStdIn.isEmpty(); i++) {
            if(BYTES_PER_LINE == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if(i == 0)
                System.out.printf("");
            else if(i % BYTES_PER_LINE == 0)
                System.out.printf("\n", i);
            else
                System.out.print(" ");
            char c = BinaryStdIn.readChar();
            System.out.printf("%02x", c & 0xff);
        }
        if(BYTES_PER_LINE != 0)
            System.out.println();
        System.out.println((i*8) + " bits");
    }
}
