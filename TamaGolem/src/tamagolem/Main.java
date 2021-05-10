package tamagolem;

public class Main {
	
	public static void main(String[] args) {
		Partita partita1;
		do {
			//utente schiaccia invio per iniziare la partita
			partita1=new Partita();
			
			if(partita1.setDifficolta()) {
				
				partita1.setEquilibrio();
				partita1.creaGiocatori();
				
				partita1.avviaPartita();
				
			}
			
		//scelta difficolta partita
		//avviaPartita(difficolta)
		}while(InputDati.yesOrNo("vuoi fare una nuova partita?"));
	}
}
