package tamagolem;

public class Main {
	
	public static void main(String[] args) {
		Partita partita1;
		
		do {
			//utente schiaccia invio per iniziare la partita
			partita1=new Partita();
			
			if(partita1.setDifficolta()) {
				
				partita1.setElementi_partita();
				partita1.setEquilibrio();
				
				partita1.creaGiocatori();
				partita1.calcolaVariabili();
				partita1.definisciScorta();			
				
				partita1.avviaPartita();
				
			}
			
		}while(InputDati.yesOrNo("\n\n\nVuoi fare una nuova partita?"));
	}
}
