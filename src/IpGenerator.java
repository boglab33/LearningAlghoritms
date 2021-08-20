import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class IpGenerator {
    private static final String FILE_ADDRESS = "ipList.txt";
    private static final int NUMBER_OF_ADDRESSES = 10_000_000_0;
    public static void main(String[] args) {
        writeToTheFile();
    }

    private static void writeToTheFile() {
        try (FileWriter writer = new FileWriter(FILE_ADDRESS, false)) {
            for (int i = 0; i < NUMBER_OF_ADDRESSES; i++) {
               writer.write(getIpAddress());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getIpAddress() {
        return String.format("%s.%s.%s.%s\n", getNum(), getNum(), getNum(), getNum());
    }

    private static Integer getNum() {
        return ThreadLocalRandom.current().nextInt(256);
    }
}
