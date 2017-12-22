package pVCorrectorMedidas;


import java.awt.Color;
import java.awt.event.ActionListener;

public interface VistaMedidas {
	 /**
	  * Muestra el mensaje con el color seleccionado
	  */
	void mensaje(String msg, Color j);
	 /**
	  * Establece el controlador
	  */
	void controlador(ActionListener ctrMenu);
	static final String[] nombres={"Campaña","Fecha","Hora","Isc","Voc","Pmax",
			"Ipmax","VPmax","FF"};
}
