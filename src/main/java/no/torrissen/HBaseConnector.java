package no.torrissen;

/**
 * Created by IntelliJ IDEA.
 * User: tobiast
 * Date: Mar 2, 2010
 * Time: 8:48:40 PM
 * To change this template use File | Settings | File Templates.
 */


import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.io.RowResult;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class HBaseConnector {

    public static Map retrievePost(String postId) throws IOException {
        HTable table = new HTable(new HBaseConfiguration(), "muppets");
        Map post = new HashMap();

        RowResult result = table.getRow(postId);

        for (byte[] column : result.keySet()) {
            post.put(new String(column), new String(result.get(column).getValue()));
        }
        return post;
    }

    public static void main(String[] args) throws IOException {
        Map blogpost = HBaseConnector.retrievePost("Kermit");
        System.out.println(blogpost.get("name:fistname"));
        System.out.println(blogpost.get("name:nickname"));
    }
}
