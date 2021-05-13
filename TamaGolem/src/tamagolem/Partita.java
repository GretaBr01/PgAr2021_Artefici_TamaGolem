package tamagolem;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Partita {
	private static final String OPZIONE_AGGIUNTIVA_MENU_DIFFICOLTA = "ESCI";
	private static final String OPZIONE_AGGIUNTIVA_MENU_ELEMENTI_PIETRA = "VISUALIZZA SCORTA";
	private static final String RICHIESTA_SCELTA_ELEMENTO = "digita il numero dell'elemento da assegnare alla pietra numero %d: ";
	private static final String TITOLO_MENU_DIFFICOLTA = "DIFFICOLTA' DELLA PARTITA";
	private static final String RICHIESTA_NOME_GIOCATORE = "Giocatore %s inserisci il tuo nome: ";
	private final static String TITOLO_MENU = "SCELTA ELEMENTI PIETRA";
	private final static String MENU_DIFFICOLTA[]= {"Facile", "Medio", "Difficile"};
	
	private final static String TITOLO_TAB_SCORTA="PIETRE SCORTA";
	private final static String INTESTAZIONE_TAB_SCORTA[]= {"ELEMENTO", "QUANTITA'"};
	
	final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";
	
	private Equilibrio equilibrio_elementi;
	private int numero_elementi_partita;
	private Giocatore giocatore_A;
	private Giocatore giocatore_B;
	private int num_pietre_tamagolem;
	private int num_pietre_scorta;
	private int num_pietre_per_elemento;
	private ArrayList<Pietra> scorta = new ArrayList<>();
	private Elementi elementi_partita[];
	
	
	/**
	 * l'utente sceglie il livello di difficolta della partita
	 * @return false per uscire, true difficolta scelta
	 */
	public boolean setDifficolta() {
		MyMenu menu_difficolta = new MyMenu(TITOLO_MENU_DIFFICOLTA, MENU_DIFFICOLTA);
		menu_difficolta.stampaMenu(OPZIONE_AGGIUNTIVA_MENU_DIFFICOLTA);
		int difficolta_partita= menu_difficolta.scegli(RICHIESTA_INSERIMENTO);
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
	 * settaggio degli elementi che verranno utilizzti nella partita
	 */
	public void setElementi_partita() {
		elementi_partita=Elementi.estraiElementi(numero_elementi_partita);
	}
	
	/**
	 * settaggio dell'Equilibrio del Mondo: regole che governano le interazioni fra gli elemnti prescelti
	 */
	public void setEquilibrio() {
		equilibrio_elementi=new Equilibrio(elementi_partita);
		equilibrio_elementi.generaEquilibrio();	
		
		//equilibrio_elementi.stampaEquilibrio();
	}
	
	/**
	 * calcolo delle variabili necessarie per l'avvio della partita
	 * in base al livello di difficoltà scelta, i parametri di gioco cambiano
	 */
	public void calcolaVariabili() {		
		int num_tamagolem_giocatore=0;
		
		num_pietre_tamagolem=(int)Math.ceil((numero_elementi_partita+1)/(float)3) +1;
		
		num_tamagolem_giocatore=(int)Math.ceil(((numero_elementi_partita - 1)*(numero_elementi_partita - 2)) / (float)(num_pietre_tamagolem*2));
		giocatore_A.setNumero_tamagolem_giocatore(num_tamagolem_giocatore);
		giocatore_B.setNumero_tamagolem_giocatore(num_tamagolem_giocatore);
		num_pietre_scorta =  (int)Math.ceil(((2*num_tamagolem_giocatore*num_pietre_tamagolem)/(float)numero_elementi_partita))* numero_elementi_partita;
		num_pietre_per_elemento = num_pietre_scorta / numero_elementi_partita;
	}
	
	/**
	 * creazione della scorta comune delle pietre utilizzabili per evocazione TamaGolen
	 * 
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
	
	/**
	 * avvio creazione dei due giocatori
	 */
	public void creaGiocatori() {
		giocatore_A= this.setGiocatore("A");
		do {
			giocatore_B= this.setGiocatore("B");
		}while(!giocatore_B.nomeValido(giocatore_A));
				
	}
	
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
	
	public void avviaPartita() {
		Giocatore giocatore_sconfitto = null;		
		int num_battaglia=0;
		evocationPhase(giocatore_A);
		evocationPhase(giocatore_B);
		OutputStringhe.inizioPartita();
		do {
			num_battaglia++;
			OutputStringhe.stampaNumeroBattaglia(num_battaglia);
			giocatore_sconfitto = battlePhase(giocatore_A.getTamaGolem(), giocatore_B.getTamaGolem());
			giocatore_sconfitto.morteTamagolem();
						
			if (giocatore_A.equals(giocatore_sconfitto) && giocatore_A.getNumero_tamagolem_giocatore() > 0) {
				evocationPhase(giocatore_A);
			}else if(giocatore_B.getNumero_tamagolem_giocatore() > 0) {
				evocationPhase(giocatore_B);
			}
			
		}while((giocatore_A.getNumero_tamagolem_giocatore() > 0 && giocatore_B.getNumero_tamagolem_giocatore() > 0));
		
        finePartita(giocatore_sconfitto);
	}
	
	public void finePartita(Giocatore g){
		OutputStringhe.stampaSconfitto( g.getNome_giocatore());
		String giocatore_vincente = giocatore_B.getNome_giocatore();
		if(g.equals(giocatore_A)) {
			giocatore_vincente = giocatore_A.getNome_giocatore();
		}
		OutputStringhe.stampaVincitore(giocatore_vincente);
        equilibrio_elementi.stampaEquilibrio();
	}
	
	/**
	 * il giocatore sceglie gli elementi da associare alle pietre
	 * @param giocatore
	 * @param numero_pietre
	 */	
	public  void evocationPhase(Giocatore giocatore) {
		Pietra pietra = null;
		int indice_elemento;
		Pietra[] pietre = new Pietra[num_pietre_tamagolem];
		
		OutputStringhe.faseEvocazioneOutput(giocatore.getNome_giocatore(), giocatore.getNumero_tamagolem_giocatore(), num_pietre_tamagolem);
		
		String elementi_menu[]=Elementi.getNomeElementi(elementi_partita);		
		MyMenu menu_elementi_pietra = new MyMenu(TITOLO_MENU, elementi_menu);
		menu_elementi_pietra.stampaMenu(OPZIONE_AGGIUNTIVA_MENU_ELEMENTI_PIETRA);
		
		for(int i = 0; i< num_pietre_tamagolem && scorta.size() > 0; i++) {
			pietra = new Pietra();

			do {
				int scelta = menu_elementi_pietra.scegli(String.format(RICHIESTA_SCELTA_ELEMENTO, i+1));
				if(scelta==0) {
					stampaScorta();
					menu_elementi_pietra.stampaMenu(OPZIONE_AGGIUNTIVA_MENU_ELEMENTI_PIETRA);					
				}else {
					indice_elemento = scelta -1;
					if(calcolaPietreRimanentiScorta(elementi_partita[indice_elemento])>0) {
						pietra.setElement(elementi_partita[indice_elemento]);
						togliPietraScelta(pietra);
						pietre[i] = pietra;
					}else {
						OutputStringhe.errorePietreNonDIsponibili();
					}
				}
			}while (Objects.isNull(pietra.getElement()));	
		}
		
		giocatore.evocazioneTamaGolem(pietre);
		OutputStringhe.clearConsole();
		OutputStringhe.evocazioneCompletata(giocatore.getNome_giocatore());
	}	
	
	public void stampaScorta() {
		//stampa numero pietre per elemento
		
		String riga_tabella[]= new String[INTESTAZIONE_TAB_SCORTA.length];
		
		OutputStringhe.stampaTitoloTabella(TITOLO_TAB_SCORTA);
		OutputStringhe.stampaIntestazioneTabella(INTESTAZIONE_TAB_SCORTA);
		
		for(int j=0; j<elementi_partita.length; j++){
			riga_tabella[0]=elementi_partita[j].name();
			riga_tabella[1]=""+calcolaPietreRimanentiScorta(elementi_partita[j]);
			OutputStringhe.stampaRigaTabella(riga_tabella);
		}
	}
	
	public int calcolaPietreRimanentiScorta(Elementi elemento) {
		int num_pietre_rimanenti = 0;
		
		for(Pietra p : scorta) {
			if(elemento.equals(p.getElement())) {
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
		String elemento_vittoria;
		String elemento_sconfitta;
		String giocatore_sconfitta;
		int turno=0;
		do {
			turno++;
			pietra_tg1 = tg1.trowPietra();
			pietra_tg2 = tg2.trowPietra();
			//giocatore_tg_abbattuto=null;
			if(pietra_tg1.isPietraDominante(pietra_tg2, equilibrio_elementi)) {
				danno = pietra_tg1.getDannoPietra(pietra_tg2, equilibrio_elementi);
				tg2.infliggiDanno(danno);
				elemento_vittoria = pietra_tg1.getElement().name();
				elemento_sconfitta = pietra_tg2.getElement().name();
				giocatore_sconfitta = giocatore_B.getNome_giocatore();
			}else {
				danno= pietra_tg2.getDannoPietra(pietra_tg1, equilibrio_elementi);
				tg1.infliggiDanno(danno);				
				elemento_vittoria = pietra_tg2.getElement().name();
				elemento_sconfitta = pietra_tg1.getElement().name();
				giocatore_sconfitta = giocatore_A.getNome_giocatore();
			}
			
			giocatore_tg_abbattuto = verificaSconfitto(tg1, tg2);
			
			if(!Objects.isNull(giocatore_tg_abbattuto)) {
				OutputStringhe.stampaTamagolemSconfitto(turno, giocatore_tg_abbattuto.getNome_giocatore());
			}else {
				OutputStringhe.stampaDanno(turno, elemento_vittoria, elemento_sconfitta, danno, giocatore_sconfitta);
			}
		}while(Objects.isNull(giocatore_tg_abbattuto));
		
		return giocatore_tg_abbattuto;
	}

	
	public Giocatore verificaSconfitto (TamaGolem tg1, TamaGolem tg2) {
		if(tg1.isSconfitto()) {
			return appartieneAgiocatore(tg1);
		}else if(tg2.isSconfitto()) {
			return appartieneAgiocatore(tg2);
		} else {
			return null;
		}		
	}
	
	public Giocatore appartieneAgiocatore(TamaGolem tg) {
		if(tg.equals(giocatore_A.getTamaGolem())) 
			return giocatore_A;
		else
			return giocatore_B;
	}

}
