package no.torrissen;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.Map;

/**
 * Unit test for the HBaseConnector
 * User: tobiast
 * Date: Mar 6, 2010
 * Time: 2:17:44 PM
 */
public class HBaseConnectorTest extends TestCase {

    private HBaseConnector myConnector;


    /**
     * Created by IntelliJ IDEA.
     * User: tobiast
     * Date: Mar 6, 2010
     * Time: 2:17:44 PM
     *
     * @param testName name of the test case
     * @throws java.io.IOException if connection fails.
     */
    public HBaseConnectorTest(String testName) throws IOException {
        super(testName);
        myConnector = new HBaseConnector("muppets", "10.0.0.26");
    }


    /**
     * Tests read from the database.
     *
     * @throws Exception if connection fails
     */
    public void testRead() throws Exception {

        Map<String, String> muppet = myConnector.retrieveMuppet("Kermit");
        System.out.println(muppet.toString());
    }

    /**
     * Tests write to the database.
     *
     * @throws java.io.IOException if connection fails
     */
    public void testWrite() throws IOException {

       myConnector.createMuppet("Wukka Foobar", null);
       Map<String, String> muppet2 = myConnector.retrieveMuppet("Wukka Foobar");

    }

}


