package tamagolem;

import java.util.concurrent.TimeUnit;


public class OutputStringhe { 	

	//Possibili nomi: Questions, Phrases,
	
	private static final int LARGHEZZA_TABELLA = 150;
	private static final String CORNICE_RIGA = " ";
	private final static String CORNICE_TITOLO = "-";
	
	private final static String SPAZIO = " ";
	private final static String ACAPO = "\n";
	

	public final static void stampaRegoleGioco() {
		System.out.println(centrata("BENVENUTI NELLA TAMARENA", LARGHEZZA_TABELLA));
		System.out.println(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO));
		System.out.println();
		System.out.println("Prima di iniziare il gruppo Artefici si inchina dinanzi ai capi del progetto e al flauto magico di Robb!");
		System.out.println();
		System.out.println("\tECCO LE REGOLE DEL GIOCO:");
		
		System.out.println("1.\tSe i due TamaGolem schierati hanno lo stesso set di pietre la battaglia finirà con un pareggio e verranno eliminati, i due giocatori schierano nuovi TamaGolem");
	}
	
	
	public final static void clearConsole(){
	    for (int i = 0; i<30; i++){
	        System.out.print('\n');
	     }
	}	

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
	
	public static String creaCornice(int larghezza, String cornice) {
		StringBuffer res = new StringBuffer();
		for(int i=0; i<larghezza; i++) {
			res.append(cornice);
		}
		return res.toString();
	}
	
	public static void stampaTitoloTabella(String titolo) {
		 StringBuffer res = new StringBuffer();
		 
		 res.append(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO)+ACAPO);
		 res.append(centrata(titolo, LARGHEZZA_TABELLA)+ACAPO);
		 res.append(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO));
		 
		 System.out.println(res.toString());
	}
	
	public static void stampaIntestazioneTabella(String ... strings) {
		System.out.println(creaRigaTabella(LARGHEZZA_TABELLA, strings));
		System.out.println(creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO));
	}
	
	public static void stampaRigaTabella(String ...strings) {
		System.out.println(creaRigaTabella(LARGHEZZA_TABELLA, strings));
		System.out.println(creaCornice(LARGHEZZA_TABELLA, CORNICE_RIGA));
	}
	
	public static String creaRigaTabella(int larghezza, String ...strings) {
		int num_colonne = strings.length;
		int spazio_colonna = larghezza/num_colonne;
		String riga="";
		
		for (int i=0; i<strings.length; i++) {
			riga=riga + "|"+ centrata(strings[i], spazio_colonna);
		}
		return riga;
	}
	
	public static void faseEvocazioneOutput(String nome_giocatore, int tamagolem_rimanenti, int numero_pietre) {
		System.out.format("\n\nEVOCAZIONE TAMAGOLEM del giocatore %s \n\n", nome_giocatore);
		System.out.format("\tTi rimangono %d TAMAGOLEM \n\n", tamagolem_rimanenti);
		
		System.out.format("SCEGLI %d PIETTRE per evocare il tamagolem\n", numero_pietre);		
	}
	
	public static void erroreNome() {
		System.out.println("ERRORE: nome inserito gia' utilizzato, inserire un nuovo nome...");
	}
	
	public static void errorePietreNonDIsponibili() {
		System.out.println("Attenzione, non ci sono più pietre disponibili dell'elemento selezionato");
	}
	
	public static void evocazioneCompletata(String nome_giocatore) {
		System.out.format("evocazione %s completata\n\n", nome_giocatore);
	}
	
	public static void timeOut(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public static void stampaTamagolemSconfitto(int turno, String nome_giocatore) {
		timeOut(2);
		System.out.print("TURNO "+turno+": ");
		System.out.format("Il TamaGolem di %s è stato sconfitto\n\n", nome_giocatore);
		timeOut(1);
		pausaSistema();
	}
	
	public static void pausaSistema() {
		InputDati.leggiStringa("premi un tasto per continuare...");
	}
	
	public static void stampaSconfitto(String nome_giocatore) {
		System.out.format("\n\n\til giocatore %s è stato sconfitto\n\n", nome_giocatore);
	}
	
	public static void stampaVincitore(String nome_giocatore) {
		System.out.format("\n\t Ha VINTO il giocatore %s\n\n", nome_giocatore);
		pausaSistema();
	}
	
	public static void inizioPartita() {
		String cornice=creaCornice(LARGHEZZA_TABELLA, CORNICE_TITOLO);
		System.out.println(cornice);
		System.out.println(centrata("che la partita abbia inizio!!", LARGHEZZA_TABELLA));
		System.out.println(cornice);
	}
	
	public static void stampaNumeroBattaglia(int num_battaglia) {
		System.out.format("INIZIO BATTAGLIA NUM. %d\n\n", num_battaglia);
	}
	
	public static void stampaBattagliaPareggio () {
		timeOut(2);
		System.out.println("I due TamaGolem hanno la stessa sequenza di pietre, la battaglia termina con un PAREGGIO!!");
		timeOut(1);
		pausaSistema();
	}
	
	public static void stampaPartitaPareggio () {
		System.out.println();
		System.out.println("\tLa partita e' terminata con un PAREGGIO");
		System.out.println();
		pausaSistema();
	}
}
