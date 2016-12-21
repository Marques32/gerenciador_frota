package gerenciador_frota;

public class Condutor {
	private String nomeMonitor;
	private boolean cooperou;
	private double litroExcedido;
	private Reputacao reputacao;
	private int coopIndex;
	private int ct;
	
	public Condutor(String nome) {
		this.coopIndex = 0;
		this.nomeMonitor = nome;
		this.reputacao = new Reputacao();
	}
	
	public int getCt() {
		return ct;
	}

	public void setCt(int ct) {
		this.ct = ct;
	}

	public void resetaCoopIndex(){
		this.coopIndex = 0;
	}
	
	public int getCoopIndex() {
		return coopIndex;
	}
	
	public void adicionarCoopIndex(int valor){
		this.coopIndex+=valor;
	}
	
	public String getNome(){
		return this.nomeMonitor;
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
