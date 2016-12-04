// CArtAgO artifact code for project gerenciador_frota

package gerenciador_frota_staghunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cartago.*;

public class ArmazenadorReputacao extends Artifact {
	private int VARIACAO_REPUTACAO_GRUPO = 20;
	private int QUANTIDADE_CONDUTOR_GRUPO = 20;
	private int QUANTIDADE_GRUPO = 5;
	private List<Grupo> grupos;
	
	void init() {
		this.grupos = new ArrayList<Grupo>();
		
		int reputacaoInicial = 0;
		
		for (int i = 1; i <= QUANTIDADE_GRUPO; i++) {
			
			Grupo grupo = new Grupo(i, reputacaoInicial, reputacaoInicial+VARIACAO_REPUTACAO_GRUPO);
			
			for (int j = 1; j <= QUANTIDADE_CONDUTOR_GRUPO; j++) {
				
				int numeroCondutor = j+(i-1)*QUANTIDADE_CONDUTOR_GRUPO;
				grupo.adicionarCondutor("inst"+numeroCondutor, new Condutor());
			}
			
			grupos.add(grupo);
			
			reputacaoInicial+=(VARIACAO_REPUTACAO_GRUPO+1);
		}
		
	}
	
	@OPERATION
	void atualiza(int valor, String quem) {
		Grupo grupoAtual = null;
		
		for (Grupo grupo : grupos) {
			if(grupo.contem(quem)){
				grupoAtual = grupo;
				break;
			}
		}
		
		Condutor condutor = grupoAtual.buscaContudor(quem);
		Reputacao reputacao = condutor.getReputacao();
		
		double nivelIrresponsabilidade = 0;
		
		if(valor>80){			
			nivelIrresponsabilidade = 1.5*(valor-80);
			condutor.adicionarLitroExcedido(nivelIrresponsabilidade*0.0066138);
		}
		
		double similaridade = 1;
		
		double valorReputacaoAnterior = reputacao.getReputacaoAnterior();
		double lembranca = reputacao.getLembranca();
		
		if(valorReputacaoAnterior != 0 || nivelIrresponsabilidade != 0){
			if(nivelIrresponsabilidade > valorReputacaoAnterior){
				similaridade = valorReputacaoAnterior/nivelIrresponsabilidade;
			}else{
				similaridade = nivelIrresponsabilidade/valorReputacaoAnterior;
			}
			
			lembranca = (lembranca+similaridade)/2;
		}
		
		reputacao.setLembranca(lembranca);
		reputacao.setReputacaoAnterior(reputacao.getValor());
		reputacao.setNivelIrresponsabilidade(nivelIrresponsabilidade);
		reputacao.atualizaValor();
		
		if(!grupoAtual.isPermitido(reputacao)){
			grupoAtual.removeCondutor(quem);
			
			for (Grupo grupo : grupos) {
				if(grupo.isPermitido(reputacao)){
					grupo.adicionarCondutor(quem, condutor);
				}
			}
		}
		
	}
	
	@OPERATION
	void listar(){
		
		for (Grupo grupo : grupos) {
			System.out.println("grupo "+grupo.getReputacaoMinima()+"-"+grupo.getReputacaoMaxima());
			
			double litrosTotalExcedidos = 0;
			double valorReputacaoTotal = 0;
			Map<String, Condutor> condutores = grupo.getCondutores();
			int quantidadeCondutores = condutores.size();
			
			for ( Map.Entry<String, Condutor> par : condutores.entrySet()) {
				valorReputacaoTotal+=par.getValue().getReputacao().getValor();
				litrosTotalExcedidos+=par.getValue().getLitroExcedido(); 	
			}
			
			System.out.println("Total gasto: "+litrosTotalExcedidos);
			System.out.println("Média de gasto condutor: "+(litrosTotalExcedidos/quantidadeCondutores));
			System.out.println("Total Reputação: "+valorReputacaoTotal);
			System.out.println("Média reputacao condutor: "+(valorReputacaoTotal/quantidadeCondutores));
		}
	}
}

