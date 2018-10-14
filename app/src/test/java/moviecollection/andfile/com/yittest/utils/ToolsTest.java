package moviecollection.andfile.com.yittest.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Andrey Lebedev on 10/14/2018.
 */
public class ToolsTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("\n setUp");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("\n tearDown");
    }

    @Test
    public void isNextPageExist() {
        System.out.println("isNextPageExist");
        assertEquals(true, Tools.isNextPageExist(1,100));
    }
}