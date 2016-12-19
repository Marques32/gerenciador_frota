// CArtAgO artifact code for project gerenciador_frota

package gerenciador_frota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import cartago.*;

public class SistemaDeSancao extends Artifact {
	private int VARIACAO_REPUTACAO_GRUPO = 1;
	private int QUANTIDADE_CONDUTOR_GRUPO = 20;
	private int QUANTIDADE_GRUPO = 5;
	private List<Grupo> grupos;
	private int quantidadeMensagens;
	private int QUANTIDADE_MEDICOES;

	void init() {
		this.quantidadeMensagens = 0;
		this.grupos = new ArrayList<Grupo>();
		this.QUANTIDADE_MEDICOES = QUANTIDADE_CONDUTOR_GRUPO * QUANTIDADE_GRUPO * 1;

		int reputacaoInicial = 0;

		for (int i = 1; i <= QUANTIDADE_GRUPO; i++) {

			Grupo grupo = new Grupo(i, reputacaoInicial, reputacaoInicial + VARIACAO_REPUTACAO_GRUPO);

			for (int j = 1; j <= QUANTIDADE_CONDUTOR_GRUPO; j++) {

				int numeroCondutor = j + (i - 1) * QUANTIDADE_CONDUTOR_GRUPO;
				String nomeCondutor = "mo" + numeroCondutor;
				grupo.adicionarCondutor(nomeCondutor, new Condutor(nomeCondutor));
			}

			grupos.add(grupo);

			reputacaoInicial += (VARIACAO_REPUTACAO_GRUPO + 1);
		}

	}

	@OPERATION
	void coloca_ct(int ct, String quem) {
		Grupo grupoAtual = null;

		for (Grupo grupo : grupos) {
			if (grupo.contem(quem)) {
				grupoAtual = grupo;
				break;
			}
		}

		Condutor condutor = grupoAtual.buscaContudor(quem);
		condutor.setCt(ct);
		
	}

	@OPERATION
	void punir(int valor, String quem) {
		
		Grupo grupoAtual = buscaGrupoCondutor(quem);
		
		Condutor condutor = grupoAtual.buscaContudor(quem);

		Reputacao reputacao = condutor.getReputacao();

		double nivelDeGasto = 0;

		int valorReputacao = reputacao.getValor();

		nivelDeGasto = 0.015 * (valor - 80);
		condutor.adicionarLitroExcedido(nivelDeGasto * 0.0066138);

		condutor.adicionarCoopIndex(1);

		if (condutor.getCoopIndex() == 5) {
			if (valorReputacao > 0) {
				reputacao.adicionarValor(-1);
			}
			condutor.resetaCoopIndex();
		}
		
		condutor.setCooperou(false);
		
		atualizarAposSancao(grupoAtual, condutor);
	}

	@OPERATION
	void recompensar(int valor, String quem) {
		
		Grupo grupoAtual = buscaGrupoCondutor(quem);

		Condutor condutor = grupoAtual.buscaContudor(quem);

		Reputacao reputacao = condutor.getReputacao();

		int valorReputacao = reputacao.getValor();

		condutor.adicionarCoopIndex(-1);

		if (condutor.getCoopIndex() == -5) {
			if (valorReputacao < 9) {
				reputacao.adicionarValor(1);
			}
			condutor.resetaCoopIndex();
		}
		condutor.setCooperou(true);
	
		atualizarAposSancao(grupoAtual, condutor);
	}
	
	public Grupo buscaGrupoCondutor(String quem){
		Grupo grupoAtual = null;

		for (Grupo grupo : grupos) {
			if (grupo.contem(quem)) {
				grupoAtual = grupo;
				break;
			}
		}
		
		return grupoAtual;
	}
	
	private void atualizarAposSancao(Grupo grupoAtual, Condutor condutor) {
		quantidadeMensagens++;

		if (!grupoAtual.isPermitido(condutor.getReputacao())) {
			realizarMudancaDeGrupo(condutor);
		}

		if (QUANTIDADE_MEDICOES == quantidadeMensagens) {
			imprimirSaida();
		}
	}

	private void realizarMudancaDeGrupo(Condutor condutor) {
		for (Grupo grupo : grupos) {

			grupo.removeCondutor(condutor.getNome());

			for (Grupo grupoNovo : grupos) {
				if (grupoNovo.isPermitido(condutor.getReputacao())) {
					grupoNovo.adicionarCondutor(condutor.getNome(), condutor);
					break;
				}
			}
		}
	}

	private void imprimirSaida() {
		for (Grupo grupo : grupos) {
			double totalGasto = 0;
			Map<String, Condutor> condutores = grupo.getCondutores();
			int numeroCondutores = condutores.size();
			for (Map.Entry<String, Condutor> par : condutores.entrySet()) {
				Condutor cond = par.getValue();
				totalGasto += cond.getLitroExcedido();
			}

			System.out.println("grupo reputação: " + grupo.getReputacaoMinima() + "-" + grupo.getReputacaoMaxima());
			System.out.println("Média de gasto: " + (totalGasto / numeroCondutores));
			System.out.println("Quantidade Condutores Ct maior que 50: " + grupo.buscaCondutorCtMaiorQue(50));
			System.out.println("Numero de condutores: " + numeroCondutores);

		}
	}
}
