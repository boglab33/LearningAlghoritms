import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CountIp {

    private static final List<Character> LIST_OF_AVERAGE_NUMS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private static final List<Character> LIST_OF_START_NUMS = List.of('1', '2');


    private final List<List<Map<Character, Boolean>>> listOfGroups;
    private final IpFileReader reader;


    public CountIp(IpFileReader reader) {
        this.reader = reader;
        listOfGroups = getListOfGroups();
    }

    public static void main(String[] args) {
        System.out.println(new CountIp(new IpFileReader()).getCount());
    }

    public long getCount() {
        return reader.getStreamOfLines()
                .filter(this::isIpUniq)
                .count();
    }

    private boolean isIpUniq(String test) {
        String[] inputGroups = test.split("\\.");
        return IntStream.range(0, inputGroups.length)
                .anyMatch(a -> isGroupUniq(inputGroups, a));
    }

    private boolean isGroupUniq(String[] address, int group) {
        for (int index = address[group].length() - 1; index >= 0; index--) {
            if (listOfGroups.get(group).get(index).get(address[group].charAt(index))) {
                saveNewIp(address, group, index);
                return true;
            }
        }
        return false;
    }

    private void saveNewIp(String[] address, int group, int index) {
        saveGroup(address[group], group, index);
        saveUniqPart(address, group);
    }

    private void saveGroup(String address, int group, int index) {
        for (int curIndex = index; curIndex < address.length(); curIndex++) {
            listOfGroups.get(group).get(curIndex).put(address.charAt(curIndex), Boolean.FALSE);
        }
    }

    private void saveUniqPart(String[] address, int group) {
        for (int curGroup = group; curGroup < address.length; curGroup++) {
            for (int curIndex = 0; curIndex < address[curGroup].length(); curIndex++) {
                listOfGroups.get(curGroup).get(curIndex).put(address[curGroup].charAt(curIndex), Boolean.FALSE);
            }
        }
    }

    private List<List<Map<Character, Boolean>>> getListOfGroups() {
        return List.of(getGroup(), getGroup(), getGroup(), getGroup());
    }

    private List<Map<Character, Boolean>> getGroup() {
        return List.of(
                getHashMap(LIST_OF_START_NUMS),
                getHashMap(LIST_OF_AVERAGE_NUMS),
                getHashMap(LIST_OF_AVERAGE_NUMS));
    }

    private Map<Character, Boolean> getHashMap(List<Character> listOfStartNums) {
        return listOfStartNums
                .stream()
                .collect(Collectors.toMap(Function.identity(), a -> Boolean.TRUE));
    }
}
