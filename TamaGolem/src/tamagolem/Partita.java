package tamagolem;

import java.util.ArrayList;
import java.util.Objects;

public class Partita {
	private static final String TITOLO_MENU_DIFFICOLTA = "DIFFICOLTA' DELLA PARTITA";
	private static final String RICHIESTA_NOME_GIOCATORE = "Giocatore %s inserisci il tuo nome: ";
	final static String TITOLO_MENU = "SCELTA ELEMENTI PIETRA";
	final static String MENU_DIFFICOLTA[]= {"Facile", "Medio", "Difficile"};
	
	private Equilibrio equilibrio_elementi;
	private int numero_elementi_partita;
	private Giocatore giocatore_A;
	private Giocatore giocatore_B;
	private int num_pietre_tamagolem;
	private int num_pietre_scorta;
	private int num_pietre_per_elemento;
	private ArrayList<Pietra> scorta = new ArrayList<>();
	
	/**
	 * creazione di un nuovo giocatore
	 * @param arg  stringa per differenziare giocatori
	 * @return Giocatore creato
	 */ 
	public Giocatore setGiocatore(String arg) {
		String nome_giocatore = new String();
		nome_giocatore = InputDati.leggiStringaNonVuota(String.format(RICHIESTA_NOME_GIOCATORE, arg));
		Giocatore giocatore = new Giocatore (nome_giocatore);
		return giocatore;
	}
	
	/**
	 * il giocatore sceglie gli elementi da associare alle pietre
	 * @param giocatore
	 * @param numero_pietre
	 */
	
	
	public  void evocationPhase(Giocatore giocatore) {
		Pietra pietra = null;
		int n_elemento;
		Pietra[] pietre = new Pietra[num_pietre_tamagolem];
		MyMenu menu = new MyMenu(TITOLO_MENU, Elementi.getNomeElementi(numero_elementi_partita));
		menu.stampaMenu();
		for(int i = 0; i< num_pietre_tamagolem && scorta.size() > 0; i++) {
			pietra = new Pietra();
		    do {
		    	n_elemento = menu.scegli();
		    	if(calcolaPietreRimanentiScorta(n_elemento)>0) {
					pietra.setElement(Elementi.getElementoDaValore(n_elemento));
					togliPietraScelta(pietra);
					pietre[i] = pietra;
				}
				else {
					System.out.println("Attenzione, non ci sono più pietre disponibili dell'elemento selezionato");
				}
		    }while (Objects.isNull(pietra.getElement()));
		}
		giocatore.evocazioneTamaGolem(pietre);
		System.out.println("");
	}
	
	/**
	 * due tamagolem si scagliano le pietre a vicenda infliggendosi i danni i cui valori sono regolati dall'equilibrio del mondo
	 * questo scontro continua fino a quando uno dei due tamagolem viene sconfitto
	 * @param tg1
	 * @param tg2
	 * @return lettera che indica a quale dei due giocatori è stato sconfitto il tamaGolem
	 */
	public Giocatore battlePhase(TamaGolem tg1, TamaGolem tg2) {
		Giocatore giocatore_tg_abbattuto;
		int danno;
		Pietra pietra_tg1, pietra_tg2;
		do {
			pietra_tg1 = tg1.trowPietra();
			pietra_tg2 = tg2.trowPietra();
			Elementi elemento_dominante = equilibrio_elementi.verificaElementoDominante(pietra_tg1.getElement(), pietra_tg2.getElement());
			if(pietra_tg1.getElement().equals(elemento_dominante)) {
				danno = equilibrio_elementi.getDanno(pietra_tg1.getElement(), pietra_tg2.getElement());
				tg2.infliggiDanno(danno);
				
				//prova
				System.out.println(String.format("il TamaGolem di %s ha infitto %d danni al TamaGolem di %s", giocatore_A.getNome_giocatore(), danno, giocatore_B.getNome_giocatore()));
			} else {
				danno = equilibrio_elementi.getDanno(pietra_tg2.getElement(), pietra_tg1.getElement());
				tg1.infliggiDanno(danno);
				
				//prova
				System.out.println(String.format("il TamaGolem di %s ha infitto %d danni al TamaGolem di %s", giocatore_B.getNome_giocatore(), danno, giocatore_A.getNome_giocatore()));
			}
			giocatore_tg_abbattuto = verificaSconfitto(tg1, tg2);
			if(Objects.isNull(giocatore_tg_abbattuto)) {
				
			} else {
				//prova
				System.out.println(String.format("il TamaGolem di %s è stato sconfitto", giocatore_B.getNome_giocatore()));
			}
		}while(Objects.isNull(giocatore_tg_abbattuto));
		
		return giocatore_tg_abbattuto;
	}
	
	
	public Giocatore verificaSconfitto (TamaGolem tg1, TamaGolem tg2) {
		if(tg1.getHealthPoints() <= 0) {
			return giocatore_A;
		}else if(tg2.getHealthPoints() <= 0) {
			return giocatore_B;
		} else {
			return null;
		}
		
	}
	
	/*public Giocatore verificaGiocatoreTGSconfitto (TamaGolem tg_abbatuto) {
		if(giocatore_A.getTamaGolem().equals(tg_abbatuto)) {
			return giocatore_A;
		} else {
			return giocatore_B;
		}
	}*/
	
	
	
	
	/**
	 * l'utente sceglie il linello di difficolta della partita
	 * @return false per uscire, true difficolta scelta
	 */
	public boolean setDifficolta() {
		MyMenu menu = new MyMenu(TITOLO_MENU_DIFFICOLTA, MENU_DIFFICOLTA);
		menu.stampaMenu();
		int difficolta_partita= menu.scegli();
		switch(difficolta_partita) {
			case 1:	//facile
				numero_elementi_partita=NumeriCasuali.estraiIntero(3, 5);
				break;
			case 2:	//medio
				numero_elementi_partita=NumeriCasuali.estraiIntero(6, 8);
				break;
			case 3:	//difficile
				numero_elementi_partita=NumeriCasuali.estraiIntero(9, 10);
				break;
			default: return false;
		}
	
		return true;
	}
	
	/**
	 * in base al livello di difficoltà della partita i parametri di gioco cambiano
	 */
	public void calcolaVariabili() {
		
		int num_tamagolem_giocatore=0;
		
		num_pietre_tamagolem=Math.round((float)(numero_elementi_partita+1)/3) +1;
		
		num_tamagolem_giocatore=Math.round((float)((numero_elementi_partita - 1)*(numero_elementi_partita - 2)) / (num_pietre_tamagolem*2));
		giocatore_A.setNumero_tamagolem_giocatore(num_tamagolem_giocatore);
		giocatore_B.setNumero_tamagolem_giocatore(num_tamagolem_giocatore);
		
		num_pietre_scorta = Math.round((float)((2*numero_elementi_partita*num_pietre_tamagolem)/numero_elementi_partita)) * numero_elementi_partita;
		num_pietre_per_elemento = num_pietre_scorta / numero_elementi_partita;
	}
	
	/**
	 * in base al numero di pietre disponibili per l'intera partita creo una scorta da cui i giocatori possono attingere per definire i propri 
	 * tamaGolems
	 */
	public void definisciScorta() {
		for(int valore_elemento  = 1; valore_elemento <= numero_elementi_partita; valore_elemento++) {
			for(int cicli_x_elemento = 0; cicli_x_elemento < num_pietre_per_elemento; cicli_x_elemento++) {
				Pietra pietra = new Pietra();
				pietra.setElement(Elementi.getElementoDaValore(valore_elemento));
			    scorta.add(pietra);
			}
		}
	}
	
	
	public int calcolaPietreRimanentiScorta(int valore_elemento) {
		int num_pietre_rimanenti = 0;
		
		for(Pietra p : scorta) {
			if(p.getElement().equals(Elementi.getElementoDaValore(valore_elemento))) {
				num_pietre_rimanenti ++;
			}
		}
		
		return num_pietre_rimanenti;
	}
	
	public void togliPietraScelta(Pietra pietra) {
		boolean tolta = false;
		for(int i = 0; !tolta; i++) {
			if(scorta.get(i).getElement().equals(pietra.getElement())) {
				scorta.remove(i);
				tolta = true;
			}
		}
	}
	
	public void setEquilibrio() {
		equilibrio_elementi=new Equilibrio(numero_elementi_partita);
		equilibrio_elementi.generaEquilibrio();		
	}
	
	public void creaGiocatori() {
		giocatore_A= this.setGiocatore("A");
		giocatore_B= this.setGiocatore("B");		
	}
	
	public void avviaPartita() {
		Giocatore giocatore_sconfitto = null;
		calcolaVariabili();
		//creazione scorta
		definisciScorta();
		//INIZIO PARTITA
		/*//EVOCAZIONE DEI DUE GIOCATORI
		 * WHILE(NUMERO TAMAGOLEM CONSENTITI){
		 *    do{
		 *      BATTAGLIA;
		 *      }while(BATTAGLIA FINITA);
		 *  EVOCAZIONE A O B;
		 *  }
		 */
		evocationPhase(giocatore_A);
		evocationPhase(giocatore_B);
		do {
			do {
				giocatore_sconfitto = battlePhase(giocatore_A.getTamaGolem(), giocatore_B.getTamaGolem());
			}while(Objects.isNull(giocatore_sconfitto));
			if (giocatore_A.equals(giocatore_sconfitto) && giocatore_A.getNumero_tamagolem_giocatore() > 0) {
				evocationPhase(giocatore_A);
			}else {
				if(giocatore_B.getNumero_tamagolem_giocatore() > 0) evocationPhase(giocatore_B);
			}
		}while((giocatore_A.getNumero_tamagolem_giocatore() > 0 && giocatore_B.getNumero_tamagolem_giocatore() > 0));
        if (giocatore_A.equals(giocatore_sconfitto)) {
			System.out.println("il giocatoreA è stato sconfitto");
		}else {
			System.out.println("il giocatoreB è stato sconfitto");
		}
	}


}
