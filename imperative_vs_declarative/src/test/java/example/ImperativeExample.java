package example;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ImperativeExample {

    static Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
    static String[] inputs = new String[]{"a","b","*"};

    public void doSomething(String[] items) {
        Set<Integer> isoCodes = new HashSet<>();
        for (String item : items) {
            if (!item.trim().isEmpty() && hasNoSpecialChars(item)) {
                isoCodes.add(findISOCodeByString(item));
            }
        }
        System.out.println(isoCodes);
    }

    public static int findISOCodeByString(String str) {
        System.out.println("find ISOCode by : " + str);
        return 0;
    }

    public static boolean hasNoSpecialChars(String str) {
        return pattern.matcher(str).find();
    }

    @Test
    public void doSomething() {
        doSomething(inputs);
    }

}
