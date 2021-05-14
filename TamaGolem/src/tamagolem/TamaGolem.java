package tamagolem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TamaGolem {
	
	private static final int HP_MAX = 100;//punti vita massimi  
	
	private int health_points;
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
		}
	}
	
	
	/**
	 * quanto viene lanciata una pietra, essa viene riposta automaticamente all'interno del caricatore (queue)
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
	
	/**
	 * il danno passato come argomento viene sottratto ai punti vita del tamagolem
	 * @param danno
	 */
	public void infliggiDanno(int danno) {
		this.health_points = this.health_points - danno;
	}
	
	/**
	 * @return ritorna i punti vita del tamagolem
	 */
	public int getHealthPoints() {
		return health_points;
	}

	/**
	 * @return true se i punti vita del tamagolem sono <= 0, false altrimenti
	 */
	public boolean isSconfitto() {
		if(this.health_points<=0)
			return true;
		else
			return false;
	}

	/**
	 * override metodo equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TamaGolem other = (TamaGolem) obj;
		if (caricatore == null) {
			if (other.caricatore != null)
				return false;
		} else if (!caricatore.equals(other.caricatore))
			return false;
		if (health_points != other.health_points)
			return false;

		return true;
	}
	
	
	
}
