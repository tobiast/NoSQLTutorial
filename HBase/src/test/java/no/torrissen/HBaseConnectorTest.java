package no.torrissen;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for the HBaseConnector
 * User: tobiast
 * Date: Mar 6, 2010
 * Time: 02:17:44 AM
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
        myConnector = new HBaseConnector("muppets", "10.0.0.28");
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

       myConnector.createMuppet("The Animal", createAnimal());
       Map<String, String> muppet2 = myConnector.retrieveMuppet("The Animal");

    }


    /**
     * Creates Map containing the Animal«s attributes. To be used in testing.
     * @return an initialized map
     */
    final Map<String, Map<String,String>> createAnimal(){

     final Map<String, Map<String, String>> newMuppet = new HashMap<String, Map<String, String>>();

        final Map <String, String> newMuppetNames = new HashMap<String, String>();
        newMuppetNames.put("fullname", "George the animal Steele");
        newMuppetNames.put("shortname", "animal");

        final Map <String, String> newMuppetActors = new HashMap<String, String>();
        newMuppetActors.put("puppeteer and voice", "Frank Oz");

        final Map <String, String> newMuppetJobs = new HashMap<String, String>();
        newMuppetJobs.put("main", "Drummer");
        newMuppetJobs.put("part time", "wildlife");

        newMuppet.put("names", newMuppetNames);
        newMuppet.put("actors", newMuppetActors);
        newMuppet.put("jobs", newMuppetJobs);

        return  newMuppet;
    }

}


