package no.knowit.voldemort;

import org.junit.After;
import org.junit.Test;
import voldemort.client.rebalance.RebalanceClientConfig;
import voldemort.client.rebalance.RebalanceController;
import voldemort.cluster.Cluster;
import voldemort.server.VoldemortServer;
import voldemort.versioning.Versioned;
import voldemort.xml.ClusterMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RebalanceEmbeddedServersTest {
    private List<VoldemortServer> nodes;

    @Test
    public void multipleServers() throws IOException {
        Utils t = new Utils();
        nodes = t.createThreeNodeCluster();

        nodes.get(0).start();
        nodes.get(1).start();
        nodes.get(2).start();

        assertEquals(true, nodes.get(0).isStarted());
        assertEquals(true, nodes.get(1).isStarted());
        assertEquals(true, nodes.get(2).isStarted());

        VoldemortServer node3 = t.createNewNode();

        node3.start();

        assertEquals(true, node3.isStarted());

        String bootstrapUrl = "tcp://localhost:6666";
        String targetClusterXML = "src/test/resources/four_node_cluster_config/4nodecluster.xml";
        Cluster targetCluster = new ClusterMapper().readCluster(new File(targetClusterXML));

        RebalanceClientConfig config = new RebalanceClientConfig();
        config.setMaxParallelRebalancing(1);
        config.setDeleteAfterRebalancingEnabled(true);
        RebalanceController rebalanceController = new RebalanceController(bootstrapUrl, config);
        rebalanceController.rebalance(targetCluster);

        Versioned<Cluster> cluster = rebalanceController.getAdminClient().getRemoteCluster(0);

        assertEquals(4, cluster.getValue().getNumberOfNodes());
        rebalanceController.stop();
    }

    @After
    public void cleanup(){
          for(VoldemortServer n : nodes)
            if(n.isStarted()) n.stop();

        Utils.cleanUp();
    }
}