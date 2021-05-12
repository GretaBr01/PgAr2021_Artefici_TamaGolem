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
	
	/*public ArrayList<Integer> verificaDominante (Pietra pietra_tg1, Pietra pietra_tg2) {
		ArrayList<Integer> tgw_danno = new ArrayList<Integer>();
		int elemento_pietra_tg1 = pietra_tg1.getElement().valore - 1;
		int elemento_pietra_tg2 = pietra_tg2.getElement().valore -1 ;
		
		if(matrice_grafo[elemento_pietra_tg1][elemento_pietra_tg2] > 0) {
			tgw_danno.add(1);
			tgw_danno.add(matrice_grafo[elemento_pietra_tg1][elemento_pietra_tg2]);
		}
		else {
			tgw_danno.add(2);
			tgw_danno.add(matrice_grafo[elemento_pietra_tg2][elemento_pietra_tg1]);
		}
		
		return tgw_danno;
	}*/
	
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
	
	
	
}
