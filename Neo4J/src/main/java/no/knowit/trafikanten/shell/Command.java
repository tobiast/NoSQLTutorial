package no.knowit.trafikanten.shell;

import java.io.IOException;
import java.util.List;

import no.knowit.trafikanten.domain.Station;
import no.knowit.trafikanten.route.Route;
import no.knowit.trafikanten.route.Routeprinter;

public abstract class Command {

	private String name;

	public Command(String name) {
		this.name = name;
	}

	public abstract void run();

	@Override
	public String toString() {
		return name;
	}

	static final Command EXIT_COMMAND = new Command("Avslutt") {
		@Override
		public void run() {
			System.out.println("Heidå!");
			System.exit(0);
		}
	};
	static final Command MIN_HOP_COMMAND = new Command("Planlegg rute mellom A og B") {
		@Override
		public void run() {
			getRoute();
		}
	};

	static final Command INIT_COMMAND = new Command("Initiering av grafdatabase") {
		@Override
		public void run() {
			initDatabase();
		}
	};

	static final Command HELP_COMMAND = new Command("Hjelp") {
		@Override
		public void run() {
			TrafikantenShell.printHelpmessage();
		}
	};

	static final Command FIND_STATION_COMMAND = new Command("Finn stasjon") {
		@Override
		public void run() {
			findStation();
		}
	};

	public static final Command ROUTE_EXISTS_COMMAND = new Command("Finnes rute mellom A og B?") {
		@Override
		public void run() {
			routeExistsBetween();
		}
	};

	private static void getRoute() {
		try {
			Station to;
			Station from;
			List<Station> stations = TrafikantenShell.trafikanten.getAvailableStations();
			System.out.println("Velg startstasjon: ");
			from = pickStation(stations);
			System.out.println("Velg endestasjon: ");
			to = pickStation(stations);
			Route route = TrafikantenShell.trafikanten.planRoute(from, to);
			String result = route == null ? "Rute fanns ikke" : new Routeprinter(route).toString();
			System.out.println(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void initDatabase() {
		try {
			TrafikantenShell.trafikanten.createDatabase();
			System.out.println("Databasen ble initiert!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void findStation() {
		try {
			String searchTerm = enterSearchterm();

			System.out.println("Søker etter stasjoner som matcher '" + searchTerm + "'");
			List<Station> result = TrafikantenShell.trafikanten.searchForStation(searchTerm);

			presentSearchResult(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static Station pickStation(List<Station> stations) throws IOException {
		int currentIndex = 0;
		for (Station station : stations) {
			System.out.println(++currentIndex + " " + station.getName());
		}
		String stationIndex = TrafikantenShell.readCommand();
		int s = Integer.valueOf(stationIndex);
		Station station = stations.get(s - 1);
		System.out.println("Du valgte " + station.getName());
		return station;
	}

	private static void presentSearchResult(List<Station> result) {
		System.out.println("Fant " + result.size() + " stasjoner:");
		for (Station station : result) {
			System.out.println(station.getName());
		}
	}

	private static String enterSearchterm() throws IOException {
		System.out.println("Skriv in søketermen: ");
		String searchTerm = TrafikantenShell.readCommand();
		searchTerm.trim();
		searchTerm.replaceAll("\\*", ".*");
		searchTerm = ".*" + searchTerm + ".*";
		return searchTerm;
	}

	public static void routeExistsBetween() {
		try {
			Station to;
			Station from;
			List<Station> stations = TrafikantenShell.trafikanten.getAvailableStations();
			System.out.println("Velg startstasjon: ");
			from = pickStation(stations);
			System.out.println("Velg endestasjon: ");
			to = pickStation(stations);
			boolean connectionExists = TrafikantenShell.trafikanten.connectionExists(from, to);
			String svar = connectionExists ? "Ja" : "Nei";
			System.out.println("Svar: " + svar);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
