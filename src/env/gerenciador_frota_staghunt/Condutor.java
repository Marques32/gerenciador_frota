package gerenciador_frota_staghunt;

public class Condutor {
	private double litroExcedido;
	private Reputacao reputacao;

	public Condutor() {
		this.reputacao = new Reputacao();
	}

	public double getLitroExcedido() {
		return litroExcedido;
	}

	public Reputacao getReputacao() {
		return reputacao;
	}

	public void setReputacao(Reputacao reputacao) {
		this.reputacao = reputacao;
	}
	
	public void adicionarLitroExcedido(double litro){
		this.litroExcedido+=litro;
	}

}
