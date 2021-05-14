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
	 * settaggio degli elementi che verranno utilizzati nella partita
	 */
	public void setElementi_partita() {
		elementi_partita=Elementi.estraiElementi(numero_elementi_partita);
	}
	
	/**
	 * settaggio dell'Equilibrio della partita: regole che governano le interazioni fra gli elemnti prescelti
	 */
	public void setEquilibrio() {
		equilibrio_elementi=new Equilibrio(elementi_partita);
		equilibrio_elementi.generaEquilibrio();	
		
		//equilibrio_elementi.stampaEquilibrio();
	}
	
	/**
	 * calcolo delle variabili necessarie per l'avvio della partita in base al livello di difficoltà scelto
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
	 * creazione della scorta comune delle pietre utilizzabili per evocazione TamaGolem
	 * 
	 * in base al numero di pietre disponibili per l'intera partita creo una scorta da cui i giocatori possono attingere per definire i propri 
	 * tamaGolems
	 */
	public void definisciScorta() {
		for(int i = 0; i < elementi_partita.length; i++) {
			for(int cicli_x_elemento = 0; cicli_x_elemento < num_pietre_per_elemento; cicli_x_elemento++) {
				Pietra pietra = new Pietra();
				pietra.setElement(elementi_partita[i]);
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
	
	/**
	 * 
	 */
	public void avviaPartita() {
		Giocatore giocatore_sconfitto = null;		
		int num_battaglia=0;
		evocationPhase(giocatore_A);//evocazione primo giocatore
		evocationPhase(giocatore_B);//evocazione secondo giocatore
		OutputStringhe.inizioPartita();
		do {
			num_battaglia++;
			OutputStringhe.stampaNumeroBattaglia(num_battaglia);
			giocatore_sconfitto = battlePhase(giocatore_A.getTamaGolem(), giocatore_B.getTamaGolem());//salvo il giocatore a cui muore un tamagolem
			
			if(!Objects.isNull(giocatore_sconfitto)) {//se il giocatore sconfitto punta ad un'istanza di giocatore
				giocatore_sconfitto.morteTamagolem();//decremento il numero di tamagolem disponibili per quel giocatore
			}
			
			if (Objects.isNull(giocatore_sconfitto)) {		//nel caso in cui il giocatore sconfitto sia un oggetto vuoto
				OutputStringhe.stampaBattagliaPareggio();				
				if(giocatore_A.getTamaGolem().getHealthPoints() < giocatore_B.getTamaGolem().getHealthPoints()) { //elimino il tamagolem che ha meno vita, ossia il tamagolem del giocatore che ha copiato il set di pietre del tamagolem già in campo
					giocatore_B.morteTamagolem();
					if(giocatore_B.getNumero_tamagolem_giocatore() > 0) {
						evocationPhase(giocatore_B);
					}					
				} else if (giocatore_B.getTamaGolem().getHealthPoints() < giocatore_A.getTamaGolem().getHealthPoints()){
					giocatore_A.morteTamagolem();
					if( giocatore_A.getNumero_tamagolem_giocatore() > 0) {
						evocationPhase(giocatore_A);
					}
				} else {//se i due tamagolem che hanno lo stesso set vengono evocati contemporaneamente allora vengono eliminati entrambi
					giocatore_A.morteTamagolem();
					giocatore_B.morteTamagolem();
					if (giocatore_A.getNumero_tamagolem_giocatore() > 0 && giocatore_B.getNumero_tamagolem_giocatore() > 0) {
						evocationPhase(giocatore_A);
						evocationPhase(giocatore_B);
					}
				}	
			//nel caso in cui giocatore_sconfitto punti ad un istanza del giocatore 
			}else if (giocatore_A.equals(giocatore_sconfitto) && giocatore_A.getNumero_tamagolem_giocatore() > 0) {//del giocatore A allora avvio un'evacazione per il primo giocatore
				evocationPhase(giocatore_A);
			}else if(giocatore_B.equals(giocatore_sconfitto) && giocatore_B.getNumero_tamagolem_giocatore() > 0) {//altrimenti per il secondo giocatore
				evocationPhase(giocatore_B);
			}
			
		}while((giocatore_A.getNumero_tamagolem_giocatore() > 0 && giocatore_B.getNumero_tamagolem_giocatore() > 0));//la partita continua fino a quando almeno uno dei due giocatori non possiede più tamagolem disponibili per l'evocazione
		
		if(Objects.isNull(giocatore_sconfitto)) {		//in caso che l'ultimo scontro finisca in pareggio dichiaro un vincitore
			if(giocatore_A.getNumero_tamagolem_giocatore() == 0 && giocatore_B.getNumero_tamagolem_giocatore() > 0 ) {
				giocatore_sconfitto = giocatore_A;
			} else if (giocatore_B.getNumero_tamagolem_giocatore() == 0 && giocatore_A.getNumero_tamagolem_giocatore() > 0) {
				giocatore_sconfitto = giocatore_B;
			} 
		}
		
        finePartita(giocatore_sconfitto);
	}
	
	/**
	 * stampo i risultati della partita svolta
	 * @param giocatore_sconfitto
	 */
	public void finePartita(Giocatore giocatore_sconfitto){
		if(!Objects.isNull(giocatore_sconfitto)) {//se il giocatore sconfitto punta ad un'istanza di giocatore allora 
			OutputStringhe.stampaSconfitto( giocatore_sconfitto.getNome_giocatore());//stampo il giocatore sconfitto
			String giocatore_vincente = giocatore_B.getNome_giocatore();
			if(!giocatore_sconfitto.equals(giocatore_A)) {
				giocatore_vincente = giocatore_A.getNome_giocatore();
			}
			OutputStringhe.stampaVincitore(giocatore_vincente);//stampo il giocatore vincente
		} else {//altrimenti
			OutputStringhe.stampaPartitaPareggio();//stampo che la partita e' terminata con un pareggio
		}
		
        equilibrio_elementi.stampaEquilibrio();//stampo l'equilibrio della partita
	}
	
	/**
	 * il giocatore sceglie gli elementi da associare alle pietre
	 * @param giocatore
	 * @param numero_pietre
	 */	
	public  void evocationPhase(Giocatore giocatore) {
		Pietra pietra = null;
		int indice_elemento;
		Pietra[] pietre = new Pietra[num_pietre_tamagolem];//creao un array che conterrà le pietre da inserire nel caricatore del tamagolem
		
		OutputStringhe.faseEvocazioneOutput(giocatore.getNome_giocatore(), giocatore.getNumero_tamagolem_giocatore(), num_pietre_tamagolem);
		
		String elementi_menu[]=Elementi.getNomeElementi(elementi_partita);		
		MyMenu menu_elementi_pietra = new MyMenu(TITOLO_MENU, elementi_menu);
		menu_elementi_pietra.stampaMenu(OPZIONE_AGGIUNTIVA_MENU_ELEMENTI_PIETRA);//stampo il menu che mostra all'utente gli elementi delle pietre che puo' scegliere nella fase di evocazione 
		
		for(int i = 0; i< num_pietre_tamagolem && scorta.size() > 0; i++) {
			pietra = new Pietra();

			do {
				int scelta = menu_elementi_pietra.scegli(String.format(RICHIESTA_SCELTA_ELEMENTO, i+1));//richiedo all'utente di inserire il valore corrispondete all'elemento che vuole selezionare
				if(scelta==0) {
					stampaScorta();//se l'utente inserisce 0 allora stampo la scorta delle pietre
					menu_elementi_pietra.stampaMenu(OPZIONE_AGGIUNTIVA_MENU_ELEMENTI_PIETRA);					
				}else {
					indice_elemento = scelta -1;
					if(calcolaPietreRimanentiScorta(elementi_partita[indice_elemento])>0) {//verifico che vi siano pietre dell'elemento selezionato all'interno della scorta
						pietra.setElement(elementi_partita[indice_elemento]);//istanzio la pietra
						togliPietraScelta(pietra);//tolgo la pietra dalla scorta
						pietre[i] = pietra;
					}else {
						OutputStringhe.errorePietreNonDIsponibili();//nel caso in cui non ci siano più pietre disponibili stampo un errore
					}
				}
			}while (Objects.isNull(pietra.getElement()));
		}
		
		giocatore.evocazioneTamaGolem(pietre);//una volta scelti tutti gli elementi da assegnare evoco il tamagolem
		OutputStringhe.clearConsole();
		OutputStringhe.evocazioneCompletata(giocatore.getNome_giocatore());
	}	
	
	/**
	 * stampo il numero di pietre di ogni elemento che rimangono all'interno della scorta
	 */
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
	
	/**
	 * dato un elemento passato come argomento, determino quante pietre di quell'elemento rimangono all'interno della scorta
	 * @param elemento
	 * @return numero pietre rimanenti 
	 */
	public int calcolaPietreRimanentiScorta(Elementi elemento) {
		int num_pietre_rimanenti = 0;
		
		for(Pietra p : scorta) {
			if(elemento.equals(p.getElement())) {
				num_pietre_rimanenti ++;
			}
		}
		
		return num_pietre_rimanenti;
	}
	
	/**
	 * rimuove la pietra passata come argomento dalla scorta comune
	 * @param pietra
	 */
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
	 * @return giocatore alla quale viene sconfitto un tamagolem
	 */
	public Giocatore battlePhase(TamaGolem tg1, TamaGolem tg2) {
		Giocatore giocatore_tg_abbattuto;
		int danno;
		Pietra pietra_tg1, pietra_tg2;
		String elemento_vittoria;//nome elemento della pietra del tamagolem che infligge danni
		String elemento_sconfitta;//nome elemeneto della pietra del tamagolem che subisce danni
		String giocatore_sconfitta;//tamagolem che subisce danno
		int turno=0;
		int numero_danni_nulli = 0;
		do {
			turno++;
			//primo e secondo tamagolem scagliano la pietra
			pietra_tg1 = tg1.trowPietra();
			pietra_tg2 = tg2.trowPietra();
			if(pietra_tg1.isPietraDominante(pietra_tg2, equilibrio_elementi)) {//se la pietra dominante e' quella scagliata dal primo tamagolem 
				danno = pietra_tg1.getDannoPietra(pietra_tg2, equilibrio_elementi);//determino danno
				tg2.infliggiDanno(danno);//secondo tamagolem subisce danno
				elemento_vittoria = pietra_tg1.getElement().name();
				elemento_sconfitta = pietra_tg2.getElement().name();
				giocatore_sconfitta = giocatore_B.getNome_giocatore();
			}else {//se la pietra dominante e' quella scagliata dal secondo tamagolem
				danno= pietra_tg2.getDannoPietra(pietra_tg1, equilibrio_elementi);//determino danno
				tg1.infliggiDanno(danno);//primo tamagolem subisce danno
				elemento_vittoria = pietra_tg2.getElement().name();
				elemento_sconfitta = pietra_tg1.getElement().name();
				giocatore_sconfitta = giocatore_A.getNome_giocatore();
			}
			
			if(danno == 0) {
				numero_danni_nulli++;
			}
			
			giocatore_tg_abbattuto = verificaSconfitto(tg1, tg2);//determino se uno dei due tamagolem non ha più hp disponibili
			
			if(!Objects.isNull(giocatore_tg_abbattuto)) {//se il giocatore del tamagolem abbattuto punta ad un'istanza di giocatore 
				OutputStringhe.stampaTamagolemSconfitto(turno, giocatore_tg_abbattuto.getNome_giocatore());//stampo sconfitta del tamagolem
			}else {
				OutputStringhe.stampaDanno(turno, elemento_vittoria, elemento_sconfitta, danno, giocatore_sconfitta);//altrimenti stampo il danno inflitto
			}
		}while(Objects.isNull(giocatore_tg_abbattuto) && !(numero_danni_nulli == num_pietre_tamagolem && numero_danni_nulli == turno) );
		
		
		return giocatore_tg_abbattuto;
	}

	/**
	 * dati due tamagolem passati come argomento verifica quale dei due non ha più hp disponibili
	 * @param tg1
	 * @param tg2
	 * @return il giocatore al quale il tamagolem viene sconfitto, null se entrambi i tamagolem hanno hp > 0 
	 */
	public Giocatore verificaSconfitto (TamaGolem tg1, TamaGolem tg2) {
		if(tg1.isSconfitto()) {
			return appartieneAgiocatore(tg1);
		}else if(tg2.isSconfitto()) {
			return appartieneAgiocatore(tg2);
		} else {
			return null;
		}		
	}
	
	/**
	 * dato un tamagolem passato come argoment ritorna il giocatore alla quale appartiene
	 * @param tg
	 * @return
	 */
	public Giocatore appartieneAgiocatore(TamaGolem tg) {
		if(tg.equals(giocatore_A.getTamaGolem())) 
			return giocatore_A;
		else
			return giocatore_B;
	}

}
