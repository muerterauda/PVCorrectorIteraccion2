package pVCorrectorGrafica;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Metodo1Panel extends JOptionPane {
	
	private JLabel ObjetivoTemperaturaJL;
	private JTextField ObjetivoTemperaturaJT;
	private JLabel ObjetivoIrradianciaJL;
	private JTextField ObjetivoIrradianciaJT;
	private JPanel panelB;
	public Metodo1Panel() {
		panelB= new JPanel();
		ObjetivoTemperaturaJL= new JLabel("Introduzca temperatura ojetivo: ");
		ObjetivoIrradianciaJL= new JLabel("Introduzca irradiancia objetivo: ");
		ObjetivoTemperaturaJT= new JTextField(10);
		ObjetivoIrradianciaJT= new JTextField(10);
		panelB.setLayout(new GridLayout(2, 2));
		panelB.add(ObjetivoTemperaturaJL);
		panelB.add(ObjetivoTemperaturaJT);
		panelB.add(ObjetivoIrradianciaJL);
		panelB.add(ObjetivoIrradianciaJT);
		super.setLayout(new BorderLayout());
		super.add(panelB, BorderLayout.CENTER);
	}
	public String getTemperatura() {
		return ObjetivoTemperaturaJT.getText();
	}
	public String getIrradiancia() {
		return ObjetivoIrradianciaJT.getText();
	}
}
