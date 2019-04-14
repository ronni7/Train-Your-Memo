package test;

import hello.utilities.KeyGenerator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeyGeneratorTest  {
    private KeyGenerator keyGenerator;
    private int length;


    public KeyGeneratorTest() {
        this.keyGenerator = new KeyGenerator();
        this.length=6;
    }

    @Test
    public void testGeneratedKeyLength() {
String generated=keyGenerator.generate(length);
        assertEquals(length,generated.length() );
    }
    @Test
    public void testGeneratedKeyContainsOnlyUpperCaseLettersAndNumbers() {
        String generated=keyGenerator.generate(length);
        char[] generatedArray=generated.toCharArray();
        for (int i = 0; i < generatedArray.length; i++) {
            char c=generated.charAt(i);
          //  System.out.println("charat = " + c);
         assertTrue(Character.isDigit( c)|| Character.isUpperCase(c));
           // System.out.println("c = " + c);
        }
      //  System.out.println("generated = " + generated);

    }
}
