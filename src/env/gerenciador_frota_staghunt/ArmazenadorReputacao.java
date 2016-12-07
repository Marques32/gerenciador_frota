// CArtAgO artifact code for project gerenciador_frota

package gerenciador_frota_staghunt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import cartago.*;

public class ArmazenadorReputacao extends Artifact {
	private int VARIACAO_REPUTACAO_GRUPO = 1;
	private int QUANTIDADE_CONDUTOR_GRUPO = 20;
	private int QUANTIDADE_GRUPO = 5;
	private List<Grupo> grupos;
	private int quantidadeMensagem;
	private int quantidadeRodadas;
	private MultiploDataset multiploDataset;
	
	void init() {
		multiploDataset = new MultiploDataset(); 
		this.quantidadeMensagem = 0;
		this.quantidadeRodadas = 0;
		this.grupos = new ArrayList<Grupo>();
		
		int reputacaoInicial = 0;
		
		for (int i = 1; i <= QUANTIDADE_GRUPO; i++) {
			
			Grupo grupo = new Grupo(i, reputacaoInicial, reputacaoInicial+VARIACAO_REPUTACAO_GRUPO);
			
			for (int j = 1; j <= QUANTIDADE_CONDUTOR_GRUPO; j++) {
				
				int numeroCondutor = j+(i-1)*QUANTIDADE_CONDUTOR_GRUPO;
				String nomeCondutor = "inst"+numeroCondutor;
				grupo.adicionarCondutor(nomeCondutor, new Condutor(nomeCondutor));
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
		
		int valorReputacao = reputacao.getValor();
		
		if(valor>80){			
			nivelIrresponsabilidade = 1.5*(valor-80);
			condutor.adicionarLitroExcedido(nivelIrresponsabilidade*0.0066138);
			
			condutor.adicionarCoopIndex(1);
			
			if(condutor.getCoopIndex() == 5){
				if(valorReputacao > 0){
					reputacao.adicionarValor(-1);
				}
				condutor.resetaCoopIndex();
			}
			
			condutor.setCooperou(false);
		}else{
			condutor.adicionarCoopIndex(-1);
			
			if(condutor.getCoopIndex() == -5){
				if(valorReputacao < 9 ){
					reputacao.adicionarValor(1);
				}
				condutor.resetaCoopIndex();
			}
			condutor.setCooperou(true);
		}
		
		quantidadeMensagem++;
		
		int quantidadeCondutores = QUANTIDADE_GRUPO*QUANTIDADE_CONDUTOR_GRUPO;
		
		if(quantidadeCondutores == quantidadeMensagem){
			
			for (Grupo grupo : grupos) {
				if(grupo.existeCooperacaoDeTodos()){
					for (Map.Entry<String, Condutor> par : grupo.getCondutores().entrySet()) {
						par.getValue().adicionarPontuacao(9);
					}
				}else{
					for (Map.Entry<String, Condutor> par : grupo.getCondutores().entrySet()) {
						Condutor cond = par.getValue();
						if(cond.isCooperou()){
							cond.adicionarPontuacao(0);
						}else{
							cond.adicionarPontuacao(7);
						}
					}
				}
			}
			quantidadeRodadas++;
			quantidadeMensagem = 0;
			
			if(quantidadeRodadas % 10 == 0){
				for (Grupo grupo : grupos) {
					
					Map<String, Condutor> condutores = new HashMap<String, Condutor>(grupo.getCondutores());
					for (Map.Entry<String, Condutor> par : condutores.entrySet()) {
						Condutor condutor2 = par.getValue();
						
						double porcentagemDaPontuacaoAnterior = 1;
						
						if(condutor2.getPontuacaoAnterior() > 0){
							porcentagemDaPontuacaoAnterior = condutor2.getPontuacao()/condutor2.getPontuacaoAnterior(); 
						}
						
						condutor2.setPontuacaoAnterior(condutor2.getPontuacao());
						condutor2.setPontuacao(0);
						
						if(porcentagemDaPontuacaoAnterior < 0.9){
							if(!grupo.isPermitido(condutor2.getReputacao())){
								grupo.removeCondutor(condutor2.getNome());
							
								for (Grupo grupoNovo : grupos) {
									if(grupoNovo.isPermitido(condutor2.getReputacao())){
										grupoNovo.adicionarCondutor(condutor2.getNome(), condutor2);
										break;
									}
								}
							}
						}
					}
					multiploDataset.adicionar("rTag "+grupo.getReputacaoMinima()+"-"+grupo.getReputacaoMaxima(), grupo.getCondutores().size(), quantidadeRodadas/10);
				} 
			}
			
			if(quantidadeRodadas != 2000){
				signal("proximaRodada");
			}else{
				exibirGrafico(multiploDataset);
				for (Grupo grupo : grupos) {
					double totalGasto = 0;
					for (Map.Entry<String, Condutor> par : grupo.getCondutores().entrySet()) {
						Condutor cond = par.getValue();
						totalGasto+=cond.getLitroExcedido();
					}
					System.out.println("grupo rTag: "+grupo.getReputacaoMinima()+"-"+grupo.getReputacaoMaxima());
					System.out.println("Total de gasto: "+totalGasto);
				}
			}
		}
		
	}
	
	
	private void exibirGrafico(MultiploDataset multiploDataset){
		JFrame frame = new JFrame("Minha Janela");
		frame.add(multiploDataset.getPanel());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

