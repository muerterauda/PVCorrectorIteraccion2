package pVCorrectorModulos2;

import java.util.ArrayList;
import java.util.List;

import base_de_datos.BD;
import pVCorrectorMedidas.IMedida;

public interface IModulo {
	static BD myBD = BD.getInstance();

	List<IMedida> getMedidas();

	String getNombre();

	List<ICampanya> getCampanyas();

	static List<String> getModulos() {
		List<String> aux = new ArrayList<String>();
		List<String[]> auxdb = myBD.select("SELECT Name FROM Modulo");
		for (int i = 0; i < auxdb.size(); i++) {
			aux.add(auxdb.get(i)[0]);
		}
		return aux;
	}

	static boolean estaModulo(String nombre) {
		int i = Integer.parseInt(myBD.selectEscalar("SELECT COUNT(*) FROM Modulo WHERE Name='" + nombre + "';"));
		return i > 0;
	}

	static void borrar(String modulo) { //borrado en cascada de todo lo dependiente del moudlulo y del mismo
		IMedida.borrarTodas(modulo);
		ICampanya.borrarTodas(modulo);
		myBD.delete("DELETE FROM Modulo WHERE Name = '" + modulo + "';");
	}

	static List<ICampanya> getCampanyas(String modulo) {
		return ICampanya.getFromDB(modulo);
	}
	
	public void borrar();
	String getAlfa();
	String getBeta();
	String getGamma();
	String getKappa();
	String getRs();
	void setAlfa(String alfa2);
	void setBata(String beta2);
	void setGamma(String gamma2);
	void setKappa(String kappa2);
	void setRs(String rs2);
}
