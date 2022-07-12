package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulator {

	List<Team > squadre = new ArrayList<>();
	//dati in ingresso
	
	private Team home;
	private Team away;
	private int giocatoriH;
	private int giocatoriA;
	
	
	//dati in uscita
	private int goalA;
	private int goalH;
	private int espulsiH;
	private int espulsiA;
	
	//modello del mondo
	private int n;  // numero azioni salienti nel match
	
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	
	
	
	public void setSquadre(List<Team> squadre) {
		this.squadre = squadre;
	}

	public Simulator() {
		
	}
	
	public void init(int n) {
		this.n=n;
		home = squadre.get(0);
		away = squadre.get(1);
		
		home.setNGiocatori(11);
		giocatoriH = home.getNGiocatori();
		away.setNGiocatori(11);
		giocatoriA= away.getNGiocatori();
		home.setGoal(0);
		goalH= home.getGoal();
		away.setGoal(0);
		goalA= away.getGoal();
		
		//espulsiS1=0;
		//espulsiS2=0;
		
		this.queue= new PriorityQueue<>();
		
		for(int i=0; i<=n; i++) {
			Event e = new Event(home, away);
			
			if(e.getProbabilita()<=0.5) {  //probabilità 50%
				
				e.setTipo(EventType.GOAL);
			
				this.queue.add(e);
				
			}else if(e.getProbabilita()<=0.8) {   //probabilità 30%
				
				e.setTipo(EventType.ESPULSIONE);
				
				this.queue.add(e);
				
			}else {   //probabilità 20%
				
				e.setTipo(EventType.INFORTUNIO);
				this.queue.add(e);

				double caso2= Math.random();
				if(caso2<=0.5) {
					n+=2;
				}
				else {
					n+=3;
				}
				
			}
			
		}
		
	}
	
	public void run(Player best) {
		while(!this.queue.isEmpty()) {
			Event e= this.queue.poll();
			processEvent(e, best);
		}
	}

	private void processEvent(Event e, Player best) {
		
		switch (e.getTipo()) {
		case GOAL:
			if(e.getSqudraH().getNGiocatori()>e.getSquadraA().getNGiocatori()) {
				this.goalH++;
				home.setGoal(goalH);
			}
			else if(e.getSqudraH().getNGiocatori()<e.getSquadraA().getNGiocatori()) {
				this.goalA++;
				away.setGoal(goalA);
			}
			else {
				
				if(best.getSquadra().equals(home)) {
					goalH++;
					home.setGoal(goalH);
				}
				else {
					goalA++;
					away.setGoal(goalA);
				}
			}
			break;

		case ESPULSIONE:
			double caso= Math.random();
			if(caso<=0.6) {
				if(best.getSquadra().equals(home)) {
					giocatoriH--;
					home.setNGiocatori(giocatoriH);
				}
				else {
					giocatoriA--;
					away.setNGiocatori(giocatoriA);
				}
			}
			else {
				if(!best.getSquadra().equals(home)) {
					giocatoriH--;
					home.setNGiocatori(giocatoriH);
				}
				else {
					giocatoriA--;
					away.setNGiocatori(giocatoriA);
				}
				
			}
			break;
		case INFORTUNIO:
			
//			double caso2= Math.random();
//			if(caso2<=0.5) {
//				nAzioni+=2;
//			}
//			else {
//				nAzioni+=3;
//			}
			break;
		}
		
	}
	
	

		
	
}
