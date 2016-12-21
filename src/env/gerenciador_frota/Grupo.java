package gerenciador_frota;

import java.util.HashMap;
import java.util.Map;

public class Grupo {
	private final int id;
	private final int reputacaoMaxima;
	private final int reputacaoMinima;
	private Map<String,Condutor> condutores;
	
	public Grupo(int id, int reputacaoMinima, int reputacaoMaxima) {
		this.id = id;
		this.reputacaoMaxima = reputacaoMaxima;
		this.reputacaoMinima = reputacaoMinima;
		this.condutores = new HashMap<String, Condutor>();
	}

	public int getId() {
		return id;
	}

	public int getReputacaoMaxima() {
		return reputacaoMaxima;
	}

	public int getReputacaoMinima() {
		return reputacaoMinima;
	}
	
	public Map<String,Condutor> getCondutores(){
		return this.condutores;
	}
	
	public void adicionarCondutor(String nomeMonitor, Condutor condutor){
		this.condutores.put(nomeMonitor, condutor);
	}

	public boolean contem(String condutor) {
		return this.condutores.containsKey(condutor);
	}

	public Condutor buscaContudor(String nomeMonitor) {
		return this.condutores.get(nomeMonitor);
	}
	
	public boolean isPermitido(Reputacao reputacao){
		int valorReputacao = reputacao.getValor();
		
		if(valorReputacao <= this.reputacaoMaxima && valorReputacao >= this.reputacaoMinima){
			return true;
		}
		
		return false;
	}

	public void removeCondutor(String nomeMonitor) {
		this.condutores.remove(nomeMonitor);
	}

	public int quantidadeCondutoresCtMaiorQue(int valor) {
		int ctMaior = 0;
		
		for (Map.Entry<String, Condutor> par : condutores.entrySet()) {
			Condutor cond = par.getValue();
			
			if(cond.getCt() > valor){
				ctMaior++;
			}
		}
		
		return ctMaior;
	}
}
