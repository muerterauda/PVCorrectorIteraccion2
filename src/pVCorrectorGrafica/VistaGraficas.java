package pVCorrectorGrafica;


import java.awt.Color;
import java.awt.event.ActionListener;

import estructurasDatos.Tuple2;

public interface VistaGraficas {
	void mensaje(String msg, Color j);
	public void controlador(ActionListener ctrmen, ListSelectionListenerGraficas ctrlist);
	public void actualizarGraficas();
	public int getMetodoSelected();
	public String getMetodoSelect();
	public Tuple2<String,String> seleccionarParametros();
	static final String AplicarPorcedimiento="A";
	void añadirGrafica(String v);
}
