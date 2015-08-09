package parser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CalcTest {

    @Test
    public void test() throws Exception {
        int actual = Calc.calc("1 + 2 - (3 * 4 + 5) - 6 / (7 + 8 - 9)");
        int expected = 1 + 2 - (3 * 4 + 5) - 6 / (7 + 8 - 9);
        assertThat(actual, is(expected));
    }
}
