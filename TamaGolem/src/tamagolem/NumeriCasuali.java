package tamagolem;

import java.util.*;

//classe estrattore numeri casuali

public class NumeriCasuali {
	
	private static Random estrattore = new Random();	
	
	/**
	 * generazione casuale di un numero intero
	 * @param min numero minimo estraibile
	 * @param max numero massimo estraibile
	 * @return numero estratto
	 */
	public static int estraiIntero(int min, int max){
		 int range = max + 1 - min;
		 int casual = estrattore.nextInt(range);
		 return casual + min;			 
	}
}
