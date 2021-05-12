package tamagolem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public enum Elementi {
	FUOCO (1),
	ACQUA (2),
	MERDA (3),
	ALESSANDROCONTI (4),
	CLAVE (5),
	CRISTIANESIMO(6),
	ARIA(7),
	TERRA(8),
	LEGNO(9),
	FULMINI(10);

	private int valore;
	
	Elementi ( int val ){
		this.valore = val;
	}
	
	/**
	 * @return	valore corrispondente all' elemento Enum Elementi
	 */
	public int getValore() {
		return valore;
	}
	
	/**
	 * serve per trovare il nome dell'elemento Enum Elementi che occupa l'indice dato come argomento
	 * @param id	indice dell'elemento Enum Elementi
	 * @return	 elemento Enum Elementi presente all'indice dato
	 */
	public static Elementi getById(int id) {
		return Elementi.values()[id];
	}
	
	/**
	 * verifica corrispondenza del carattere all'interno della classe Enum Elementi
	 * @param valore
	 * @return true se il carattere passato come argomento � stato trovato nella classe Enum Elementi
	 */
	public static boolean isPresente(int valore) {
		Elementi nodes[] = values();
		
		for(int i=0; i<nodes.length; i++) {
			if(nodes[i].getValore() == (valore)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ricerca dell'elemento Enum Elementi il cui valore corrisponde a quello passato come argomento
	 * @param valore
	 * @return oggetto Enum Elementi
	 */
	public static Elementi getElementoDaValore(int valore) {
		Elementi elemento = null;
		Elementi nodes[] = values();
	
		for(int i=0; i<nodes.length; i++) {
			if (nodes[i].getValore() == (valore)) {
				return getById(i);
			}
		}
		return elemento;
	}
	
	/**
	 * @return numero elementi presenti nella classe Enum Elementi
	 */
	public static int getNumElementi() {
		return Elementi.values().length;
	}
	
	/**
	 * @return nomi degli elementi presenti nella classe Enum Elementi
	 */
	public static String[] getNomeElementi(Elementi ... elementi_partita) {
	String[] nome_elementi = new String[elementi_partita.length];
		for(int i = 0; i <  elementi_partita.length; i++) {
			nome_elementi[i] = elementi_partita[i].name();
		}
		return nome_elementi;
	}
	
	/**
	 * @return vettore contenente gli elementi della classe Enum Elementi
	 */
	public static Elementi[] getElementi(int numero_elementi_partita) {
		Elementi nodes[]= new Elementi[numero_elementi_partita];
		for(int i=0; i<numero_elementi_partita; i++) {
			nodes[i]=getById(i);
		}
		return nodes;
	}
	
	public static Elementi[] estraiElementi(int numero_elementi_partita) {
		//Set<Elementi> insieme= new HashSet<Elementi>();
		Elementi nodes[]= new Elementi[numero_elementi_partita];
		int valore_elemento;
		boolean trovato;
		int i=0;
		while(i<numero_elementi_partita){
			trovato=false;
			valore_elemento=NumeriCasuali.estraiIntero(1, numero_elementi_partita);
			for(int j=0; j<i; j++) {
				if(nodes[j].getValore()==valore_elemento) {
					trovato=true;
				}
			}
			if(!trovato) {
				nodes[i]=getElementoDaValore(valore_elemento);
				i++;
			}
		}
		return nodes;
	}
	
}
