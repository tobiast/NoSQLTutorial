package no.torrissen;

/**
 * Class that puts stuff into an HBase instance.
 * User: tobiast
 * Date: Mar 2, 2010
 * Time: 23:48:40 PM
 */


import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.IOException;
import java.util.Set;

public class HBaseConnector {


    private static HTable table;

    private static final Logger LOGGER = Logger.getLogger(HBaseConnector.class);
    

    public HBaseConnector(final String tableName, String ipAddress) throws IOException {
       HBaseConfiguration config =  new HBaseConfiguration();
       config.set("hbase.zookeeper.quorum", ipAddress);
       table = new HTable(config, tableName);
    }


    /**
     * Returns all values for a muppet.
     * @param muppetId The muppet identifier
     * @return a map containing all the keys and all the values
     * @throws IOException if read from HBase fails
     */
    public  Map<String, String> retrieveMuppet(final String muppetId) throws IOException {

        Map<String, String> muppet = new HashMap<String, String>();
        Get g = new Get(Bytes.toBytes(muppetId));
        Result r = table.get(g);

        KeyValue[] keyValues = r.raw();
        for (KeyValue keyValue : keyValues) {
            LOGGER.info("key: " + keyValue.toString() + " Value: " + Bytes.toString(keyValue.getValue()));
            muppet.put(keyValue.toString(), Bytes.toString(keyValue.getValue()));
        }
        return muppet;
    }


    /**
     * Stores a muppet in the DB.
     *
     * Iterates over the Map passed as parameter and inserts into the 
     *
     * @param muppetId the muppet identifier
     * @param attributes a map containing all the muppet«s attributes. Keys must match the column families defined in the table
     * @throws IOException if stuff blow up.
     */
    public  void createMuppet(final String muppetId, final Map<String, Map<String, String>> attributes) throws IOException {

        Put p = new Put(Bytes.toBytes(muppetId));

        for (Map.Entry <String, Map<String, String>> columnFamily : attributes.entrySet()) {

            String columnFamilyName = columnFamily.getKey();
            Map<String, String> columns = columnFamily.getValue();

            for (Map.Entry<String, String> column : columns.entrySet()){
                 p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(column.getKey()), Bytes.toBytes(column.getValue()));
            }
        }

        table.put(p);
    }

}
