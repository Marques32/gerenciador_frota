package gerenciador_frota;

public class Reputacao {
	private int valor;
	
	public Reputacao(){
		this.valor = 5;
	}

	public int getValor() {
		return valor;
	}
	
	public void adicionarValor(int valor){
		this.valor+=valor;
	}
}
