package tamagolem;

import java.util.ArrayList;


public class Equilibrio {
	private static final int DANNO_MAX = 100;
	private static final int DANNO_MINIMO = 1;
	private int matrice_grafo[][];
	private Elementi nodi[];
	
	/**
	 * costruttore dell'Equilibrio settando gli elementi in gioco
	 * @param numero_elementi_partita
	 */
	public Equilibrio(Elementi ... elementi_partita) {
		nodi= elementi_partita;
		matrice_grafo= new int[nodi.length][nodi.length];
	}

	/**
	 * generazione della matrice di adiacenza corrispondente al grafo dell'Eqilibrio del mondo
	 */
	public void generaEquilibrio() {
		int somma;
		int danno;

		for(int i=0; i<nodi.length; i++) {
			somma=0;
			for(int j=0; j<nodi.length; j++) {
				somma= somma + matrice_grafo[i][j];
					
				if(i!=j && matrice_grafo[i][j]==0){					
					if(j==nodi.length-1) {
						danno=-somma;//in corrispondenza dell'ultima colonna, il valore di danno assegnato coincide con la somma cambiata di segno dei danni contenuti negli indici precedenti nella stessa riga
					}else if(somma >= DANNO_MINIMO && somma <= DANNO_MAX) {//se la somma dei danni e' compresa tra il danno minimo e massimo (estremi compresi)
						danno=-NumeriCasuali.estraiIntero(DANNO_MINIMO, DANNO_MAX/2);//il danno generato avra' valori compresi tra il danno minimo e la meta' del valore del danno massimo (cambiati di segno)
					} else if (somma > DANNO_MAX) {//se la somma dei danni generati e' maggiore del danno massimo viene generato un numero negativo per compensare 
						danno = - NumeriCasuali.estraiIntero(somma/2, DANNO_MAX);
					}else if (somma <= 0 && somma > -DANNO_MAX){//se la somma dei danni generati è compresa tra 0 e il valore di danno max (cambiato di segno)
						danno = NumeriCasuali.estraiIntero(DANNO_MINIMO, DANNO_MAX/2);//il danno generato avra' valori compresi tra il danno minimo e la meta' del valore del danno massimo
					} else {							
						danno = NumeriCasuali.estraiIntero(somma/2, DANNO_MAX); //se la somma dei danni generati e' minore del danno massimo cambiato di segno, allora viene generato un numero positivo per compensare 
					}
					
					//costruisco la matrice simmetrica
					matrice_grafo[i][j]=danno;
					matrice_grafo[j][i]=-danno;
					somma=somma+danno;
				}
			}
		}	
	}

	/**
	 * Dati due elementi passati come parametro, viene verificato quale dei due è l'elemento che domina sull'altro in 
	 * base all'equilibrio della partita
	 * @param elemento_tg1
	 * @param elemento_tg2
	 * @return elemento dominante
	 */
	public Elementi verificaElementoDominante (Elementi elemento_tg1, Elementi elemento_tg2) {
		int indice_elemento_tg1 = getIndiceElementoEquilibrio(elemento_tg1);
		int indice_elemento_tg2 = getIndiceElementoEquilibrio(elemento_tg2);
		
		if(matrice_grafo[indice_elemento_tg1][indice_elemento_tg2] > 0) {//se il danno estratto dalla matrice è positivo allora l'elemento dominante è il primo elemento passato come argomento
			return elemento_tg1;
		} else {//altrimenti è il secondo elemento passato come argomento
			
			return elemento_tg2;
		}
	}
	
	/**
	 * Dati due elementi passati come argomento, viene definito il danno che viene inflitto o subito dal primo elemento rispetto il secondo
	 * @param elemento_dominante
	 * @param elemento_recessivo
	 * @return danno inflitto/subito dal primo elemento rispetto il secondo
	 */
	public int getDanno (Elementi elemento_dominante, Elementi elemento_recessivo) {
		int indice_elemento_dominante = getIndiceElementoEquilibrio(elemento_dominante);
		int indice_elemento_recessivo = getIndiceElementoEquilibrio(elemento_recessivo);
	
		int danno = matrice_grafo[indice_elemento_dominante][indice_elemento_recessivo];
		return danno;
	}
	
	/**
	 * determina la posizione dell'elemento passato come argomento all'interno dell'array contenente gli elementi della partita
	 * @param elemento
	 * @return indice elemento se presente tra gli elementi della partta, -1 se non trovato
	 */
	public int getIndiceElementoEquilibrio(Elementi elemento) {
		int i;
		for(i=0; i<nodi.length; i++) {
			if(elemento.equals(nodi[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @return la matrice che rappresenta l'equilibrio della partita
	 */
	public int[][] getMatrice_grafo() {
		return matrice_grafo;
	}

	/**
	 * @return gli elementi che interagiscono nell'equilibrio della partita
	 */
	public Elementi[] getNodi() {
		return nodi;
	}

	/**
	 * Stampa l'equilibrio della partita
	 */
	public void stampaEquilibrio() {
		String titolo="TABELLA EQUILIBRIO";
		String [] intestazione= {"SCONTRO", "VINCITORE", "DANNO ARRECATO"};
		
		OutputStringhe.stampaTitoloTabella(titolo);
		OutputStringhe.stampaIntestazioneTabella(intestazione);
		String riga_tabella[]= new String[intestazione.length];
		
		for(int i=1; i<nodi.length; i++ ) {
			for(int j=0; j<i; j++) {				
				if(matrice_grafo[i][j]>0) { //se il danno contenuto nella matrice è > 0 allora l'elemento della riga domina sull'elemento della colonna
					riga_tabella[0]=nodi[i].name()+" vs "+ nodi[j].name();
					riga_tabella[1]=nodi[i].name();
					riga_tabella[2]=""+matrice_grafo[i][j];
				}else {//altrimenti, l'elemento della colonna domina sull'elemento della riga
					riga_tabella[0]=nodi[i].name()+" vs "+ nodi[j].name();
					riga_tabella[1]=nodi[j].name();
					riga_tabella[2]=""+matrice_grafo[j][i];
				}
				OutputStringhe.stampaRigaTabella(riga_tabella);
			}
		}
	}
	
	
}
