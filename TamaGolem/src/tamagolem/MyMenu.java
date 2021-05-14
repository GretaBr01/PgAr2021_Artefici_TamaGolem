package tamagolem;

import java.util.ArrayList;



public class MyMenu {
  final private static String CORNICE = "--------------------------------";

  private String titolo;
  private String [] voci;


  public MyMenu (String titolo, String [] voci){
		this.titolo = titolo;
		this.voci = voci;
  }
  
  /**
   * @return scelta inserita dall'utente
   */
  public int scegli (String richiesta_inserimento) {
	return InputDati.leggiIntero(richiesta_inserimento, 0, voci.length);	 
  }
		
  /**
   * stampa un menu testuale generico a piu' voci. Si suppone che la voce per uscire sia sempre associata alla scelta 0 
   * e sia presentata in fondo al menu
   */
  public void stampaMenu (String voce_aggiuntiva){
		System.out.println(CORNICE);
		System.out.println(titolo);
		System.out.println(CORNICE);
	    for (int i=0; i<voci.length; i++){
		  System.out.println( (i+1) + "\t" + voci[i]);
		}
	    
	    if(!voce_aggiuntiva.isEmpty()) {
		    System.out.println();
			System.out.println("0\t"+voce_aggiuntiva);
		    System.out.println();
	    }

  }
		
}

