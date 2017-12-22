package pVCorrectorModulos2;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import lector_de_ficheros.LectorArchivo;
import pVCorrectorMedidas.*;


public class CtrModulos implements ActionListener{
	private VistaModulos vista;

	public CtrModulos(VistaModulos menu) {
		this.vista= menu;
		this.vista.mensaje("Inicializacion correcta", Color.BLUE);
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		vista.mensaje("Procesando",Color.BLUE);
		switch(comando){
		case VistaModulos.BORRAR:
			for(String c:vista.modulosSeleccionados()) {
				IModulo.borrar(c);
			}
			vista.borrarmodulos();
			vista.mensaje("Modulos borrados", Color.BLUE);
			break;
		case VistaModulos.EDITAR:
			vista.mensaje("Edicion realizada correctamente",Color.BLUE);
			break;
		case VistaModulos.IMPORTAR:
			File ficheroimportacion=vista.importar();
			try{
				LectorArchivo lector= new LectorArchivo(ficheroimportacion);
				lector.leer();
				vista.importar(lector.getModulo(),this);
				vista.mensaje("Importacion realizada correctamente",Color.BLUE);
			}catch(Exception e){
				vista.mensaje("Error al importar el fichero", Color.RED);
			}
			break;
		case VistaModulos.VER:
			if(vista.modulomarcados() < 1) {
				vista.mensaje("Seleccione un modulo", Color.RED);
			} else if (vista.modulomarcados() > 1) {
				vista.mensaje("Seleccione un solo modulo", Color.RED);
			} else {
				String aux = vista.ModuloMarcado(vista.getMarcado());
				Modulo m = new Modulo(aux);
				crearVista(m);
				vista.mensaje("Medidas cargadas correctamente",Color.BLUE);
			}

			vista.ver(); //useless
			break;
		default:
			int num= vista.modulomarcados();
			if(num==1){
				try{
				String modulo=vista.ModuloMarcado(vista.getMarcado());
				List<ICampanya> lista=IModulo.getCampanyas(modulo);
				vista.setcampanyas(lista);
				vista.mensaje("1 modulo marcado", Color.BLUE);
				}catch(Exception e) {
					vista.setcampanyas(null);
					vista.mensaje("Error fatal del Sistema", Color.BLUE);
				}
			}else if(num!=0){
				vista.setcampanyas(null);
				vista.mensaje(num+" modulos marcados", Color.BLUE);
			}else{
				vista.setcampanyas(null);
				vista.mensaje("Ningun modulo seleccionado",Color.BLUE);
			}
			break;
		}
	}

	private void crearVista(Modulo m) {
		JFrame ventana = new JFrame("Medidas");
		VistaMedidas vista=new PanelMedidas(m.getNombre(), m.getMedidas());
		CtrMedidas ctr = new CtrMedidas(vista, m);
		vista.controlador(ctr);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setContentPane((JPanel) vista);
	//	ventana.setMinimumSize(new Dimension(400*2,300*2));
		ventana.pack();
	//	ventana.setBackground(Color.WHITE);
		ventana.setVisible(true);
		ventana.setLocationRelativeTo(null);
	}
}
