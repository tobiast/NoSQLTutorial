package no.knowit.trafikanten.route;


/**
 * Denne klassen skriver ut ruter på en pen og pyntelig måte.
 */
public class Routeprinter {

		private final Route route;

		public Routeprinter(Route route) {
			this.route = route;
		}

		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("Rute mellom ").append(route.getFrom()).append(" og ").append(route.getTo()).append(":\n");
			for (RouteElement r : route) {
				String travelBy = r.getTravelType().getName();
				String dest = r.getDestination();
				Integer dur = r.getDuration();
				sb.append("Ta "+travelBy+" til "+dest+" det tar "+dur+" minutter.");
				sb.append("\n");
			}
			sb.append("Turen tar totalt "+route.getTotalDuration()+" minutter");
			return sb.toString();
		}	
	
}
