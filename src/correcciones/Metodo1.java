package correcciones;

import java.util.List;

import pVCorrectorMedidas.IMedida;
import pVCorrectorMedidas.Medida;
import pVCorrectorMedidas.Punto;

public class Metodo1 extends MetCorrecion{

	public Metodo1(IMedida medida, String t, String g) {
		super(medida, t, g);
	}

	@Override
	public String aplicarMetodo() {
		float tc = Float.parseFloat(t2);
		float gc = Float.parseFloat(g2);
		IMedida res = new Medida((Medida) super.med);
		res.setParametrosCorreccion(t2, g2, "Metodo 1 de la norma IEC-60891");
		List<double[]> ps = super.med.generarCurvaIV();
		try {

			StringBuilder st = new StringBuilder();
			StringBuilder sc = new StringBuilder();
			StringBuilder sp = new StringBuilder();
			
			for (int i = 0; i < ps.size(); i++) {
				double v1 = ps.get(i)[0];
				double c1 = ps.get(i)[1];
				double v2;
				double c2;
				
				c2 = c1 + super.Isc*(gc/super.g1 -1) + super.alfa*(tc - super.t1);
				v2 = v1 - (super.rs*(c2 - c1) - super.k*c2*(tc - super.t1) + super.beta*(tc-super.t1));
				
				st.append(v2 + " ");
				sc.append(c2 + " ");
				sp.append(v2*c2 + " ");
			}
			
			new Punto(st.toString(), sc.toString(), sp.toString(), res.getId());
		}catch(Exception e) {
			
		}
		
		return res.getId() + " Temperatura Objetivo: " + super.t2 + " Irradiancia Objetivo: " + super.g2+" Metodo de Correccion: "+"Metodo 1 de la norma IEC-60891";
	}

}
