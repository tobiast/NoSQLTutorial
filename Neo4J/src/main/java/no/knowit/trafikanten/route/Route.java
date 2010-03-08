package no.knowit.trafikanten.route;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import no.knowit.trafikanten.domain.Connectiontype;

/**
 * Denne klassen representerer en rute. Denne er laget så immutable som mulig. 
 */
public class Route implements Iterable<RouteElement>
{ 

	/**
	 * For at Route skal være immutable så brukes denne Builderen for å skape instanser av Route. 
	 */
	public static class Builder {
		
		private final List<RouteElement> routeElements = new LinkedList<RouteElement>();
		private final String from;
		
		public Builder(String from){
			this.from = from;
		}
		
		public Builder addRouteelement(String destination, Integer duration, Connectiontype travelType) {
			RouteElement routeElement = new RouteElement(destination, duration, travelType);
			routeElements.add(routeElement);
			return this;
		}

		public Route build() {
			return new Route(from, routeElements);
		}

	}

	/** Samtlige element i ruten untagen det første */
	private final List<RouteElement> routeElements = new LinkedList<RouteElement>();
	
	/** Startpunktet for denne ruten er definert mha denne strengen. */
	private final String from;

	private Route(String from, List<RouteElement> routeElements) {
		this.from = from;
		this.routeElements.addAll(routeElements);
	}

	@Override
	public Iterator<RouteElement> iterator() {
		return this.routeElements.iterator();
	}

	/**
	 * Returnerer en string som beskriver instansen.
	 * Denne metoden skal ikke brukes i UI.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (RouteElement routeElement : this) {
			sb.append(routeElement).append("\n");
		}
		return sb.toString();
	}

	public String getFrom() {
		return this.from;
	}

	public String getTo() {
		int index = this.routeElements.size()-1;
		return this.routeElements.get(index).getDestination();
	}

	public int getTotalDuration() {
		int res = 0;
		for (RouteElement r : routeElements) {
			res += r.getDuration();
		}
		return res;
	}

	public int getHops() {
		return this.routeElements.size();
	}

}
