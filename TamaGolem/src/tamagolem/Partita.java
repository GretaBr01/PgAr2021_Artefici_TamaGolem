package tamagolem;
import java.util.ArrayList;
import it.unibs.fp.mylib.*;

public class Partita {

	public static void main(String[] args) {
		ArrayList<Pietra> pietre = new ArrayList<>();
		
		TamaGolem tg = new TamaGolem();
		for(int i = 0; i < 3; i++) {
			Pietra pietra = new Pietra();
			pietra.setElement(Elementi.FUOCO);
			pietre.add(pietra);
		}
		
		tg.setTamaGolem(pietre);
		
		for(int i=0; i < 3; i++) {
			System.out.println("E' stata lanciata una pietra di tipo "+ tg.trowPietra().getElement().toString());
		}
		

	}

}
