package no.knowit.voldemort;

import org.junit.Before;
import org.junit.Test;
import voldemort.client.MockStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.serialization.StringSerializer;
import voldemort.versioning.Versioned;

import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class MockServerTest {

    private final String STORE_NAME = "test";
    private StoreClient<String, String> client;

    @Before
    public void connect() {
        StoreClientFactory factory = new MockStoreClientFactory(new StringSerializer(), new StringSerializer());
        client = factory.getStoreClient(STORE_NAME);
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

}
