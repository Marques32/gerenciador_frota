package gerenciador_frota_staghunt;

public class Reputacao {
	private double reputacaoAnterior;
	private double nivelIrresponsabilidade;
	private double lembranca;
	private double valor;

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

	public double getValor() {
		return valor;
	}
	
	public void atualizaValor(){
		this.valor = (this.reputacaoAnterior*this.lembranca+this.nivelIrresponsabilidade*(2-this.lembranca))/2;
	}
}
