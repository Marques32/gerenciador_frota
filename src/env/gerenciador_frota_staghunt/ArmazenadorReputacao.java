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
	private int quantidadeMensagens;
	private int quantidadeRodadas;
	private MultiploDataset multiploDataset;
	
	void init() {
		multiploDataset = new MultiploDataset(); 
		this.quantidadeMensagens = 0;
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
			nivelIrresponsabilidade = 0.015*(valor-80);
			condutor.adicionarLitroExcedido(nivelIrresponsabilidade*0.0746667);
			
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
		
		quantidadeMensagens++;
		
		int quantidadeCondutores = QUANTIDADE_GRUPO*QUANTIDADE_CONDUTOR_GRUPO*480;
		
				for (Grupo grupo : grupos) {
					
					Map<String, Condutor> condutores = new HashMap<String, Condutor>(grupo.getCondutores());
					for (Map.Entry<String, Condutor> par : condutores.entrySet()) {
						Condutor condutor2 = par.getValue();
						
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
			
		if(quantidadeCondutores == quantidadeMensagens){
				for (Grupo grupo : grupos) {
					double totalGasto = 0;
					Map<String, Condutor> condutores = grupo.getCondutores();
					int numeroCondutores = condutores.size();
					for (Map.Entry<String, Condutor> par : condutores.entrySet()) {
						Condutor cond = par.getValue();
						totalGasto+=cond.getLitroExcedido();
					}
					multiploDataset.adicionar("reputação", grupo.getCondutores().size(),grupo.getReputacaoMinima()+"-"+grupo.getReputacaoMaxima());
					System.out.println("grupo rTag: "+grupo.getReputacaoMinima()+"-"+grupo.getReputacaoMaxima());
					System.out.println("Total de gasto: "+totalGasto);
					System.out.println("Média de gasto: "+(totalGasto/numeroCondutores));
					Condutor condGast = grupo.buscaMaiorGastador();
					System.out.println("Maior gastador "+condGast.getNome()+": "+condGast.getLitroExcedido());
					Condutor condGas = grupo.buscaMenorGastador();
					if(condGas != null){	
						System.out.println("Menor gastador "+condGas.getNome()+": "+condGas.getLitroExcedido());
					}
				}
				exibirGrafico(multiploDataset);
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

