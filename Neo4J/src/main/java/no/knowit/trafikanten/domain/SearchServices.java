package no.knowit.trafikanten.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import no.knowit.trafikanten.route.Route;

import org.apache.log4j.Logger;
import org.neo4j.graphalgo.shortestpath.CostAccumulator;
import org.neo4j.graphalgo.shortestpath.CostEvaluator;
import org.neo4j.graphalgo.shortestpath.Dijkstra;
import org.neo4j.graphalgo.shortestpath.std.IntegerAdder;
import org.neo4j.graphalgo.shortestpath.std.IntegerComparator;
import org.neo4j.graphalgo.shortestpath.std.IntegerEvaluator;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
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
		List<Station> res = new ArrayList<Station>();
		Node referencenode = getReferencenode();
		Iterable<Relationship> relationships = referencenode.getRelationships(Station.TABLE, Direction.OUTGOING);
		for (Relationship relationship : relationships) {
			Node currentNode = relationship.getEndNode();
			Station station = new Station(currentNode);
			boolean matches = station.getName().matches(regexp);
			if(matches){
				res.add(station);
			}
		}
		return res ;
	}

	public boolean connectionExists(Station from, Station to) {
		Node fromNode = from.getUnderlyingNode();
		
		Order traversalOrder = Order.DEPTH_FIRST;
		StopEvaluator stopEvaluator = StopEvaluator.END_OF_GRAPH;
		ReturnableEvaluator returnableEvaluator = ReturnableEvaluator.ALL_BUT_START_NODE;
		Traverser traverser = fromNode.traverse(traversalOrder , stopEvaluator , returnableEvaluator , OUTGOING_TRAVELTYPES);
		
		for (Node node : traverser) {
			Station currentStation = new Station(node);
			boolean equals = currentStation.equals(to);
			if (equals) {
				return true;
			}
		}
		
		return false;
	}

	public Route findRoute(Station from, Station to) {
		Route res = new Route(from.getName());
		
		Integer startCost = 0;
		Node startNode = from.getUnderlyingNode();
		Node endNode = to.getUnderlyingNode();
		CostEvaluator<Integer> costEvaluator = new IntegerEvaluator("duration");
		CostAccumulator<Integer> costAccumulator = new IntegerAdder();
		Comparator<Integer> costComparator = new IntegerComparator();
		Direction direction = Direction.OUTGOING;
		Connectiontype[] connectionTypes = Connectiontype.values();
		Dijkstra<Integer> dijkstra = new Dijkstra<Integer>(startCost , startNode , endNode , costEvaluator , costAccumulator , costComparator , direction , connectionTypes);
		
		List<Relationship> relationships = dijkstra.getPathAsRelationships();
		for (Relationship relationship : relationships) {
			Connection currentConnection = new Connection(relationship);
			String destination = currentConnection.getDestination().getName();
			Integer duration = currentConnection.getDuration();
			Connectiontype travelType = currentConnection.getTravelType();
			res.addRouteelement(destination, duration, travelType);
		}
		
		return res ;
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
