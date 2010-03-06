package no.torrissen;

/**
 * Created by IntelliJ IDEA.
 * User: tobiast
 * Date: Mar 2, 2010
 * Time: 8:48:40 PM
 * To change this template use File | Settings | File Templates.
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
import java.util.Map;
import java.io.IOException;

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



    public  void createMuppet(final String muppetId, final Map<String, String> attributes) throws IOException {



        Put p = new Put(Bytes.toBytes(muppetId));
        p.add(Bytes.toBytes("names"), Bytes.toBytes("Andebynavn"),
             Bytes.toBytes("Donald Duck"));
             table.put(p);

    }

}
