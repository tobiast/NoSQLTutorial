package no.knowit.trafikanten.domain;

import java.util.ArrayList;
import java.util.List;

import no.knowit.trafikanten.route.Route;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Denne klassen inneholder alle tjenestene som er koblet til søke innehold fra
 * databasen.
 */
public class SearchServices {

	/**
	 * Denne konstanten definerer en array som inneholder alle reisetypene, samt
	 * utgående retning. Denne kan brukes ved traversering av grafen dersom alle
	 * reisemiddel er tillatt.
	 */
	private static final Object[] OUTGOING_TRAVELTYPES = new Object[Connectiontype.values().length * 2];
	static {
		Connectiontype[] traveltypes = Connectiontype.values();
		int i = 0;
		for (Connectiontype traveltype : traveltypes) {
			OUTGOING_TRAVELTYPES[i++] = traveltype;
			OUTGOING_TRAVELTYPES[i++] = Direction.OUTGOING;
		}
	}

	private static Logger logger = Logger.getLogger(SearchServices.class);

	private final EmbeddedGraphDatabase database;

	public SearchServices(EmbeddedGraphDatabase database) {
		this.database = database;
	}

	public List<Station> searchStation(String regexp) {
		throw new RuntimeException("Not implemented");
	}

	public boolean connectionExists(Station from, Station to) {
		throw new RuntimeException("Not implemented");
	}

	public Route findRoute(Station from, Station to) {
		throw new RuntimeException("Not implemented");
	}


	private Node getReferencenode() {
		return this.database.getNodeById(0);
	}

	/**
	 * Sjekker om databasen finnes gjennom å se om referansenoden er koblet til
	 * en stasjon.
	 */
	public boolean graphExists() {
		boolean res = false;
		Iterable<Relationship> relationships = getReferencenode().getRelationships(Station.TABLE, Direction.OUTGOING);
		for (@SuppressWarnings("unused")
		Relationship r : relationships) {
			res = true;
		}
		return res;
	}

	public List<Station> getAvailableStations() {
		List<Station> res = new ArrayList<Station>();
		Transaction tx = database.beginTx();
		try {
			Iterable<Relationship> relationships = getReferencenode().getRelationships(Station.TABLE, Direction.OUTGOING);
			for (Relationship relationship : relationships) {
				Station station = new Station(relationship.getEndNode());
				res.add(station);
			}
		} catch (Exception e) {
			logger.error("Klarte ikke å finne valgbare stasjoner", e);
			tx.failure();
		} finally {
			tx.finish();
		}
		return res;
	}
}
