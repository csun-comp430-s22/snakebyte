package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserTest {
    @Test
    public void testEqualsOpExp() {
        // 1 + 1 == 1 + 1
        final OpExp first = new OPExp(new IntExp(1),
                                      new PlusOP(),
                                      new IntExp(1));
        final OpExp second = new OPExp(new IntExp(1),
                                      new PlusOP(),
                                      new IntExp(1));
        assertEquals(first, second);
    }
}