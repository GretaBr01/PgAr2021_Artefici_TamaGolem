package tamagolem;


import it.unibs.fp.mylib.*;

public class Partita {
	final static String TITOLO_MENU = "SCELTA ELEMENTI PIETRA";
	
	public static Giocatore setGiocatore() {
		String nome_giocatore = InputDati.leggiStringaNonVuota("inserisci il tuo nome: ");
		Giocatore giocatore = new Giocatore (nome_giocatore);
		return giocatore;
	}
	
	/**
	 * il giocatore sceglie gli elementi da associare alle pietre
	 * @param giocatore
	 * @param numero_pietre
	 */
	public static void mainPhase(Giocatore giocatore,  int numero_pietre) {
		Pietra pietra;
		Pietra[] pietre = new Pietra[numero_pietre];
		MyMenu menu = new MyMenu(TITOLO_MENU, Elementi.getNomeElementi());
		menu.stampaMenu();
		for(int i = 0; i < numero_pietre; i++) {
			pietra = new Pietra(); 
			int n_elemento = menu.scegli();
			pietra.setElement(Elementi.getNomeDaValore(n_elemento));  
			pietre[i] = pietra; 
		}
		giocatore.evocazioneTamaGolem(pietre, giocatore.getNumSquadra());
		System.out.println("");
	}
	
	
	
	public static void battlePhase(TamaGolem tg1, TamaGolem tg2) {
		
	}

	public static void main(String[] args) {
		int numero_pietre = 3;
		Giocatore giocatore1;
		Giocatore giocatore2;
		
		//set giocatore 1 e 2
		giocatore1 = setGiocatore();
	    
		//sceltaElementi();
		
		//set tamagolem giocatore 1
		mainPhase(giocatore1, numero_pietre);
		//set tamagolem giocatore 2
		
		//battaglia 
		
		//verifica vita tamagolem 
		
		
		//se uno dei due tamagolem muore verifica la disponibiltà di tg
		for(int j = 0;j < 2; j++) {
			for(int i=0; i < 3; i++) {
				System.out.println("E' stata lanciata una pietra di tipo "+ giocatore1.getTamaGolem(0).trowPietra().getElement().toString());
			}
			
		}
		

	}

	


}
