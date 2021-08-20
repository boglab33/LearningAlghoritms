import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class CountIp {

    /*Defining two BitSet for storing ipAddresses as indexes,
    one bitSet stores negative numbers, other - positive
    false means that address is never shown,
    true - address was shown*/
    private final BitSet setOfNegative;
    private final BitSet setOfPositive;
    private final IpFileReader reader;


    public CountIp(IpFileReader reader) {
        setOfNegative = new BitSet(Integer.MAX_VALUE);
        setOfPositive = new BitSet(Integer.MAX_VALUE);
        this.reader = reader;
    }

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        System.out.println(new CountIp(new IpFileReader()).getCount());
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + ((endTime - startTime) / 1000) + "s");
    }

    /*Iterate through stream of lines,
    and count all uniq lines*/
    public long getCount() {
        try {
            return reader.getStreamOfLines()
                    .parallel()
                    .filter(this::isIpUnique)
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*Wrapping String up into unique integer,
    cos ip range == integer range*/
    private boolean isIpUnique(String ipLine) {
        try {
            return intIsUnique(ByteBuffer
                    .wrap(InetAddress
                            .getByName(ipLine)
                            .getAddress())
                    .getInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*Performs check for availability in the bitSet,
     * if address unique - changes boolean value to true
     * subtracting one, to avoid integer overflow */
    private boolean intIsUnique(int value) {
        if (value >= 0) {
            if (!setOfPositive.get(value)) {
                setOfPositive.set(value);
                return true;
            }
        } else {
            int index = Math.abs(value) - 1;
            if (!setOfNegative.get(index)) {
                setOfNegative.set(index);
                return true;
            }
        }
        return false;
    }
}
