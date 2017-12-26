package pVCorrectorMedidas;

import java.util.ArrayList;
import java.util.List;

import base_de_datos.BD;

public interface IMedida {
	static BD myBD = BD.getInstance();
	
	public void borrar();
	public void añadirVars(List<String> l);
	public void setParametrosCorreccion(String t, String g);
	public List<double[]> generarCurvaIV();
	public List<double[]> generarCurvaPV();
	
	public List<String> getParametrosCorreccion();
	public int getId();
	public List<String> getVars();
	public String getModulo();
	public String getCampania();
	public String getIsc();
	public List<IMedida> getMedidasCorregidas();
	
	public List<String>listacanalesM();
	public static List<IMedida> getFromBD(String nombre) {
		List<String[]> aux = myBD
				.select("SELECT Campania, Fecha, Hora FROM Medida WHERE ModuloNombre = '" + nombre + "' and MedidaOrig IS NULL;");
		List<IMedida> aux2 = new ArrayList<IMedida>();
		for (int i = 0; i < aux.size(); i++) {
				String c=aux.get(i)[0];
				String f=aux.get(i)[1];
				String h=aux.get(i)[2];
				c.replace(" ", "");
				f.replace(" ", "");
				h.replace(" ","");
				aux2.add(new Medida(c,f,h,nombre));
		}
		return aux2;
	}
	public static void borrarTodas(String modulo) {// si borra un modulo clicado se borra todo en cascada
		 List<IMedida> med=getFromBD(modulo);
		 for (IMedida iMedida : med) {
			iMedida.borrar();
		}
		 med=null;
	}

	
}
