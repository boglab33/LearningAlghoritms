import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class CountIp {


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

    public long getCount() {
        try {
            return reader.getStreamOfLines()
                    .parallel()
                    .filter(this::isIpUniq)
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isIpUniq(String ipLine) {
        try {
            return intIsUniq(ByteBuffer
                    .wrap(InetAddress
                            .getByName(ipLine)
                            .getAddress())
                    .getInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean intIsUniq(int value) {
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
