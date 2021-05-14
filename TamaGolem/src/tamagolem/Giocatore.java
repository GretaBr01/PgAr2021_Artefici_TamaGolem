package tamagolem;

import java.util.ArrayList;

public class Giocatore {
	private String nome_giocatore;
	private TamaGolem tamagolem ;
	private int numero_tamagolem_giocatore;
	
	/**
	 * costruttore della classe Giocatore
	 * @param nome del giocatore che si crea
	 */
	public Giocatore(String nome) {
		nome_giocatore = nome;		
	}
	
	/**
	 * @return nome del giocatore
	 */
	public String getNome_giocatore() {
		return nome_giocatore;
	}
	
	/**
	 * @return numero di TamaGolem rimasti al giocatore
	 */
	public int getNumero_tamagolem_giocatore() {
		return numero_tamagolem_giocatore;
	}

	/**
	 * settaggio numero max di TamaGolem per giocatore
	 * @param num numero dei TamaGolem che il Giocatore puo' evocare
	 */
	public void setNumero_tamagolem_giocatore(int num) {
		this.numero_tamagolem_giocatore = num;		
	}

	/**
	 * creazione di un TamaGolem del giocatore
	 * @param _pietre vettore delle pietre selezionate dal giocatore
	 */
	public void evocazioneTamaGolem (Pietra[]_pietre) {
			tamagolem = new TamaGolem();
		    tamagolem.setTamaGolem(_pietre);
			
	}
	
	/**
	 * Decremento del numero di TamaGolem del giocatore nel caso in cui venga sconfitto in battaglia
	 */
	public void morteTamagolem() {
		this.numero_tamagolem_giocatore --;
	}
	
	/** 
	 * @return TamaGolem del Giocatore
	 */
	public TamaGolem getTamaGolem() {
		return this.tamagolem;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		if (nome_giocatore == null) {
			if (other.nome_giocatore != null)
				return false;
		} else if (!nome_giocatore.equals(other.nome_giocatore))
			return false;
		if (numero_tamagolem_giocatore != other.numero_tamagolem_giocatore)
			return false;
		if (tamagolem == null) {
			if (other.tamagolem != null)
				return false;
		} else if (!tamagolem.equals(other.tamagolem))
			return false;
		return true;
	}
	
	/**
	 * verifica che i due nomi dei giocatori siano diversi
	 * @param g giocatore con cui confrontare il nome
	 * @return true se i nomi sono diversi, false su nomi uguali
	 */
	public boolean nomeValido(Giocatore g) {
		if(this.nome_giocatore.equals(g.nome_giocatore)) {
			OutputStringhe.erroreNome();			
			return false;
		}else {
			return true;
		}
	}
}
