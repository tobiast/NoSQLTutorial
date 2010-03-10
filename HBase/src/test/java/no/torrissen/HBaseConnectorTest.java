package no.torrissen;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.io.IOException;

/**
 * Unit test for the HBaseConnector
 * User: tobiast
 * Date: Mar 6, 2010
 * Time: 2:17:44 PM
 */
public class HBaseConnectorTest{



    /**
     * testcase init method
     * User: tobiast
     * Date: Mar 6, 2010
     * Time: 2:17:44 AM
     *
     * @throws java.io.IOException if connection fails.
     */
    @BeforeClass
    public static  void init() throws IOException {
    }


    /**
     * Tests read from the database.
     *
     * @throws Exception if connection fails
     */
    @Test
    public void testRead() throws Exception {
        fail("Implement me");
    }

    /**
     * Tests write to the database.
     *
     * @throws java.io.IOException if connection fails
     */
    @Test
    public void testWrite() throws IOException {
         fail("Implement me!");
    }

}


