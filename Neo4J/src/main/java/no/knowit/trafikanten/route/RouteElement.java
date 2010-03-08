package no.knowit.trafikanten.route;

import no.knowit.trafikanten.domain.Connectiontype;

class RouteElement {
	
	private final String destination;
	private final Connectiontype travelType;
	private final Integer duration;

	RouteElement(String destination, Integer duration, Connectiontype travelType) {
		super();
		this.destination = destination;
		this.duration = duration;
		this.travelType = travelType;
	}

	String getDestination() {
		return destination;
	}

	Connectiontype getTravelType() {
		return travelType;
	}

	Integer getDuration() {
		return duration;
	}

	@Override
	public String toString(){
		return "Traveltype: "+travelType+" Destination: "+destination+" Duration "+duration;
	}
}
