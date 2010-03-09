package no.knowit.trafikanten.init;

import no.knowit.trafikanten.domain.DomainServices;
import no.knowit.trafikanten.domain.Station;
import no.knowit.trafikanten.domain.Connectiontype;

import org.apache.log4j.Logger;

/**
 * Denne klassen produserer en database som kan brukes til å utforske noen av neo4j:s finesser. 
 */
public class Initializer {

	private static Logger logger = Logger.getLogger(Initializer.class);

	public void initDatabase(DomainServices domainServices) {

		Station majorstuen = domainServices.createStation("Majorstuen");
		Station bislett = domainServices.createStation("Bislett");
		Station tullinlokka = domainServices.createStation("Tullinløkka");
		Station nasjonalteateret = domainServices.createStation("Nasjonalteateret");
		Station jernbanetorget = domainServices.createStation("Jernbanetorget");
		
		Station gardermoen = domainServices.createStation("Gardermoen");
		Station altaLufthavn = domainServices.createStation("Alta Lufthavn");
		Station sentrum = domainServices.createStation("Alta Sentrum");
		Station city = domainServices.createStation("Alta City");
		Station kronstad = domainServices.createStation("Kronstad");
		Station bossekopp = domainServices.createStation("Bossekopp");
		Station elvebakken = domainServices.createStation("Elvebakken");

		domainServices.createBidirectionalConnection(majorstuen, nasjonalteateret, 5, Connectiontype.SUB);
		domainServices.createBidirectionalConnection(majorstuen, nasjonalteateret, 12, Connectiontype.TRAM);
		domainServices.createBidirectionalConnection(jernbanetorget, nasjonalteateret, 5, Connectiontype.SUB);
		domainServices.createBidirectionalConnection(jernbanetorget, nasjonalteateret, 8, Connectiontype.TRAM);
		domainServices.createBidirectionalConnection(jernbanetorget, tullinlokka, 8, Connectiontype.TRAM);
		domainServices.createBidirectionalConnection(bislett, tullinlokka, 8, Connectiontype.TRAM);
		domainServices.createBidirectionalConnection(bislett, majorstuen, 10, Connectiontype.WALK);
		domainServices.createBidirectionalConnection(nasjonalteateret, tullinlokka, 5, Connectiontype.WALK);
		
		domainServices.createBidirectionalConnection(jernbanetorget, gardermoen, 19, Connectiontype.AIRPORT_EXPRESS);
		domainServices.createBidirectionalConnection(gardermoen, altaLufthavn, 150, Connectiontype.AIRPLANE);
		domainServices.createBidirectionalConnection(altaLufthavn, elvebakken, 8, Connectiontype.WALK);
		domainServices.createBidirectionalConnection(altaLufthavn, city, 15, Connectiontype.AIRPORT_BUS);
		
		domainServices.createBidirectionalConnection(bossekopp, city, 15, Connectiontype.BUS);
		domainServices.createBidirectionalConnection(city, sentrum, 5, Connectiontype.BUS);
		domainServices.createBidirectionalConnection(sentrum, elvebakken, 10, Connectiontype.BUS);
		domainServices.createBidirectionalConnection(elvebakken, kronstad, 10, Connectiontype.BUS);

		logger.info("Du er nå klar for workshopen!");
	}



}