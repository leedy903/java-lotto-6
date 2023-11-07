package lotto.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int stringToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] Cannot convert this string to integer");
        }
    }

    public static List<Integer> stringListToInt(List<String> strings) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; integers.size() < strings.size(); i++) {
            int integer = stringToInt(strings.get(i));
            integers.add(integer);
        }
        return integers;
    }
}
