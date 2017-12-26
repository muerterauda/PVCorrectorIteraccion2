package pVCorrectorModulos2;

import java.util.ArrayList;
import java.util.List;

import base_de_datos.BD;
import pVCorrectorMedidas.IMedida;

public interface ICampanya {
	static BD myBD = BD.getInstance();

	static List<ICampanya> getFromDB(String modulo) {
		List<ICampanya> cams = new ArrayList<ICampanya>();
		List<String[]> list = myBD.select("SELECT * FROM Campanya WHERE ModuloNombre = '" + modulo + "';");
		for (String[] ss : list) {
			cams.add(new Campanya(ss[0], ss[1], ss[2], ss[3]));
		}
		return cams;
	}

	static boolean isInDB(String nombre, String modulo) {
		int i = Integer.parseInt(myBD.selectEscalar(
				"SELECT COUNT(*) FROM Campanya WHERE Nombre = '" + nombre + "' AND ModuloNombre = '" + modulo + "';"));
		return i > 0;
	}

	static void borrarTodas(String modulo) { // si borra un modulo clicado se borra todo en cascada
		myBD.delete("DELETE FROM Campanya WHERE ModuloNombre = '" + modulo + "';");
	}

	public void borrar(); // si selecciona campaña y la borra directamente
	
	public List<IMedida> medidasAsociadas();
	
	public static List<IMedida> medidasAsociadas(List<ICampanya> camps){
		List<IMedida> res = new ArrayList<IMedida>();
		List<IMedida> aux2;
		for(ICampanya c : camps) {
			aux2 = c.medidasAsociadas();
			for(IMedida m : aux2) {
				res.add(m);
			}
		}
		return res;
	}
	
	String getFechaFin();
	String getFechaInit();
	String getNombre();
	void setFechaFin(String s);
	void setFechaInit(String s);

}
