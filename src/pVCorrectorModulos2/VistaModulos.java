package pVCorrectorModulos2;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public interface VistaModulos {
	 /**
	  * Muestra el mensaje con el color seleccionado
	  */
	void mensaje(String msg, Color j);
	 /**
	  * Establece el controlador
	  */
	void controlador(ActionListener ctrMenu);
	 /**
	  *Devuelve el fichero marcado por el usuario
	  */
	 int modulomarcados();

	 /**
	  * Carga las campa�as deprecated no usar
	  */
	void setcampanyas(List<ICampanya> lista);
	 /**
	  * Borra modulos seleccionados
	  */
	void borrarmodulos();
	 /**
	  * Te devuelve el numero de modulos marcados
	  */
	List<String> modulosSeleccionados();
	int getMarcado();
	 /**
	  * Devuelve True si el modulo n esta marcado
	  */
	boolean marcado(int n);
	/**
	 * Le pasa un modulo para mostrarlo por pantalla
	 */
	void importar(String c,ActionListener k);
	/**
	 * Te devuelve el num de campanyas seleccionadas
	 */
	int getCampaSelecc();
	/**
	 * Te devuelve todas las campanyas seleccionadas
	 */
	List<String> getCampanyas();
	/**
	 * Te devuelve el modulo marcado de esa posicion
	 */
	String  ModuloMarcado(int n);
	File importar();
	static final String BORRAR="B";
	static final String IMPORTAR="I";
	static final String VER="V";
	static final String EDITAR="E";
	static final Color FONDO= new Color(237,255,254);
	static final Color FONDO2= new Color(192,255,252);
}
