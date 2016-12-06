package gerenciador_frota_staghunt;

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
	
	public void adicionarCondutor(String nome, Condutor condutor){
		this.condutores.put(nome, condutor);
	}

	public boolean contem(String condutor) {
		return this.condutores.containsKey(condutor);
	}

	public Condutor buscaContudor(String condutor) {
		return this.condutores.get(condutor);
	}
	
	public boolean isPermitido(Reputacao reputacao){
		int valorReputacao = reputacao.getValor();
		
		if(valorReputacao <= this.reputacaoMaxima && valorReputacao >= this.reputacaoMinima){
			return true;
		}
		
		return false;
	}

	public void removeCondutor(String quem) {
		this.condutores.remove(quem);
	}

	public boolean existeCooperacaoDeTodos() {
		for (Map.Entry<String, Condutor> par : condutores.entrySet()) {
			if(!par.getValue().isCooperou()){
				return false;
			}
		}
		return true;
	}
}
