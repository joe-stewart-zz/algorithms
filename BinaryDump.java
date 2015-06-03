public class BinaryDump {
    public static void main(String[] args) {
        int BITS_PER_LINE = 16;
        if(args.length == 1)
            BITS_PER_LINE = Integer.parseInt(args[0]);
        int count;
        for(count = 0; !BinaryStdIn.isEmpty(); count++) {
            if(BITS_PER_LINE == 0) {
                BinaryStdIn.readBoolean();
                continue;
            } else if(count != 0 && count % BITS_PER_LINE == 0) {
                System.out.println();
            }
            if(BinaryStdIn.readBoolean())
                System.out.print(1);
            else
                System.out.print(0);
        }
        if(BITS_PER_LINE != 0)
            System.out.println();
        System.out.println(count + " bits");
    }
}
