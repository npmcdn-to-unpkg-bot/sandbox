package util;

import static java.util.function.Predicate.*;
import static org.assertj.core.api.Assertions.*;
import static util.Functions.*;

import java.util.stream.Stream;

import org.junit.Test;

public class FunctionsTest {

    @Test
    public void test_by() throws Exception {

        Object[] os1 = Stream.of("foo", "hello", "hoge").filter(s -> s.length() == 5).toArray();
        assertThat(os1).contains("hello");

        Object[] os2 = Stream.of("foo", "hello", "hoge").filter(by(String::length, isEqual(5)))
                .toArray();
        assertThat(os2).contains("hello");

    }

    @Test
    public void test_filterMap() throws Exception {

        Object[] os1 = Stream.of("foo", "bar", "baz").filter(s -> s.startsWith("ba"))
                .map(s -> s + s).toArray();
        assertThat(os1).contains("barbar", "bazbaz");

        Object[] os2 = Stream.of("foo", "bar", "baz")
                .flatMap(filterMap(s -> s.startsWith("ba"), s -> s + s)).toArray();
        assertThat(os2).contains("barbar", "bazbaz");
    }

    @Test
    public void test_applyPartial() throws Exception {

        Object[] os1 = Stream.of(1, 2, 3).map(a -> Integer.sum(a, 4)).toArray();
        assertThat(os1).contains(5, 6, 7);

        Object[] os2 = Stream.of(1, 2, 3).map(applyPartial(Integer::sum, 4)).toArray();
        assertThat(os2).contains(5, 6, 7);
    }
}
