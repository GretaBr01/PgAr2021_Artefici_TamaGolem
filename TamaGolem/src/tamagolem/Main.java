package tamagolem;

public class Main {
	
	private static final String NUOVA_PARTITA = "\n\n\nVuoi fare una nuova partita?";

	public static void main(String[] args) {
		Partita partita1;
		
		do {
			//utente schiaccia invio per iniziare la partita
			partita1=new Partita();//creazione di una partita
			
			if(partita1.setDifficolta()) {//definizione del livello difficolta' della partita
				
				partita1.setElementi_partita();//definizione degli elementi che andranno a definire l'equilibrio della partita
				partita1.setEquilibrio();//definizione dell'equilibrio della partita
				
				partita1.creaGiocatori();//creazione dei due giocatori
				partita1.calcolaVariabili();//definizione dei parametri della partita a seconda della difficolta' impostata
				partita1.definisciScorta();//creazione della scorta di pietre comune		
				
				partita1.avviaPartita();//avvio della partita
				
			}
			
		}while(InputDati.yesOrNo(NUOVA_PARTITA));
	}
}
