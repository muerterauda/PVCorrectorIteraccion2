package pVCorrectorGrafica;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
