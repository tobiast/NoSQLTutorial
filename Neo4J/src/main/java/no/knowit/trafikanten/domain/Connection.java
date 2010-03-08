package no.knowit.trafikanten.domain;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * Klassen representerer en kobling mellom to stasjoner.
 * Denne klassen er en wrapper for databaserepresentasjonen av tilsvarende kobling.
 *
 */
public class Connection {

	private static final String DURATION = "duration";
	private final Relationship underlyingRelationship;
	
	/**
	 * Instanser av denne klassen fåes via DomainServices 
	 */
	Connection(Relationship relationship) {
		this.underlyingRelationship = relationship;
	}

	/** 
	 * Vi ønsker ikke å eksponere neo4j-logikk utenfor domain-pakken
	 */
	Relationship getUnderlyingRelationship() {
		return underlyingRelationship;
	}

	public Station getDestination() {
		Node node = this.underlyingRelationship.getEndNode();
		return new Station(node);
	}

	public Connectiontype getTravelType() {
		String name = (String) this.underlyingRelationship.getType().name();
		return Connectiontype.valueOf(name);
	}

	public void setDuration(int duration) {
		this.underlyingRelationship.setProperty(DURATION, duration);
	}
	
	public int getDuration(){
		return (Integer)this.underlyingRelationship.getProperty(DURATION);
	}

}
