package it.polito.tdp.PremierLeague.model;

public class GiocatoreMIgliore {
	private Player p;
	private double delta;
	public GiocatoreMIgliore(Player p, double delta) {
		super();
		this.p = p;
		this.delta = delta;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public double getDelta() {
		return delta;
	}
	public void setDelta(double delta) {
		this.delta = delta;
	}
	@Override
	public String toString() {
		return "[p=" + p + ", delta=" + delta + "]";
	}
	
	

}