package no.knowit.trafikanten.domain;

import org.neo4j.graphdb.RelationshipType;

/**
 * Denne enumer holder oversikt over hvilke typer av reiser man kan utføre.
 */
public enum Connectiontype implements RelationshipType{
	
	TRAM("Trikk"), SUB("T-bane"), WALK("Spasertur"), AIRPORT_EXPRESS("Flytåget"), BUS("Buss"), AIRPORT_BUS("Flybussen"), AIRPLANE("Flyet");
	
	private final String name;

	private Connectiontype(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}