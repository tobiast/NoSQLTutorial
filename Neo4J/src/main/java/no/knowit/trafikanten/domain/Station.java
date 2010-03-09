package no.knowit.trafikanten.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

/**
 * Denne klassen representerer en Stasjon. Klassen er en wrapper til en underliggende node som er persistert i databasen.
 * Endringer i objekt av denne typen vil persisteres i databasen.
 */
public class Station {

	/** Denne konstanten brukes til å spesifisere relasjonen mellom denne typen noder og referansenoden */
	public static final RelationshipType TABLE = new RelationshipType() {
		@Override
		public String name() {
			return Station.class.getSimpleName();
		}
	};

	/**
	 * Instanser av denne klassen fåes via DomainServices 
	 */
	Station(Node node) {
		this.underlyingNode = node;
	}

	public void setName(String name) {
		this.underlyingNode.setProperty("name", name);
	}

	public String getName() {
		return (String) this.underlyingNode.getProperty("name");
	}

	public List<Connection> getConnections(){
		List<Connection> res = new ArrayList<Connection>(); 
		Iterable<Relationship> relationships = this.underlyingNode.getRelationships(Direction.OUTGOING);
		for (Relationship relationship : relationships) {
			res.add(new Connection(relationship));
		}
		return res;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	private final Node underlyingNode;
	
	
	Node getUnderlyingNode() {
		return underlyingNode;
	}
	
	@Override
	public int hashCode() {
		return (int) underlyingNode.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (underlyingNode == null) {
			if (other.underlyingNode != null)
				return false;
		} else if (underlyingNode.getId() != other.getUnderlyingNode().getId())
			return false;
		return true;
	}

	public void addConnection(Station destination, int duration, Connectiontype traveltype) {
		Relationship relationship = this.underlyingNode.createRelationshipTo(destination.getUnderlyingNode(), traveltype);
		Connection connection = new Connection(relationship);
		connection.setDuration(duration);
	}

}
