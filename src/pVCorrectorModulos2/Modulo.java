package pVCorrectorModulos2;

import java.util.ArrayList;
import java.util.List;

import pVCorrectorMedidas.IMedida;
import pVCorrectorMedidas.Medida;

public class Modulo implements IModulo {
	
	private String nombre;
	private String Alfa;
	private String Beta;
	private String Gamma;
	private String Kappa;
	private String Rs;
	public Modulo(String n) {
		if (Modulo.isDB(n)) {
			actualizar(n);
			nombre = myBD.selectEscalar("SELECT Name FROM Modulo WHERE Name = '" + n + "';");
		} else {
			nombre = n;
			myBD.insert("INSERT INTO Modulo(name) VALUES ('" + n + "');");
		}
	}

	public Modulo(String n,String alfa,String beta, String gamma,String kappa, String rs) {
	    if (Modulo.isDB(n)) {
	      actualizar(n);
	      nombre = myBD.selectEscalar("SELECT Name FROM Modulo WHERE Name = '" + n + "';");
	    } else {
	      nombre = n;
	      myBD.insert("INSERT INTO Modulo VALUES ('" + n + "',"+alfa+","+beta+","+gamma+","+kappa+","+rs+");");
	    }
	  }

	  private void actualizar(String n){
	    Alfa=myBD.selectEscalar("SELECT Alfa FROM Modulo WHERE Name = '" + n + "';");
	    Beta=myBD.selectEscalar("SELECT Beta FROM Modulo WHERE Name = '" + n + "';");
	    Gamma=myBD.selectEscalar("SELECT Gamma FROM Modulo WHERE Name = '" + n + "';");
	    Kappa=myBD.selectEscalar("SELECT Kappa FROM Modulo WHERE Name = '" + n + "';");
	    Rs=myBD.selectEscalar("SELECT Rs FROM Modulo WHERE Name = '" + n + "';");
	  }

	public static boolean isDB(String n) {
		Integer i = Integer.parseInt(myBD.selectEscalar("SELECT COUNT(*) FROM Modulo WHERE Name = '" + n + "';"));
		if (i > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<IMedida> getMedidas() {
		return IMedida.getFromBD(nombre);
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public String toString(){
		return nombre;
	}
	@Override
	public List<ICampanya> getCampanyas() {
		return ICampanya.getFromDB(nombre);
	}
	@Override
	public void borrar() {
		IModulo.borrar(nombre);
		Alfa = Beta = Gamma = Kappa = nombre = null;
	}
	public String getAlfa() {
		return Alfa;
	}
	public String getBeta() {
		return Beta;
	}
	public String getGamma() {
		return Gamma;
	}
	public String getKappa() {
		return Kappa;
	}

	@Override
	public String getRs() {
		return Rs;
	}
}
