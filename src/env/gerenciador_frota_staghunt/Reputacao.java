package gerenciador_frota_staghunt;

public class Reputacao {
	private double reputacaoAnterior;
	private double nivelIrresponsabilidade;
	private double lembranca;
	private int valor;
	
	public Reputacao(){
		this.valor = 5;
	}
	
	public double getReputacaoAnterior() {
		return reputacaoAnterior;
	}

	public void setReputacaoAnterior(double reputacaoAnterior) {
		this.reputacaoAnterior = reputacaoAnterior;
	}

	public double getNivelIrresponsabilidade() {
		return nivelIrresponsabilidade;
	}

	public void setNivelIrresponsabilidade(double nivelIrresponsabilidade) {
		this.nivelIrresponsabilidade = nivelIrresponsabilidade;
	}

	public double getLembranca() {
		return lembranca;
	}

	public void setLembranca(double lembranca) {
		this.lembranca = lembranca;
	}

	public int getValor() {
		return valor;
	}
	
	public void adicionarValor(int valor){
		this.valor+=valor;
	}
}
