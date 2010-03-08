package no.torrissen;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Unit test for the HBaseConnector
 * User: tobiast
 * Date: Mar 6, 2010
 * Time: 2:17:44 PM
 */
public class HBaseConnectorTest{

    private static HBaseConnector myConnector;


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
        myConnector = new HBaseConnector("muppets", "10.0.0.28");
    }


    /**
     * Tests read from the database.
     *
     * @throws Exception if connection fails
     */
    @Test
    public void testRead() throws Exception {

    }

    /**
     * Tests write to the database.
     *
     * @throws java.io.IOException if connection fails
     */
    @Test
    public void testWrite() throws IOException {

    }


    /**
     * Creates Map containing the Animal«s attributes. To be used in testing.
     * @return an initialized map
     */
    private Map<String, Map<String,String>> createAnimal(){

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
    }

}


