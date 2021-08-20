import java.util.List;
import java.util.stream.Stream;

public class IpFileReader {

    public Stream<String> getStreamOfLines() {
        return Stream.of("199.94.128.255",
                "156.216.64.59",
                "160.125.26.132", "160.125.26.132", "160.125.26.132","160.125.26.132");
    }
}
