package pVCorrectorMedidas;

import java.util.ArrayList;
import java.util.List;

public class Medida implements IMedida {
	private int id;
	private Integer medOrig;
	private String campania;
	private String fecha;
	private String hora;
	private String Isc;
	private String Voc;
	private String Pmax;
	private String IPmax;
	private String VPmax;
	private String FF;
	private String modulo;
	private boolean existe;
	private Punto punt; 
	
	public Medida(Medida m) {
	    this.campania = m.getCampania();
	    this.fecha = m.getFecha();
	    this.hora = m.getHora();
	    this.modulo = m.getModulo();
	    
	    id = 1 + Integer.parseInt(myBD.selectEscalar("SELECT MAX(id) FROM Medida"));
	    medOrig = m.getId();
	    medidas(medOrig);
	    
	    myBD.insert("INSERT INTO Medida (id, Campania, Fecha, Hora, Isc, Voc, Pmax, IPmax, VPmax, FF, ModuloNombre, MedidaOrig) "
	        + "VALUES ("+id+",'"+campania+"','"+fecha+"','"+hora+"','"+Isc+"','"+Voc+"','"+Pmax+"','"+IPmax+"','"+VPmax+"','"+FF+"','"+modulo+"',"+medOrig+");");
	  }

	public Medida(int i) {
		String[]med=myBD.select("select * from medida where id= " + i + " ;").get(0);
		id=Integer.parseInt(med[0]);
		campania=med[1];
		fecha=med[2];
		hora=med[3];
		Isc=med[4];
		Voc=med[5];
		Pmax=med[6];
		IPmax=med[7];
		VPmax=med[8];
		FF=med[9];
		modulo=med[10];
		try {
			medOrig=Integer.parseInt(med[11]);
		}catch(Exception e) {
			medOrig=null;
		}
	}
	public Medida(String c, String f, String h, String m) {
		Integer n = Integer.parseInt(myBD.selectEscalar("SELECT COUNT(*) FROM Medida WHERE Campania = '" + c
				+ "' and  Fecha = '" + f + "' and Hora = '" + h + "' and ModuloNombre = '" + m + "';"));
		if (n > 0) {
			id = Integer.parseInt(myBD.selectEscalar("SELECT id FROM Medida WHERE Campania = '" + c + "' and Fecha = '"
					+ f + "' and Hora = '" + h + "' and ModuloNombre = '" + m + "';"));
			campania = myBD.selectEscalar("SELECT Campania FROM Medida WHERE id = " + id + " ;");
			fecha = myBD.selectEscalar("SELECT Fecha FROM Medida WHERE id = " + id + " ;");
			hora = myBD.selectEscalar("SELECT Hora FROM Medida WHERE id = " + id + " ;");
			modulo = myBD.selectEscalar("SELECT ModuloNombre FROM Medida WHERE id = " + id + " ;");
			medidas(id);
			medOrig=null;
			existe = true;
		} else {
			existe = false;
			campania = c;
			fecha = f;
			hora = h;
			modulo = m;
			medOrig=null;
			try {
				id = 1 + Integer.parseInt(myBD.selectEscalar("SELECT MAX(id) FROM Medida"));
			} catch (Exception e) {
				id = 0;
			}
			myBD.insert("INSERT INTO Medida (id, Campania, Fecha, Hora, ModuloNombre) " + " VALUES (" + id + ",'" + c
					+ "', '" + f + "', '" + h + "', '" + m + "');");
		}
	}

	private void medidas(int id2) {
		List<String[]> n = myBD.select("Select * from medida where id=" + id2 + "");
		String[] n2 = n.get(0);
		int i = 4;
		Isc = n2[0 + i];
		Voc = n2[1 + i];
		Pmax = n2[2 + i];
		IPmax = n2[3 + i];
		VPmax = n2[4 + i];
		FF = n2[5 + i];
	}

	public boolean getExiste() {
		return existe;
	}

	public int getId() {
		return id;
	}

	private Punto getPuntosFromDB() {
		if(punt==null)
			punt=new Punto(id);
		return punt;
	}

	@Override
	public List<double[]> generarCurvaIV() {
		List<double[]> res = new ArrayList<double[]>();
		getPuntosFromDB();
		double tensiones[] = punt.getTensiones();
		double corrientes[] = punt.getCorrientes();
		for (int i = 0; i < tensiones.length; i++) {
			double[] d = new double[2];
			d[0] = tensiones[i];
			d[1] = corrientes[i];
			res.add(d);
		}
		return res;
	}

	public List<double[]> generarCurvaPV() {
		List<double[]> res = new ArrayList<double[]>();
		Punto p = getPuntosFromDB();
		double tensiones[] = p.getTensiones();
		double potencias[] = p.getPotencias();
		for (int i = 0; i < tensiones.length; i++) {
			double[] d = new double[2];
			d[0] = tensiones[i];
			d[1] = potencias[i];
			res.add(d);
		}
		return res;
	}

	public void borrar() {
		List<IMedida> m=getMedidasCorregidas();
		for (IMedida iMedida : m) {
			iMedida.borrar();
		}
		m=null;
		myBD.delete("DELETE FROM PARAMETROSC WHERE id = " + id + ";");
		myBD.delete("DELETE FROM Canal WHERE idM = " + id + ";");
		myBD.delete("DELETE FROM Punto WHERE idMedida = " + id + ";");
		myBD.delete("DELETE FROM Medida WHERE id = " + id + ";");
		campania = fecha = FF = hora = IPmax = Isc = modulo = Pmax = Voc = VPmax = null;
		punt= null;
		medOrig= null;
	}

	@Override
	public void añadirVars(List<String> l) {
		Isc = l.get(0);
		Voc = l.get(1);
		Pmax = l.get(2);
		IPmax = l.get(3);
		VPmax = l.get(4);
		FF = l.get(5);
		myBD.update("UPDATE Medida SET Isc='" + Isc + "'  ,Voc='" + Voc + "' ,Pmax='" + Pmax + "'  ,IPmax='" + IPmax
				+ "'  ,VPmax='" + VPmax + "'  ,FF='" + FF + "' WHERE id=" + id + ";");
	}

	@Override
	public List<String> getVars() {
		List<String> vars = new ArrayList<String>();
		vars.add(campania);
		vars.add(fecha);
		vars.add(hora);
		vars.add(Isc);
		vars.add(Voc);
		vars.add(Pmax);
		vars.add(IPmax);
		vars.add(VPmax);
		vars.add(FF);
		return vars;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Campaña: " + campania + "\n");
		sb.append("Fecha: " + fecha + "\n");
		sb.append("Hora: " + hora + "\n");
		sb.append("Isc: " + Isc + "\n");
		sb.append("Voc: " + Voc + "\n");
		sb.append("Pmax: " + Pmax + "\n");
		sb.append("IPmax: " + IPmax + "\n");
		sb.append("VPmax: " + VPmax + "\n");
		sb.append("FF: " + FF + "\n");
		return sb.toString();
	}

	@Override
	public List<String> listacanalesM() {
		return Canal.getNombresModulo(modulo);
	}

	@Override
	public String getModulo() {
		return modulo;
	}

	@Override
	public String getCampania() {
		return campania;
	}

	public String getIsc() {
		return Isc;
	}

	public String getVoc() {
		return Voc;
	}

	public String getPmax() {
		return Pmax;
	}

	public String getIPmax() {
		return IPmax;
	}

	public String getVPmax() {
		return VPmax;
	}

	public String getFF() {
		return FF;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public List<IMedida> getMedidasCorregidas() {
		List<IMedida> l = new ArrayList<IMedida>();
		for (String[] e : myBD.select("SELECT id FROM Medida WHERE MedidaOrig = " + this.id + ";")) {
			l.add(new Medida(Integer.parseInt(e[0])));
		}
		return l;
	}

	public List<String> getParametrosCorreccion() {
		List<String> l = new ArrayList<String>();
		if(medOrig!=null) {
		String[]e=myBD.select("SELECT temperaturaObjetivo, irradianciaObjetivo FROM PARAMETROSC WHERE id = " + this.id + ";").get(0);
			l.add(e[0]);
			l.add(e[1]);
		}
		return l;
	}

	public void setParametrosCorreccion(String t, String g) {
		if (medOrig != null) {
			Integer n = Integer
					.parseInt(myBD.selectEscalar("SELECT COUNT(*) FROM PARAMETROSC WHERE id = " + this.id + ";"));
			if (n > 0) {
				myBD.update("UPDATE PARAMETROSC SET temperaturaObjetivo = '" + t + "', irradianciaObjetivo = '" + g
						+ "' WHERE id = " + this.id + ";");
			} else {
				myBD.insert("INSERT INTO PARAMETROSC (id, temperaturaObjetivo, irradianciaObjetivo) VALUES (" + this.id
						+ ", '" + t + "', '" + g + "');");
			}
		}
	}
}
