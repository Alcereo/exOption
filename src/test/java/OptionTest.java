import org.junit.Assert;
import org.junit.Test;
import ru.alcereo.exoption.Option;

import java.util.Random;
import java.util.function.Function;

import static org.junit.Assert.*;

public class OptionTest {

    private class ConcreteException extends Exception{}

    private Integer randValue = new Random().nextInt();

    @Test
    public void map(){
        Assert.assertEquals(
                Option.asOption(randValue)
                        .map(i -> i*2)
                        .map(i -> "a"+i)
                        .getOrElse(null),
                "a"+(randValue*2)
        );
    }

    @Test
    public void flatMap(){
        assertEquals(
                Option.asOption(randValue)
                        .flatMap(i -> Option.asOption(i*2))
                        .flatMap(i -> Option.asOption("a"+i)).getOrElse(null),
                "a"+(randValue*2)
        );
    }

    @Test
    public void filter(){
        assertTrue(
                Option.asOption(randValue)
                .flatMap(i -> Option.asOption(i*2))
                .filter(value -> value!=randValue*2)
                .flatMap(i -> Option.asOption("a"+i))
                .isNone()
        );
    }

    @Test
    public void getOreElse(){
        assertEquals(
                (int)Option
                     .asOption(randValue)
                     .flatMap(i -> Option.asOption(i*2))
                     .getOrElse(randValue),
                randValue*2
        );

        assertEquals(
                Option.asOption(null)
                     .flatMap(value -> Option.asOption(randValue*2))
                     .getOrElse(randValue),
                randValue
        );
    }

    @Test(expected = ConcreteException.class)
    public void throwException() throws ConcreteException {
        Option.asException(new ConcreteException()).throwException();
    }

    @Test
    public void getOrElseWithExc(){
        assertEquals(
                Option
                .asOption(randValue)
                .getOrElseWithExc(
                        e -> null,
                        null
                ),
                randValue
        );

        assertEquals(
                Option
                .asException(new Exception())
                .getOrElseWithExc(
                        e -> randValue,
                        null
                        ),
                randValue
        );

        assertEquals(
                Option
                .asOption(() -> null)
                .getOrElseWithExc(
                        e -> null,
                        randValue
                ),
                randValue
        );

    }

}
