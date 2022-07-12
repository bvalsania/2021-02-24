package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	
	List<Team> squadre= new ArrayList<>();
	Team home;
	Team away;
	
	Player best;
	int match;
	
	String risultatoPartita="";
	int espulsiH;
	int espusliA;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();
		this.dao.listAllPlayers(idMap);
	}
	
	public String creaGrafo(Match m) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getVertici(m, idMap));
		
		for(Coppia c : this.dao.getArchi(m, idMap)) {
			if(c.getPeso()>0) {
				Graphs.addEdge(grafo, c.getP1()	, c.getP2(), c.getPeso());
			}else 
				Graphs.addEdge(grafo, c.getP2(), c.getP1(), (-1)*c.getPeso());
		}
		
		return "Il grafo ha "+this.grafo.vertexSet().size()+" vertici e archi "+this.grafo.edgeSet().size();
	}
	
	public List<Match> getm(){
		return dao.listAllMatches();
	}
	
	
	public GiocatoreMIgliore getGiocatoreMigliore(){
		Player best = null;
		double max = 0.0;
		
		for(Player p : this.grafo.vertexSet()) {
			double pesoUscente = 0.0;
			for(DefaultWeightedEdge edge : this.grafo.outgoingEdgesOf(p)) {
				 pesoUscente += grafo.getEdgeWeight(edge);
			}
			double pesoEntrante = 0.0;
			for(DefaultWeightedEdge edge : grafo.incomingEdgesOf(p)) {
				pesoEntrante += this.grafo.getEdgeWeight(edge);
			}
			
			double delta = pesoUscente-pesoEntrante;
			if(delta>max) {
				max = delta;
				best = p;
				
			}
		}
		return (new GiocatoreMIgliore(best, max));
	}
	
public void simula(int azioni) {
		
		Simulator sim= new Simulator();
		sim.setSquadre(squadre);
		sim.init(azioni);
		sim.run(best);
		risultatoPartita="Il risultato finale della partita è:\n"+home.getName()+": "+home.getGoal()+" --- "+away.getName()+": "+away.getGoal()+"\n"
				+"Espulsi--> "+home.getName()+": "+(11-home.getNGiocatori())+", "+away.getName()+": "+(11-away.getNGiocatori());
	}
	
	public String finale(int azioni) {
		simula(azioni);
		return this.risultatoPartita;
	}
	
	
	
	public Player bestPlayer() {
		return best;
	}
}
