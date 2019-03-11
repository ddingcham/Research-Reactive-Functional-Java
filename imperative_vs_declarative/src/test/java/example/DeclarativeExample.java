package example;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static example.ImperativeExample.inputs;

public class DeclarativeExample {

    public void doSomething(String[] items) {
        Set<Integer> isoCodes = Stream
                .of(items)
                .filter(DeclarativeExample::notEmpty)
                .filter(ImperativeExample::hasNoSpecialChars)
                .map(ImperativeExample::findISOCodeByString)
                .collect(Collectors.toSet());
        System.out.println(isoCodes);
    }

    public static boolean notEmpty(String str) {
        return !str.trim().isEmpty();
    }

    @Test
    public void doSomething() {
        doSomething(inputs);
    }

}
