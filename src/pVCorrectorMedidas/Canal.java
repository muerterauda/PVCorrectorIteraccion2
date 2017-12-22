package pVCorrectorMedidas;

import java.util.LinkedList;
import java.util.List;

import base_de_datos.BD;

public class Canal {
	static BD myBD = BD.getInstance();
	private String nombre;
	private double valormedio;
	private double valorinicial;
	private double valorfinal;
	private String unidad;
	private int id;
	private int idM;
	private String campania;
	private String modulo;
	public static List<String> getNombresModulo(String n){
		List<String> lista= new LinkedList<>();
		List<String[]>lista2=myBD.select("SELECT DISTINCT nombre FROM Canal WHERE ModuloNombre='"+n+"';");
		for (String[] strings : lista2) {
			lista.add(strings[0]);
		}
		return lista;
	}
	public static List<String[]> getMedidasModulo(int idM, String m,String c,String canal){
		List<String[]> lista= new LinkedList<>();
		List<String[]>lista2=myBD.select("SELECT valorM, valorI,valorF,unidad FROM Canal WHERE ModuloNombre='"+m+"' and "
				+ " Campania='"+c+"' and idM="+idM+" and nombre='"+canal+"';");
		for (String[] strings : lista2) {
			String vM="";
			String vI="";
			String vF="";
			String aux[]= new String[3];
			if(strings[3]==null){
				if (strings[0] != null) {
					 vM=strings[0];
				}
				if (strings[1] != null) {
					 vI=strings[1];
				}
				if (strings[2] != null) {
					 vF=strings[2];
				}
			}else{
				if (strings[0] != null) {
					 vM=strings[0]+" "+strings[3];
				}
				if (strings[1] != null) {
					vI=strings[1]+" "+strings[3];
				}
				if (strings[2] != null) {
					vF=strings[2]+" "+strings[3];
				}
			}
			aux[0]=vM;
			aux[1]=vI;
			aux[2]=vF;
			lista.add(aux);
		}
		return lista;
	}
	public Canal(String mod, String camp, int med, String nom) {
		Integer n = Integer.parseInt(myBD.selectEscalar("SELECT COUNT(*) FROM Canal WHERE "
				+ " ModuloNombre='"+mod+"' and Campania='"+camp+"' and idM="+idM+" and nombre='"+nombre+
				"';"));
		if(n>0){
			nombre=nom;
			modulo=mod;
			idM=med;
			campania=camp;
			id= Integer.parseInt(myBD.selectEscalar("SELECT id FROM  Canal WHERE "
				+ " ModuloNombre='"+mod+"' and Campania='"+camp+"' and idM="+idM+" and nombre='"+nombre+
				"';"));
			actualizar();
		}else{
			nombre=nom;
			modulo=mod;
			idM=med;
			campania=camp;
			try{
				id = 1 + Integer.parseInt(myBD.selectEscalar("SELECT MAX(id) FROM Canal")); 
			}catch(Exception e){
				id=0;
			}
			myBD.insert("INSERT INTO Canal (id, Campania, ModuloNombre,idM,nombre) "
					+ " VALUES ("+id+",'"+campania+"', '"+modulo+"', "+idM+", '"+nombre+"');");
		}
	}
	private void actualizar(){
		 String Svalormedio=myBD.selectEscalar("SELECT valorM FROM Canal WHERE id = " + id+" ;");
		 String Svalorinicial=myBD.selectEscalar("SELECT valorI  FROM Canal WHERE id = " + id+" ;");
		 String Svalorfinal=myBD.selectEscalar("SELECT valorF   FROM Canal WHERE id = " + id+" ;");
		 unidad=myBD.selectEscalar("SELECT unidad  FROM Canal WHERE id = " + id+" ;");
		 if(Svalormedio!=null){
			 valormedio=Double.parseDouble(Svalormedio);
		 }
		 if(Svalorinicial!=null){
			 valorinicial=Double.parseDouble(Svalorinicial);
		 }
		 if(Svalorfinal!=null){
			 valorfinal=Double.parseDouble(Svalorfinal);
		 }
	}
	public void update( Double valormedio, Double valorinicial, Double valorfinal, String unidad){
		if(valormedio!=null){
			this.valormedio=valormedio;
			myBD.update("UPDATE Canal SET valorM="+this.valormedio+" WHERE id="+id+";");
		}
		if (valorinicial != null) {
			this.valorinicial=valorinicial;
			myBD.update("UPDATE Canal SET valorI="+this.valorinicial+" WHERE id="+id+";");
		}
		if (valorfinal != null) {
			this.valorfinal=valorfinal;
			myBD.update("UPDATE Canal SET valorF="+this.valorfinal+" WHERE id="+id+";");
		}
		if(unidad!=null){
			this.unidad=unidad;
			myBD.update("UPDATE Canal SET unidad='"+this.unidad+"' WHERE id="+id+";");
		}
	}
}
