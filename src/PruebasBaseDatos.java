import java.util.Arrays;

import base_de_datos.BD;

public class PruebasBaseDatos {

	public static void main(String[] args) {
		BD base= BD.getInstance();
		System.out.println(Arrays.toString(base.select("Select * from Medida;").get(0)));
		System.out.println(Arrays.toString(base.select("Select * from Medida;").get(1)));
	}

}
