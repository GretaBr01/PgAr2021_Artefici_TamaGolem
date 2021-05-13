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
				somma= somma+ matrice_grafo[i][j];
				
				if(i!=j && matrice_grafo[i][j]==0){					
					if(j==nodi.length-1) {
						danno=-somma;
					}else if(somma>0) {
						danno=-NumeriCasuali.estraiIntero(DANNO_MINIMO, DANNO_MAX);
					}else {
						danno=NumeriCasuali.estraiIntero(DANNO_MINIMO, DANNO_MAX);
					}
					matrice_grafo[i][j]=danno;
					matrice_grafo[j][i]=-danno;
					somma=somma+danno;
				}
			}
		}		
	}

	
	public Elementi verificaElementoDominante (Elementi elemento_tg1, Elementi elemento_tg2) {
		int indice_elemento_tg1 = elemento_tg1.ordinal();
		int indice_elemento_tg2 = elemento_tg2.ordinal() ;
		if(matrice_grafo[indice_elemento_tg1][indice_elemento_tg2] > 0) {
			return elemento_tg1;
		} else {
			return elemento_tg2;
		}
	}
	
	public int getDanno (Elementi elemento_dominante, Elementi elemento_recessivo) {
		int indice_elemento_dominante = elemento_dominante.ordinal();
		int indice_elemento_recessivo = elemento_recessivo.ordinal();
		int danno = matrice_grafo[indice_elemento_dominante][indice_elemento_recessivo];
		return danno;
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
