package tamagolem;

public class Pietra {
	Elementi element;
	
	public Elementi getElement() {
		return element;
	}

	public void setElement(Elementi element) {
		this.element = element;
	}
	
	public boolean isPietraDominante(Pietra pietra2, Equilibrio eq) {
		Elementi elemento_dom = eq.verificaElementoDominante(pietra2.element, this.element);
		if(this.element.equals(elemento_dom)) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getDannoPietra(Pietra pietra2, Equilibrio eq) {
		return eq.getDanno(element, pietra2.element);
	}
	
	
	

}
