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
						danno=-somma;
					}else if(somma >= DANNO_MINIMO && somma <= DANNO_MAX) {
						danno=-NumeriCasuali.estraiIntero(DANNO_MINIMO, DANNO_MAX/2);
					} else if (somma > DANNO_MAX) {
						danno = - NumeriCasuali.estraiIntero(somma/2, DANNO_MAX);
					}else if (somma <= 0 && somma > -DANNO_MAX){
						danno = NumeriCasuali.estraiIntero(DANNO_MINIMO, DANNO_MAX/2);
					} else {							
						danno = NumeriCasuali.estraiIntero(somma/2, DANNO_MAX);
					}
					matrice_grafo[i][j]=danno;
					matrice_grafo[j][i]=-danno;
					somma=somma+danno;
				}
			}
		}	
	}

	
	public Elementi verificaElementoDominante (Elementi elemento_tg1, Elementi elemento_tg2) {
		int indice_elemento_tg1 = getIndiceElementoEquilibrio(elemento_tg1);
		int indice_elemento_tg2 = getIndiceElementoEquilibrio(elemento_tg2);
		
		if(matrice_grafo[indice_elemento_tg1][indice_elemento_tg2] > 0) {
			return elemento_tg1;
		} else {
			return elemento_tg2;
		}
	}
	
	public int getDanno (Elementi elemento_dominante, Elementi elemento_recessivo) {
		int indice_elemento_dominante = getIndiceElementoEquilibrio(elemento_dominante);
		int indice_elemento_recessivo = getIndiceElementoEquilibrio(elemento_recessivo);
		
		int danno = matrice_grafo[indice_elemento_dominante][indice_elemento_recessivo];
		return danno;
	}
	
	public int getIndiceElementoEquilibrio(Elementi elemento) {
		int i;
		for(i=0; i<nodi.length; i++) {
			if(elemento.equals(nodi[i])) {
				return i;
			}
		}
		return -1;
	}
	public int[][] getMatrice_grafo() {
		return matrice_grafo;
	}

	public Elementi[] getNodi() {
		return nodi;
	}

	public void stampaEquilibrio() {
		String titolo="TABELLA EQUILIBRIO";
		String [] intestazione= {"SCONTRO", "VINCITORE", "DANNO ARRECATO"};
		
		OutputStringhe.stampaTitoloTabella(titolo);
		OutputStringhe.stampaIntestazioneTabella(intestazione);
		String riga_tabella[]= new String[intestazione.length];
		
		for(int i=1; i<nodi.length; i++ ) {
			for(int j=0; j<i; j++) {				
				if(matrice_grafo[i][j]>0) {
					riga_tabella[0]=nodi[i].name()+" vs "+ nodi[j].name();
					riga_tabella[1]=nodi[i].name();
					riga_tabella[2]=""+matrice_grafo[i][j];
				}else {
					riga_tabella[0]=nodi[i].name()+" vs "+ nodi[j].name();
					riga_tabella[1]=nodi[j].name();
					riga_tabella[2]=""+matrice_grafo[j][i];
				}
				OutputStringhe.stampaRigaTabella(riga_tabella);
			}
		}
	}
	
	
}
