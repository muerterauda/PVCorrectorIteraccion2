package pVCorrectorGrafica;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pVCorrectorMedidas.IMedida;
import pVCorrectorMedidas.Medida;
import pVCorrectorMedidas.Punto;

public class ListSelectionListenerGraficas implements ListSelectionListener{
	
	private VistaGraficas vista;
	public ListSelectionListenerGraficas(VistaGraficas vista) {
		this.vista=vista;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		 if (e.getValueIsAdjusting())
		        return;
		 vista.actualizarGraficas();
	}
	

}
