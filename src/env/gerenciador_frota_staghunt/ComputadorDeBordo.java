// CArtAgO artifact code for project gerenciador_frota

package gerenciador_frota_staghunt;

import java.util.Random;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;

public class ComputadorDeBordo extends Artifact {
	private int fatorDeCooperacao;
	private Random random;
	private static int PISO_VELOCIDADE_INEFICIENTE = 81;
	private static int TETO_RANDOM_VELOCIDADE = 40;
	private static int TETO_RANDOM_COOPERACAO = 101;
	
	void init() {
		random = new Random();
		fatorDeCooperacao = random.nextInt(TETO_RANDOM_COOPERACAO);
	}
	
	@OPERATION
	void envia_ct(){
		defineObsProperty("cooperacao", fatorDeCooperacao);
	}
	
	@OPERATION
	void inc() {
		int velocidade;
		
		int valorCooperar = random.nextInt(TETO_RANDOM_COOPERACAO);
		
		if(valorCooperar >= fatorDeCooperacao){
			velocidade = random.nextInt(TETO_RANDOM_VELOCIDADE)+PISO_VELOCIDADE_INEFICIENTE;
		}else{
			velocidade = random.nextInt(PISO_VELOCIDADE_INEFICIENTE);
		}
		
		defineObsProperty("velocidade", velocidade);
		ObsProperty obsProperty = getObsProperty("velocidade");
		
		for(int i = 0; i < 1439; i++){
			await_time(1000);
			valorCooperar = random.nextInt(TETO_RANDOM_COOPERACAO);
					
			if(valorCooperar >= fatorDeCooperacao){
				velocidade = random.nextInt(TETO_RANDOM_VELOCIDADE)+PISO_VELOCIDADE_INEFICIENTE;
			}else{
				velocidade = random.nextInt(PISO_VELOCIDADE_INEFICIENTE);
			}
			obsProperty.updateValue(velocidade);
		}
	}
}

