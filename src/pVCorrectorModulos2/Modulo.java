package pVCorrectorModulos2;

import java.util.List;

import pVCorrectorMedidas.IMedida;

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
	    	nombre = n;
	    	setValores(alfa,beta,gamma,kappa,rs);
	    } else {
	      nombre = n;
	      myBD.insert("INSERT INTO Modulo VALUES ('" + n + "','"+alfa+"','"+beta+"','"+gamma+"','"+kappa+"','"+rs+"');");
	    }
	  }

	  private void setValores(String alfa2, String beta2, String gamma2, String kappa2, String rs2) {
		  if(alfa2!=null) {
			  setAlfa(alfa2);
		  }
		  if(beta2!=null) {
			  setBata(beta2);
		  }
		  if(gamma2!=null) {
			  setGamma(gamma2);
		  }
		  if(kappa2!=null) {
			  setKappa(kappa2);
		  }
		  if(rs2!=null) {
			  setRs(rs2);
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

	@Override
	public void setAlfa(String alfa2) {
		myBD.update("UPDATE MODULO SET Alfa='" + alfa2 + "' WHERE Name='"+nombre+"';");
		Alfa=alfa2;
	}

	@Override
	public void setBata(String beta2) {
		myBD.update("UPDATE MODULO SET Beta='" + beta2 + "' WHERE Name='"+nombre+"';");
		Beta=beta2;
	}

	@Override
	public void setGamma(String gamma2) {
		myBD.update("UPDATE MODULO SET Gamma='" + gamma2 + "' WHERE Name='"+nombre+"';");
		Gamma=gamma2;
	}

	@Override
	public void setKappa(String kappa2) {
		myBD.update("UPDATE MODULO SET Kappa='" + kappa2 + "' WHERE Name='"+nombre+"';");
		Kappa=kappa2;
	}

	@Override
	public void setRs(String rs2) {
		myBD.update("UPDATE MODULO SET Rs='" + rs2 + "' WHERE Name='"+nombre+"';");
		Rs=rs2;
	}
}
