package no.knowit.voldemort;

import voldemort.server.VoldemortServer;

import java.util.List;

import static org.junit.Assert.assertEquals;

/* Et eksempel på hvordan jeg så for meg testing av cluster fungere */
public class FailingMultipleEmbeddedServers {
    private List<VoldemortServer> nodes;


    //@Test
    public void multipleServers(){

        Utils t = new Utils();
        nodes = t.createThreeNodeCluster();

        nodes.get(0).start();
        nodes.get(1).start();
        nodes.get(2).start();

        assertEquals(true, nodes.get(0).isStarted());
        assertEquals(true, nodes.get(1).isStarted());
        assertEquals(true, nodes.get(2).isStarted());

        nodes.get(0).stop();

        assertEquals(false, nodes.get(0).isStarted());

        nodes.get(0).start();

        assertEquals(true, nodes.get(0).isStarted());

    }

}
