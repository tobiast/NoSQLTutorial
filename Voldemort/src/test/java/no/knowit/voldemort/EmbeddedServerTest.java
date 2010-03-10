package no.knowit.voldemort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import voldemort.client.StoreClient;
import voldemort.server.VoldemortServer;
import voldemort.versioning.Versioned;

import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class EmbeddedServerTest {

    private final String STORE_NAME = "test";
    private StoreClient<String, String> client;
    private VoldemortServer node0;

    @Before
    public void connect() {
        setupSingleNodeCluster();
        client = Utils.getStoreClient();
    }

    private void setupSingleNodeCluster() {
        node0 = new Utils().createSingleNodeCluster();
        node0.start();
    }

    @Test
    public void put() {
        String key = "CD0001";
        String value = "Dark side of the Moon";

        client.put(key, value);

        assertEquals(value, client.getValue(key));
    }

    @Test
    public void putMultiple() {
        String key1 = "CD0002";
        String value1 = "Physically Graffiti";
        String key2 = "CD0003";
        String value2 = "In Rainbows";

        client.put(key1, value1);
        client.put(key2, value2);

        assertEquals(value1, client.getValue(key1));
        assertEquals(value2, client.getValue(key2));
    }

    @Test
    public void multiGet() {
        String key1 = "CD0001";
        String value1 = "Dark side of the Moon";
        String key2 = "CD0002";
        String value2 = "Physically Graffiti";

        client.put(key1, value1);
        client.put(key2, value2);

        Map<String, Versioned<String>> actualMap = client.getAll(Arrays.asList(key1, key2));

        assertEquals("Dark side of the Moon", actualMap.get(key1).getValue());
        assertEquals("Physically Graffiti", actualMap.get(key2).getValue());
    }

    @Test
    public void delete() {
        String key = "CD0003";
        String value = "In Rainbows";

        client.put(key, value);

        assertEquals(value, client.getValue(key));

        client.delete(key);

        assertEquals(null, client.getValue(key));
    }

    @After
    public void cleanUp(){
        if(node0.isStarted()) node0.stop();
        Utils.cleanUp();
    }

}