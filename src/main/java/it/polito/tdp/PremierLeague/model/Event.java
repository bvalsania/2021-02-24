package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		GOAL,
		ESPULSIONE,
		INFORTUNIO,
	}
	
	private Team squdraH;
	private Team squadraA;

	
	Double probabilita;
	
	private EventType tipo;

	public Event(Team squadraH, Team squadraA) {
//		this.giocatoriH = giocatoriS1;
//		this.giocatoriA = giocatoriS2;
		this.squdraH= squadraH;
		this.squadraA= squadraA;
		
	}


	public EventType getTipo() {
		return tipo;
	}

	public Team getSqudraH() {
		return squdraH;
	}

	public Team getSquadraA() {
		return squadraA;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	
	

	public Double getProbabilita() {
		probabilita= Math.random();
		return probabilita;
	}

	@Override
	public int compareTo(Event o) {
		
		return this.probabilita.compareTo(o.probabilita);
		
	}	
	
	
}
