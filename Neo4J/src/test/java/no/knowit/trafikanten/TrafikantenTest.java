package no.knowit.trafikanten;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import no.knowit.trafikanten.domain.Station;
import no.knowit.trafikanten.route.Route;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.Transaction;

public class TrafikantenTest {

	private static Logger logger = Logger.getLogger(TrafikantenTest.class);

	private static Trafikanten app;

	private static File temp;

	/**
	 * Denne initrutinen skaper en testdatabase i OS:ets temp-folder. Databasen
	 * slettes etter att testene er kjørt.
	 * 
	 * @throws IOException
	 */
	@BeforeClass
	public static void init() throws IOException {
		temp = File.createTempFile("trafikanten", "tdb");
		temp.delete();
		temp.mkdir();
		temp.deleteOnExit();
		String dbLocation = temp.getAbsolutePath();
		logger.info("Skaper database i " + dbLocation);
		app = new Trafikanten(dbLocation);

	}

	@Test
	public void testCreateDatabase() {
		Transaction tx = app.beginTransaction();
		try {
			app.createDatabase();
			List<Station> stations = app.getAvailableStations();
			for (Station station : stations) {
				Assert.assertNotNull(station.getName());
			}
			tx.success();
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.fail("Databasen kunne ikke skapes!");
			tx.failure();
		} finally {
			tx.finish();
		}
	}

	@Test
	public void testSearch() {
		Transaction tx = app.beginTransaction();
		List<Station> list = null;
		try {
			list = app.searchForStation(".*o.*");
			tx.success();
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.fail("Misslykket søk!");
			tx.failure();
		} finally {
			tx.finish();
		}
		Assert.assertEquals("Ved søk på stasjoner som matcher .*o.* skal rett resultat returneres.", 6, list.size());
	}

	@Test
	public void testSjekkAtForbindelseFinnes() {
		Transaction tx = app.beginTransaction();
		boolean connectionExists = false;
		try {
			Station bislett = getStationByName("Bislett");
			Station kronstad = getStationByName("Kronstad");
			connectionExists = app.connectionExists(bislett, kronstad);
			tx.success();
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.fail("Klarer ikke å finne ut om forbindelse finnes.");
			tx.failure();
		} finally {
			tx.finish();
		}
		Assert.assertTrue("Forbindelse mellom bislett og kronstad skal finnes", connectionExists);
	}

	@Test
	public void testHentingRute() {
		Transaction tx = app.beginTransaction();
		Route route = null;
		try {
			Station majorstuen = getStationByName("Majorstuen");
			Station jernbanetorget = getStationByName("Jernbanetorget");
			route = app.planRoute(jernbanetorget, majorstuen);
			tx.success();
		} catch (Exception e) {
			logger.info(e.getMessage());
			Assert.fail("Klarte ikke å lage rute!");
			tx.failure();
		} finally {
			tx.finish();
		}
		Assert.assertTrue("Rute mellom jernbanetorget og majorstuen skal bestå av maks to stopp.", route.getHops() <= 2);
	}
	
	// HELPER
	private Station getStationByName(String stationName) {
		List<Station> stations = app.searchForStation(stationName);
		Assert.assertTrue("Søk på gyldig stasjonsnavn, " + stationName + ", ska gi ett treff.", stations.size() == 1);
		return stations.get(0);
	}
}
