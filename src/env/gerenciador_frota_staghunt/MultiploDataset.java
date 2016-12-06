package gerenciador_frota_staghunt;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class MultiploDataset {

	private DefaultCategoryDataset dados;
	private JFreeChart grafico;
	
	public MultiploDataset() {
		this.dados = new DefaultCategoryDataset();
		this.grafico = ChartFactory.createLineChart("Condução", "iterações", "população", dados, PlotOrientation.VERTICAL, true, true, false);
	}
	
	public void adicionar(String grupo, Integer valor, Integer rodada){
		dados.addValue(valor,grupo,rodada);
	}
	
	public JPanel getPanel(){
		return new ChartPanel(grafico);
	}


}
