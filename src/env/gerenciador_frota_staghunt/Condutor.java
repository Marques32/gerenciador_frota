package gerenciador_frota_staghunt;

public class Condutor {
	private String nome;
	private boolean cooperou;
	private double litroExcedido;
	private Reputacao reputacao;
	private int pontuacao;
	private int pontuacaoAnterior;
	
	public Condutor(String nome) {
		this.nome = nome;
		this.reputacao = new Reputacao();
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public int getPontuacaoAnterior() {
		return pontuacaoAnterior;
	}

	public void setPontuacaoAnterior(int pontuacaoAnterior) {
		this.pontuacaoAnterior = pontuacaoAnterior;
	}

	public int getPontuacao() {
		return pontuacao;
	}
	
	public void setPontuacao(int pontuacao){
		this.pontuacao = pontuacao;
	}
	
	public void adicionarPontuacao(int pontos){
		this.pontuacao+=pontos;
	}
	
	public boolean isCooperou() {
		return cooperou;
	}

	public void setCooperou(boolean cooperou) {
		this.cooperou = cooperou;
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

	public void adicionarLitroExcedido(double litro) {
		this.litroExcedido += litro;
	}

}
