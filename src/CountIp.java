import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class CountIp {


    BitSet setOfNegative;
    BitSet setOfPositive;
    private final IpFileReader reader;


    public CountIp(IpFileReader reader) {
        setOfNegative = new BitSet(Integer.MAX_VALUE);
        setOfPositive = new BitSet(Integer.MAX_VALUE);
        this.reader = reader;
    }

    public static void main(String[] args) {
        System.out.println(new CountIp(new IpFileReader()).getCount());
    }

    public long getCount() {
        try {
            return reader.getStreamOfLines()
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
            int index = Math.abs(value);
            if (!setOfNegative.get(index)) {
                setOfNegative.set(index);
                return true;
            }
        }
        return false;
    }
}
