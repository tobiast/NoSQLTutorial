package no.knowit.voldemort;

import org.junit.After;
import org.junit.Test;
import voldemort.client.StoreClient;
import voldemort.server.VoldemortServer;
import voldemort.store.InsufficientOperationalNodesException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultipleEmbeddedServersTest {
    private List<VoldemortServer> nodes;

    @Test(expected = InsufficientOperationalNodesException.class)
    public void multipleServers(){
        Utils t = new Utils();
        nodes = t.createThreeNodeCluster();

        nodes.get(0).start();
        nodes.get(1).start();
        nodes.get(2).start();

        StoreClient<String, String> client = Utils.getStoreClient();

        String key = "CD0004";
        String value = "In Rainbows";

        client.put(key, value);
        assertEquals(value, client.getValue(key));

        nodes.get(0).stop();

        String value2 = "Dark Side of The Moon";
        client.put(key, value2);
        assertEquals(value2, client.getValue(key));

        nodes.get(1).stop();

        String value3 = "Physically Graffiti";
        client.put(key, value3);
    }

    @After
    public void stopServers(){
        for(VoldemortServer n : nodes)
            if(n.isStarted()) n.stop();
        
        Utils.cleanUp();
    }
}