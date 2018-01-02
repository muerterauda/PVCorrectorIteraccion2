package correcciones;

import java.util.List;

import pVCorrectorMedidas.Canal;
import pVCorrectorMedidas.IMedida;
import pVCorrectorModulos2.IModulo;
import pVCorrectorModulos2.Modulo;

public abstract class MetCorrecion {
	IMedida med;
	IModulo mod;
	
	protected float alfa;
	protected float beta;
	protected float rs;
	protected float k;
	protected float t1;
	protected float g1;
	protected String t2;
	protected String g2;
	protected float Isc;
	protected float Voc;
	static final protected float a = (float) 0.06;
	
	public MetCorrecion(IMedida medida, String t, String g) {
		med = medida;
		
		mod = new Modulo(medida.getModulo());
		
		alfa = partirString(mod.getAlfa())/1000;

		beta = partirString(mod.getBeta())/1000;
		
		rs = partirString(mod.getRs())/1000;
		
		Isc = partirString(med.getIsc());
		
		Voc = partirString(med.getVoc());
		
		obtenerValoresIniciales();
		
		t2 = t;
		
		g2 = g;
		
		k = rs/t1;
	}

	protected float partirString(String s) {
		String[] aux = s.split(" ");
		aux[0] = aux[0].replaceAll(",", ".");
		float res = Float.parseFloat(aux[0]);
		
		return res;
	}
	
	private void obtenerValoresIniciales() {
		List<String[]> lt = Canal.getMedidasModulo(med.getId(), mod.getNombre(), med.getCampania(), "Temperatura Ambiente");
		List<String[]> lg = Canal.getMedidasModulo(med.getId(), mod.getNombre(), med.getCampania(), "Piranómetro Seguidor");
		
		t1 = partirString(lt.get(0)[0]);
		g1 = partirString(lg.get(0)[0]);
	}
	
	abstract public String aplicarMetodo();
}
