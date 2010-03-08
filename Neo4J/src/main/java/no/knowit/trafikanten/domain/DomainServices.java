package no.knowit.trafikanten.domain;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Denne klassen inneholder alle tjenestene som er koblet til skape innehold i
 * databasen.
 */
public class DomainServices {

	private static Logger logger = Logger.getLogger(DomainServices.class);
	private final EmbeddedGraphDatabase database;

	public DomainServices(EmbeddedGraphDatabase database) {
		this.database = database;
	}

	public Station createStation(String name) {
		Node node = database.createNode();
		getReferencenode().createRelationshipTo(node, Station.TABLE);
		Station res = new Station(node);
		res.setName(name);
		logger.info("Created new station: " + name);
		return res;
	}

	private Node getReferencenode() {
		return database.getNodeById(0);
	}

	public void createBidirectionalConnection(Station from, Station to, int duration, Connectiontype type) {
		from.addConnection(to, duration, type);
		to.addConnection(from, duration, type);
		logger.info("Opprettet forbindese mellom " + from + " og " + to);
	}

}
