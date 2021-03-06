import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.stream.Stream;

public class IpFileReader {

    public static final String PATH = "ipList.txt";

    public Stream<String> getStreamOfLines() throws IOException {
        return Files.lines(Paths.get(PATH));
    }
}
