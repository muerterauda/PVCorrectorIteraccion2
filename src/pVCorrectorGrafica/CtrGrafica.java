package pVCorrectorGrafica;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import correcciones.MetCorrecion;
import correcciones.Metodo1;
import correcciones.Metodo2;
import estructurasDatos.Tuple2;
import pVCorrectorMedidas.IMedida;


public class CtrGrafica implements ActionListener{
	private VistaGraficas vista;
	private IMedida med;
	private MetCorrecion met;
	public CtrGrafica(VistaGraficas menu, IMedida med) {
		this.vista= menu;
		this.med=med;
		this.vista.mensaje("Inicializacion correcta", Color.BLUE);
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		vista.mensaje(" ",Color.BLUE);
		if(comando.equals(VistaGraficas.AplicarPorcedimiento)) {
			switch(vista.getMetodoSelected()) {
			case 0:
				met=null;
				vista.mensaje("No se ha seleccionado ningun metodo",Color.RED);
				break;
			case 1:
				try {
				Tuple2<String,String> b=vista.seleccionarParametros();
				met= new Metodo1(med,b._1(), b._2());
				}catch(Exception e) {
					vista.mensaje("Error con los parametros en Procedimiento 1", Color.RED);
				}
				break;
			case 2:
				try {
				Tuple2<String,String> b=vista.seleccionarParametros();
				met= new Metodo2(med, b._1(), b._2());
				}catch(Exception e){
					vista.mensaje("Error en los parametros en Procedimiento 2", Color.RED);
				}
				break;
			default:
				met=null;
				vista.mensaje("No implementado",Color.RED);
				break;
			}
			try {
			if(met!=null) {
				String nueva=null;
				nueva= met.aplicarMetodo();
				vista.aņadirGrafica(nueva);
			}
			}catch(Exception e) {
				vista.mensaje("Error con los parametros", Color.RED);
			}
		}
	}
}
