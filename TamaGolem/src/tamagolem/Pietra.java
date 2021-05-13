package tamagolem;

public class Pietra {
	private Elementi element;
	
	/**
	 * 
	 * @return Elemento della pietra
	 */
	public Elementi getElement() {
		return element;
	}

	/**
	 * settaggio elemento della pietra
	 * @param element 
	 */
	public void setElement(Elementi element) {
		this.element = element;
	}
	/**
	 * stabilire se la pietra è dominante o no
	 * @param pietra2 pietra con cui confrontare
	 * @param eq equilibrio della partita
	 * @return true se pietra è dominante, false se pietra2 è dominante
	 */
	public boolean isPietraDominante(Pietra pietra2, Equilibrio eq) {
		Elementi elemento_dom = eq.verificaElementoDominante(pietra2.element, this.element);
		if(this.element.equals(elemento_dom)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param pietra2	pietra avversaria
	 * @param eq  equilibrio della partita
	 * @return numero intero corrispondente al danno
	 */
	public int getDannoPietra(Pietra pietra2, Equilibrio eq) {
		return eq.getDanno(element, pietra2.element);
	}
	
	
	

}
