package pVCorrectorMedidas;

import base_de_datos.BD;

public class Punto {

	static BD myBD = BD.getInstance();

	int id;
	private String tension;
	private String corriente;
	private String potencia;
	private int idMed;

	public int getIdMed() {
		return idMed;
	}

	public Punto(String t, String c, String p, int idMedida) { // el punto puede estar repetido
		try {
			id = 1 + Integer.parseInt(myBD.selectEscalar("SELECT MAX(id) FROM Punto;"));
		} catch (Exception e) {
			id = 0;
		}
		tension = t;
		corriente = c;
		potencia = p;
		idMed = idMedida;
		myBD.insert("INSERT INTO Punto VALUES (" + id + ", " + idMedida + ", '" + tension + "', '" + corriente + "', '"
				+ potencia + "');");
	}

	public Punto(int idM) {
		try {
			String[] aux = myBD.select("SELECT tension, corriente, potencia FROM Punto WHERE idMedida = " + idM + " ;").get(0);
			tension=aux[0];
			corriente=aux[1];
			potencia=aux[2];
			idMed=idM;
		}catch(Exception e) {
			tension = null;
			corriente = null;
			potencia = null;
			idMed=-1;
		}
	}
	public double[] getTensiones() {
		String[] parts = tension.split(" ");
		double[] tensiones = new double[parts.length];
		for (int i = 0; i < parts.length; i++) {
			tensiones[i] = Double.parseDouble(parts[i]);
		}
		return tensiones;
	}

	public double[] getCorrientes() {
		String[] parts = corriente.split(" ");
		double[] tensiones = new double[parts.length];
		for (int i = 0; i < parts.length; i++) {
			tensiones[i] = Double.parseDouble(parts[i]);
		}
		return tensiones;
	}
	
	public double[] getPotencias() {
		String[] parts = potencia.split(" ");
		double[] tensiones = new double[parts.length];
		for (int i = 0; i < parts.length; i++) {
			tensiones[i] = Double.parseDouble(parts[i]);
		}
		return tensiones;
	}

	public String toString() {
		return "Tension: " + tension + " Corriente: " + corriente + " Potencia: " + potencia;
	}

	

}
