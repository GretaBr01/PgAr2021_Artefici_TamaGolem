package tamagolem;

import java.util.ArrayList;

public enum Elementi {
	FUOCO (1),
	ACQUA (2),
	MERDA (3),
	ALESSANDROCONTI (4),
	CLAVE (5),
	CRISTIANESIMO(6);

	int valore;
	
	Elementi ( int val ){
		this.valore = val;
	}
	
	public int getValore() {
		return valore;
	}
	
	public static Elementi getById(int id) {
		return Elementi.values()[id];
	}
	
	public static boolean isPresente(int valore) {
		Elementi nodes[] = values();
		
		for(int i=0; i<nodes.length; i++) {
			if(nodes[i].getValore() == (valore)) {
				return true;
			}
		}
		return false;
	}
	
	public static Elementi getNomeDaValore(int valore) {
		Elementi elemento = null;
		Elementi nodes[] = values();
	
		for(int i=0; i<nodes.length; i++) {
			if (nodes[i].getValore() == (valore)) {
				return getById(i);
			}
		}
		return elemento;
	}
	
	public static int getNumElementi() {
		return Elementi.values().length;
	}
	
	public static String[] getNomeElementi() {
	String[] nome_elementi = new String[getNumElementi()];
		for(int i = 0; i <  getNumElementi(); i++) {
			nome_elementi[i] = getNomeDaValore(i+1).name();
		}
		return nome_elementi;
	}
	
	
}
