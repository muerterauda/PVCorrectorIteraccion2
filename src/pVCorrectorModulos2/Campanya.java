package pVCorrectorModulos2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pVCorrectorMedidas.IMedida;

public class Campanya implements ICampanya {

	String nombre;
	String fechaInit;
	String fechaFin;
	String modulo;

	public Campanya(String nombre, String modulo, String fi, String ff) {
		if (ICampanya.isInDB(nombre, modulo)) { // existe en la BD
			this.nombre = nombre;
			this.modulo = modulo;
			try {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
				String[] fechas = myBD.select("SELECT FechaInit, FechaFin FROM Campanya WHERE Nombre = '" + nombre
						+ "' AND ModuloNombre = '" + modulo + "';").get(0);
				fechaInit = fechas[0];
				fechaFin = fechas[1];
				Date dateInit = format.parse(fechaInit);
				Date dateFin = format.parse(fechaFin);
				Date dateI = format.parse(fi);
				Date dateF = format.parse(ff);
				if (dateInit.after(dateI)) {// fecha inicial leida es mas antigua
					fechaInit = fi;
					setFechaInit(fi);
				}
				if (dateFin.before(dateF)) {// fecha final leida es mas actual
					fechaFin = ff;
					setFechaFin(ff);
				}
			} catch (Exception e) {
				System.out.println("error fechas");
				fechaInit = fi;
				fechaFin = ff;
			}
		} else { // no existía en la BD
			myBD.insert("INSERT INTO Campanya (Nombre, ModuloNombre) VALUES ('" + nombre + "','" + modulo + "');");
			this.nombre = nombre;
			this.modulo = modulo;
			setFechaInit(fi);
			setFechaFin(ff);
		}
	}
	
	public String getFechaInit() {
		return fechaInit;
	}

	public void setFechaInit(String fechaInit) {
		myBD.update("UPDATE Campanya SET FechaInit = '" + fechaInit + "' WHERE Nombre = '" + nombre
				+ "' AND ModuloNombre = '" + modulo + "';");
		this.fechaInit = fechaInit;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		myBD.update("UPDATE Campanya SET FechaFin = '" + fechaFin + "' WHERE Nombre = '" + nombre
				+ "' AND ModuloNombre = '" + modulo + "';");
		this.fechaFin = fechaFin;
	}

	public String getNombre() {
		return nombre;
	}

	public void borrar() {
		IMedida.borrarTodas(modulo);
		myBD.delete("DELETE FROM Campanya WHERE Nombre = '" + nombre + "' AND ModuloNombre = '" + modulo + "';");
		modulo = nombre = fechaFin = fechaInit = null;
	}

	@Override
	public List<IMedida> medidasAsociadas() {
		return IMedida.getFromBD(modulo, nombre);
	}

}
