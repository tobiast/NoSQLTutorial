package no.torrissen;

/**
 * Created by IntelliJ IDEA.
 * User: tobiast
 * Date: Mar 2, 2010
 * Time: 8:48:40 PM
 * To change this template use File | Settings | File Templates.
 */


import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class HBaseConnector {

    public static Map retrieveMuppet(String muppetId) throws IOException {
        HBaseConfiguration config =  new HBaseConfiguration();
        config.set("hbase.zookeeper.quorum", "192.168.1.61");

        HTable table = new HTable(config, "muppets");
        Map muppet = new HashMap();

        Get g = new Get(Bytes.toBytes(muppetId));
        Result r = table.get(g);
        byte [] value = r.getValue(Bytes.toBytes("names"),Bytes.toBytes("fullname"));

        String valueStr = Bytes.toString(value);
        
        System.out.println("GET: " + valueStr);

       

        return muppet;
    }

    public static void main(String[] args) throws IOException {
        Map muppet = HBaseConnector.retrieveMuppet("Kermit");
        System.out.println(muppet.get("names:fullname"));
        System.out.println(muppet.get("names:nickname"));
    }
}
