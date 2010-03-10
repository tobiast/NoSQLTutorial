package no.knowit.voldemort;

import org.apache.commons.io.FileUtils;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.server.VoldemortConfig;
import voldemort.server.VoldemortServer;
import voldemort.utils.Props;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String TEST_DIR = "target/";
    private static final String SRC_DIR = "src/test/resources/";
    private static final String STORE_NAME = "test";

    public static StoreClient getStoreClient() {
        String bootstrapUrl = "tcp://localhost:6666";
        ClientConfig clientConfig = new ClientConfig().setBootstrapUrls(bootstrapUrl);
        StoreClientFactory factory = new SocketStoreClientFactory(clientConfig);
         return factory.getStoreClient(STORE_NAME);
    }

    public VoldemortServer createSingleNodeCluster() {
        String path = "single_node_cluster";
        setup(path);

        Props props0 = getProps(0, path);

        VoldemortConfig config0 = new VoldemortConfig(props0);

        return new VoldemortServer(config0);
    }

    public VoldemortServer createNewNode() {
        String path = "4th_node";
        setup(path);

        Props props0 = getProps(3, path);

        VoldemortConfig config0 = new VoldemortConfig(props0);

        return new VoldemortServer(config0);
    }

    public List<VoldemortServer> createThreeNodeCluster() {
        String path = "three_node_cluster";

        setup(path);

        Props props0 = getProps(0, path);
        Props props1 = getProps(1, path);
        Props props2 = getProps(2, path);

        VoldemortConfig config0 = new VoldemortConfig(props0);
        VoldemortConfig config1 = new VoldemortConfig(props1);
        VoldemortConfig config2 = new VoldemortConfig(props2);

        List<VoldemortServer> servers = new ArrayList<VoldemortServer>();
        servers.add(new VoldemortServer(config0));
        servers.add(new VoldemortServer(config1));
        servers.add(new VoldemortServer(config2));
        return servers;
    }


    private void setup(String path) {
        File source = new File(SRC_DIR + path);
        try {
            FileUtils.copyDirectory(source, new File(TEST_DIR + path));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Props getProps(int nodeId, String path) {
        Props props = new Props();
        props.put("node.id", nodeId);
        props.put("enable.memory.engine", "true");
        props.put("enable.bdb.engine", "false");
        props.put("voldemort.home", TEST_DIR + path + "/node" + nodeId);
        props.put("bdb.cache.size", 1 * 1024 * 1024);
        return props;
    }

    public static void cleanUp() {
        try {
            FileUtils.deleteDirectory(new File(TEST_DIR));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
