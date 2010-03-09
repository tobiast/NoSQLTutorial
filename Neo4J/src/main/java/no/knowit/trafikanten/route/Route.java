package no.knowit.trafikanten.route;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import no.knowit.trafikanten.domain.Connectiontype;

/**
 * Denne klassen representerer en rute. 
 */
public class Route implements Iterable<RouteElement>
{ 

	/** Samtlige element i ruten untagen det første */
	private final List<RouteElement> routeElements = new LinkedList<RouteElement>();
	
	/** Startpunktet for denne ruten er definert mha denne strengen. */
	private final String from;

	public Route(String from) {
		this.from = from;
	}

	public void addRouteelement(String destination, Integer duration, Connectiontype travelType) {
		RouteElement routeElement = new RouteElement(destination, duration, travelType);
		routeElements.add(routeElement);
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
