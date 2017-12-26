import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import base_de_datos.BD;
import pVCorrectorModulos2.CtrModulos;
import pVCorrectorModulos2.ICampanya;
import pVCorrectorModulos2.IModulo;
import pVCorrectorModulos2.PanelModulos;
import pVCorrectorModulos2.VistaModulos;

public class Main {
	public static void main(String[] args) {
		BD baseDeDatos= BD.getInstance();
		List<String> m = new LinkedList<String>();
		for(String modulo : IModulo.getModulos()) {
			m.add(modulo);
		}
		JFrame ventana = new JFrame("PVCorrector_Modulos");
		VistaModulos vista= new PanelModulos(m, ventana);
		CtrModulos ctr= new CtrModulos(vista);
		vista.controlador(ctr);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setContentPane((JPanel) vista);
		ventana.setPreferredSize(new Dimension(400*2,300*2));
		ventana.pack();
		ventana.setVisible(true);
		ventana.setLocationRelativeTo(null);
		
	}
}
