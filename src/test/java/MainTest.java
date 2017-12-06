package de.hamburg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static de.hamburg.Main.checkPassword;
import static de.hamburg.Main.generatePassword;
import static org.junit.Assert.*;

/**
 * Created by michelbrueger on 04.12.17.
 */
public class MainTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCheckPassword() throws Exception {
        assertTrue(checkPassword("!Ululuu1"));
        assertFalse(checkPassword("!!!!!!U1"));
        assertFalse(checkPassword("!!!!!!u1"));
        assertFalse(checkPassword("!!!!!!uU"));
        assertFalse(checkPassword("UUUUUUu1"));
        assertFalse(checkPassword("!Ululu1"));
        assertTrue(checkPassword("!Uu11111BananeBanane123"));
    }

    @Test
    public void testGeneratePassword() throws Exception {
        String password01 = generatePassword(8);
        String password02 = generatePassword(8);
        String password03 = generatePassword(22);
        String password04 = generatePassword(8);
        String password05 = generatePassword(7);
        System.out.println(password01);
        System.out.println(password02);
        System.out.println(password03);
        System.out.println(password04);
        System.out.println(password05);
        assertEquals(8, password01.length());
        assertEquals(8, password02.length());
        assertEquals(22, password03.length());
        assertEquals(8, password04.length());
 //       assertEquals(7, password05.length());
        assertTrue(checkPassword(password01));
        assertTrue(checkPassword(password02));
        assertTrue(checkPassword(password03));
        assertTrue(checkPassword(password04));
//        assertFalse(checkPassword(password05));
    }
}