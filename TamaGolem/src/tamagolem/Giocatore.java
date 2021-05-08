package tamagolem;

import java.util.ArrayList;

public class Giocatore {
	String nome_giocatore;
	ArrayList<TamaGolem> squadra = new ArrayList<TamaGolem>();
	
	//int numero_pietre = 0;
	
	public Giocatore(String nome/*, int numero_TG*/) {
		nome_giocatore = nome;
		//squadra = new TamaGolem[numero_TG];
		
	}
	public String getNome_giocatore() {
		return nome_giocatore;
	}
	
	public void setNome_giocatore(String nome_giocatore) {
		this.nome_giocatore = nome_giocatore;
	}
	
	public void evocazioneTamaGolem (Pietra[]_pietre, int n_tg) {
			TamaGolem tg = new TamaGolem();
		    tg.setTamaGolem(_pietre, n_tg);
			aggiungiNellaSquadra(tg);
	}
	
	public void aggiungiNellaSquadra (TamaGolem tg) {
		squadra.add(tg);
	}
	
	public int getNumSquadra() {
		return squadra.size();
	}
	
	public TamaGolem getTamaGolem(int id) {
		for(TamaGolem tg: squadra) {
			if(tg.getIdentificativo() == id) {
				return tg;
			}
		}
		return null;
	}
	


}
