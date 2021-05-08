package tamagolem;

import java.util.LinkedList;
import java.util.Queue;

public class TamaGolem {
	
	private static final int HP_MAX = 100;
	
	int health_points;
	int identificativo;
	Queue<Pietra> pietre = new LinkedList<Pietra>();
	
	public void listaElementi() {
		
	}
	
	public void setTamaGolem(Pietra[]_pietre, int pos_tg) {
		setPietre(_pietre);
		this.identificativo = pos_tg;
		health_points = HP_MAX;
	}
	
	public void setPietre(Pietra[]_pietre) {
		for(Pietra pietra: _pietre ) {
			this.pietre.add(pietra);
		}
	}
	
	
	/**
	 * when trowing a stone it automatically put it on the top of the queue
	 */
	public Pietra trowPietra() {
		 Pietra pietra  = getPietra();
		 pietre.poll();
		 ricaricaPietra(pietra);
		 return pietra;
	}
	
	public Pietra getPietra() {
		return pietre.peek();
	}
	
	public void ricaricaPietra(Pietra pietra) {
		pietre.add(pietra);
	}
	
	public int getIdentificativo() {
		return this.identificativo;
	}
	

}
