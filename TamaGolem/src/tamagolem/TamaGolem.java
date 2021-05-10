package tamagolem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TamaGolem {
	
	private static final int HP_MAX = 100;
	
	private int health_points;
	private ArrayList<Pietra> pietre = new ArrayList<Pietra>();
	private Queue<Pietra> caricatore = new LinkedList<Pietra>();
	//private int numero_pietre_disponibili;
	
	public void listaElementi() {
		
	}
	
	public void setTamaGolem(Pietra[]_pietre) {
		setPietre(_pietre);
		health_points = HP_MAX;
	}
	
	public void setPietre(Pietra[]_pietre) {
		for(Pietra pietra: _pietre ) {
			this.caricatore.add(pietra);
			this.pietre.add(pietra);
		}
	}
	
	
	/**
	 * when trowing a stone it automatically put it on the top of the queue
	 */
	public Pietra trowPietra() {
		 Pietra pietra  = getPietra();
		 caricatore.poll();
		 ricaricaPietra(pietra);
		 return pietra;
	}
	
	public Pietra getPietra() {
		return caricatore.peek();
	}
	
	public void ricaricaPietra(Pietra pietra) {
		caricatore.add(pietra);
	}
	

	public void infliggiDanno(int danno) {
		this.health_points = this.health_points - danno;
	}
	
	public int getHealthPoints() {
		return health_points;
	}


	
	
	
}
