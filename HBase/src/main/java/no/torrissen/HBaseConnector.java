package no.torrissen;


/**
 * Class that puts stuff into an HBase instance.
 * User: tobiast
 * Date: Mar 2, 2010
 * Time: 23:48:40 PM
 */

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class HBaseConnector {

    private static final Logger LOGGER = Logger.getLogger(HBaseConnector.class);


    /**
     * Constructor that also handles the connection.
     *
     * @param tableName the table (aka Map) to conenct to
     * @param ipAddress the IP address of the Server
     * @throws IOException if connection fails
     */
    public HBaseConnector(final String tableName, String ipAddress) throws IOException {

    }


    /**
     * Stores a muppet in the DB.
     * <p/>
     * Iterates over the Map passed as parameter and inserts into the
     *
     * @param muppetId   the muppet identifier
     * @param attributes a map containing all the muppet's attributes. Keys must match the column families defined in the table
     * @throws IOException if stuff blow up.
     */
    public void createMuppet(final String muppetId, final Map<String, Map<String, String>> attributes) throws IOException {


    }


    /**
     * Returns all values for a muppet.
     *
     * @param muppetId The muppet identifier
     * @return a map containing all the keys and all the values
     * @throws IOException if read from HBase fails
     */
    public Map<String, String> retrieveMuppet(final String muppetId) throws IOException {


        return new HashMap<String, String>();
    }

}
