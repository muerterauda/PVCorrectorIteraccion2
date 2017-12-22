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
		IMedida res = new Medida((Medida) super.med);
		res.setParametrosCorreccion(t2, g2);
		List<double[]> ps = super.med.generarCurvaIV();
		try {
			List<String> parametros = res.getParametrosCorreccion();
			float tc = Float.parseFloat(parametros.get(0));
			float gc = Float.parseFloat(parametros.get(1));
			
			StringBuilder st = new StringBuilder();
			StringBuilder sc = new StringBuilder();
			StringBuilder sp = new StringBuilder();
			
			for (int i = 0; i < ps.size(); i++) {
				double t1 = ps.get(i)[0];
				double c1 = ps.get(i)[1];
				double t2;
				double c2;
				
				c2 = c1 + super.Isc*(gc/super.g1 -1) + super.alfa*(tc - super.t1);
				t2 = t1 - super.rs*(c2 - c1) - super.k*c2*(tc - super.t1) + super.beta*(tc-super.t1);
				
				st.append(t2 + " ");
				sc.append(c2 + " ");
				sp.append(t2*c2 + " ");
			}
			
			Punto p = new Punto(st.toString(), sc.toString(), sp.toString(), res.getId());
		}catch(Exception e) {
			
		}
		
		return res.getId() + " Temperatura Objetivo: " + super.t2 + " Irradiancia Objetivo: " + super.g2;
	}

}
