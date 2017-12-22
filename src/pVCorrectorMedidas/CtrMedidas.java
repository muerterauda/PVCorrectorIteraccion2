package pVCorrectorMedidas;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pVCorrectorGrafica.CtrGrafica;
import pVCorrectorGrafica.ListSelectionListenerGraficas;
import pVCorrectorGrafica.PanelGrafica;
import pVCorrectorModulos2.IModulo;
import pVCorrectorModulos2.Modulo;

public class CtrMedidas implements ActionListener{
	private VistaMedidas menu;
	private IModulo modulo;

	public CtrMedidas(VistaMedidas menu, Modulo m) {
		this.menu= menu;
		this.modulo = modulo;
		this.menu.mensaje("Inicializacion correcta", Color.BLUE);
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
			String comando = evento.getActionCommand();
			Scanner sc = new Scanner (comando);
			sc.useDelimiter("\n");
			String x=sc.next();
			String x2=sc.next();
			String x3=sc.next();
			String x4=sc.next();
			sc.close();
			IMedida m = new Medida(x,x2,x3,x4);
			JFrame ventana = new JFrame("CurvaIV");
			List<double[]>lista= m.generarCurvaIV();
			PanelGrafica vista=new PanelGrafica(lista,m);
			CtrGrafica ctr = new CtrGrafica(vista,m);
			ListSelectionListenerGraficas ctrlist= new ListSelectionListenerGraficas(vista);
			vista.controlador(ctr, ctrlist);
			ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ventana.setContentPane((JPanel) vista);
		//	ventana.setMinimumSize(new Dimension(400*2,300*2));
			ventana.pack();
		//	ventana.setBackground(Color.WHITE);
			ventana.setVisible(true);
			ventana.setLocationRelativeTo(null);
			menu.mensaje(" ",Color.BLUE);

	}
}
