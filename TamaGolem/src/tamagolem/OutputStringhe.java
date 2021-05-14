package tamagolem;

import java.util.concurrent.TimeUnit;

//classe per l'output delle stringhe delle interazioni avvenute in partita

public class OutputStringhe { 	

	//Possibili nomi: Questions, Phrases,
	
	private static final int LARGHEZZA_TABELLA = 150;
	private static final String CORNICE_RIGA = " ";
	private final static String CORNICE_TITOLO = "-";
	
	private final static String SPAZIO = " ";
	private final static String ACAPO = "\n";
	

	/**
	 * stampa le regole del gioco
	 */
	public final static void stampaRegoleGioco() {
		String regole[]= {	"La difficolta' della partita sara' scelta dai giocatori.",
							"In base alla difficolta' scelta cambiano i parametri di gioco come il numero di TamaGolem per giocatore e il numero di pietre che compone un set",
							"I TamaGolem all'inizio della loro esistenza avranno 100 punti vita!",
							"L'equilibrio degli elementi verra' generato casualmente all'inizio della partita e i giocatori potranno visualizzarlo solo alla fine di essa.",
							"Lo scontro di due pietre con stesso elemento non comporterà alcun danno ai TamaGolem in gioco.",
							"Atti di scorrettezza come copiare il set del proprio avversario verranno puniti con l'eliminazione del proprio TamaGolem."
									+ "\n\t\tSe invece già nella prima battaglia dela partita i giocatori scelgono lo stesso set entrambi i TamaGolem verranno eliminati"};
		System.out.println();
		System.out.println();
		System.out.println(centrata("BENVENUTI NELLA TAMARENA", LARGHEZZA_TABELLA));
		System.out.println(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO));
		System.out.println();
		timeOut(4);
		System.out.println("Prima di iniziare il gruppo Artefici si inchina dinanzi ai capi del progetto e al flauto magico di Robb!");
		System.out.println();
		System.out.println("\tECCO LE REGOLE DEL GIOCO:");	
		System.out.println();
		timeOut(3);
		for(int i=0; i<regole.length; i++) {
			System.out.format("%d\t%s", i+1, regole[i]);
			System.out.println();
			timeOut(2);
		}
		System.out.println();
		pausaSistema();
		System.out.println();
	}
	
	/**
	 * genera 30 spaziature sulla console
	 */
	public final static void clearConsole(){
	    for (int i = 0; i<30; i++){
	        System.out.print('\n');
	     }
	}	

	/**
	 * la stringa viene centrata all'interno della console
	 * @param s stringa da stampare
	 * @param larghezza console 
	 * @return stringa centrata
	 */
	public static String centrata (String s, int larghezza){
		StringBuffer res = new StringBuffer(larghezza);
		if (larghezza <= s.length()) {
			res.append(s.substring(larghezza));
		} else {
			int spaziPrima = (larghezza - s.length())/2;
			int spaziDopo = larghezza - spaziPrima - s.length();
			for (int i=1; i<=spaziPrima; i++)
				res.append(SPAZIO);
			
			res.append(s);
		
			for (int i=1; i<=spaziDopo; i++)
				res.append(SPAZIO);
		}
	 	return res.toString();
	}
	
	/**
	 * creazione della cornice la cui larghezza viene definita dall'intero passato come parametro
	 * @param larghezza della cornice
	 * @param cornice
	 * @return cornice
	 */
	public static String creaCornice(int larghezza, String cornice) {
		StringBuffer res = new StringBuffer();
		for(int i=0; i<larghezza; i++) {
			res.append(cornice);
		}
		return res.toString();
	}
	
	/**
	 * stampa il titolo della tabella passato come argomento
	 * @param titolo
	 */
	public static void stampaTitoloTabella(String titolo) {
		 StringBuffer res = new StringBuffer();
		 
		 res.append(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO)+ACAPO);
		 res.append(centrata(titolo, LARGHEZZA_TABELLA)+ACAPO);
		 res.append(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO));
		 
		 System.out.println(res.toString());
	}
	
	/**
	 * stampa intestazione della tabella
	 * @param strings array che contiene i nomi delle colonne della tabella
	 */
	public static void stampaIntestazioneTabella(String ... strings) {
		System.out.println(creaRigaTabella(LARGHEZZA_TABELLA, strings));
		System.out.println(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO));
	}
	
	/**
	 * stampa una riga della tabella con il contenuto dell'array passato come argomento
	 * @param strings array che contiene le informazione da stampare nella riga della tabella
	 */
	public static void stampaRigaTabella(String ...strings) {
		System.out.println(creaRigaTabella(LARGHEZZA_TABELLA, strings));
		System.out.println(creaCornice(LARGHEZZA_TABELLA, CORNICE_RIGA));
	}
	
	/**
	 * delimitazione della riga della tabella la cui larghezza è definita dall'intero passato come argomento
	 * @param larghezza
	 * @param strings
	 * @return riga della tabella
	 */
	public static String creaRigaTabella(int larghezza, String ...strings) {
		int num_colonne = strings.length;
		int spazio_colonna = larghezza/num_colonne;
		String riga="";
		
		for (int i=0; i<strings.length; i++) {
			riga=riga + "|"+ centrata(strings[i], spazio_colonna);
		}
		return riga;
	}
	
	/**
	 * Stampa indicazioni durante l'evocazione del tamagolem
	 * @param nome_giocatore nome del giocatore
	 * @param tamagolem_rimanenti tamagolem rimanenti del giocatore
	 * @param numero_pietre numero di pietre consentite per l'evocazione del tamagolem 
	 */ 
	public static void faseEvocazioneOutput(String nome_giocatore, int tamagolem_rimanenti, int numero_pietre) {
		System.out.format("\n\nEVOCAZIONE TAMAGOLEM del giocatore %s \n\n", nome_giocatore);
		System.out.format("\tTi rimangono %d TAMAGOLEM \n\n", tamagolem_rimanenti);
		
		System.out.format("SCEGLI %d PIETTRE per evocare il tamagolem\n", numero_pietre);		
	}
	
	/**
	 * Stampa errore se i due giocatori inseriscono lo stesso nome
	 */
	public static void erroreNome() {
		System.out.println("ERRORE: nome inserito gia' utilizzato, inserire un nuovo nome...");
	}
	
	/**
	 * stampa errore se non vi sono più pietre di un determinato elemento disponibili all'interno della scorta pietre
	 */
	public static void errorePietreNonDIsponibili() {
		System.out.println("Attenzione, non ci sono più pietre disponibili dell'elemento selezionato");
	}
	
	/**
	 * stampa evocazione avvenuta con successo
	 * @param nome_giocatore
	 */
	public static void evocazioneCompletata(String nome_giocatore) {
		System.out.format("evocazione %s completata\n\n", nome_giocatore);
	}
	
	/**
	 * timer che genera un delay la cui durata è determinata dell'interno passato come argomento
	 * @param sec intero che indica la durata in secondi del delay che si vuole generare
	 */
	public static void timeOut(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * stampa il danno causato da un tamagolem durante uno scontro
	 * @param turno numero del turno in atto 
	 * @param elemento_vittoria elemento che infligge danni 
	 * @param elemento_sconfitta elemento che subisce danni
	 * @param danno danno inflitto
	 * @param giocatore_sconfitto giocatore a cui appartiene il tamagolem che subisce danni 
	 */
	public static void stampaDanno (int turno,String elemento_vittoria, String elemento_sconfitta, int danno, String giocatore_sconfitto){
		timeOut(2);
		System.out.print("TURNO "+turno+": ");
		if(danno==0) {
			System.out.println("i due Tamagolem hanno scagliato due pietre con stesso elemento non infliggendosi danni!");
		}else {
			System.out.format("%s vince su %s infliggendo %d danni al TamaGolem di %s", elemento_vittoria, elemento_sconfitta, danno, giocatore_sconfitto);
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * stampa la sconfitta di un tamagolem quando questu'ultimo termina i punti vita disponibili
	 * @param turno
	 * @param nome_giocatore
	 */
	public static void stampaTamagolemSconfitto(int turno, String nome_giocatore) {
		timeOut(2);
		System.out.print("TURNO "+turno+": ");
		System.out.format("Il TamaGolem di %s è stato sconfitto\n\n", nome_giocatore);
		timeOut(1);
		pausaSistema();
	}
	
	
	public static void pausaSistema() {
		InputDati.leggiStringaVuota("premi un tasto per continuare...");
	}
	
	/**
	 * stampa il nome del giocatore che perde la partita
	 * @param nome_giocatore nome del giocatore
	 */
	public static void stampaSconfitto(String nome_giocatore) {
		System.out.format("\n\n\til giocatore %s è stato sconfitto\n\n", nome_giocatore);
	}
	
	/**
	 * stampa il nome del giocatore che vince la partita
	 * @param nome_giocatore
	 */
	public static void stampaVincitore(String nome_giocatore) {
		System.out.format("\n\t Ha VINTO il giocatore %s\n\n", nome_giocatore);
		pausaSistema();
	}
	
	/**
	 * stampa indicazione dell'avvio della partita
	 */
	public static void inizioPartita() {
		String cornice=creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO);
		System.out.println(cornice);
		System.out.println(centrata("che la partita abbia inizio!!", LARGHEZZA_TABELLA));
		System.out.println(cornice);
	}
	
	/**
	 * stampa il numero della battaglia in atto
	 * @param num_battaglia
	 */
	public static void stampaNumeroBattaglia(int num_battaglia) {
		System.out.format("INIZIO BATTAGLIA NUM. %d\n\n", num_battaglia);
	}
	
	/**
	 * stampa indicazione battaglia terminata con un pareggio
	 */
	public static void stampaBattagliaPareggio () {
		timeOut(2);
		System.out.println("I due TamaGolem hanno la stessa sequenza di pietre, la battaglia termina con un PAREGGIO!!");
		timeOut(1);
		pausaSistema();
	}
	
	/**
	 * stampa partita terminata con un pareggio
	 */
	public static void stampaPartitaPareggio () {
		System.out.println();
		System.out.println("\tLa partita e' terminata con un PAREGGIO");
		System.out.println();
		pausaSistema();
	}
}
