package lector_de_ficheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.SingleSelectionModel;

import org.omg.Messaging.SyncScopeHelper;

import pVCorrectorMedidas.Canal;
import pVCorrectorMedidas.Medida;
import pVCorrectorMedidas.Punto;
import pVCorrectorModulos2.Campanya;
import pVCorrectorModulos2.Modulo;

public class LectorArchivo {
	private File fileO;
	private String modulo;
	private Medida medida;
	private Canal canal;

	public LectorArchivo(File fileName) {
		fileO = fileName;
	}

	public void leer() throws IOException {
		Scanner sc = new Scanner(fileO);
		String s = sc.nextLine();
		sc.close();
		
		if (s.contains("Módulo:")) {
			leermedida();
		} else {
			leermodulo();
		}
	
	}

	private void leermedida() throws FileNotFoundException {
		Scanner sc = new Scanner(fileO);
		List<String> lista = new LinkedList<String>();
		List<String> listaVars = new LinkedList<String>();
		List<String[]> listaDats = new LinkedList<String[]>();
		String[] lineas;
		String linea = null;
		
		
		StringBuilder t = new StringBuilder();
		StringBuilder c = new StringBuilder();
		StringBuilder p = new StringBuilder();
		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			linea = linea.replaceAll("[(\t)]+", " ");
			linea = linea.replaceAll("[(' ')]+", " ");
			linea = linea.trim();
			lista.add(linea);
		}
		lista.add("");
		sc.close();
		try {
			for (int i = 0; i <= 3; i++) {
				lineas = lista.get(i).split(": ");
				listaDats.add(lineas);
			}
			modulo = listaDats.get(0)[1];
			String campaniaM = listaDats.get(1)[1];
			String fechaM = listaDats.get(2)[1];
			String horaM = listaDats.get(3)[1];
			new Modulo(modulo);
			
			new Campanya(campaniaM,modulo, fechaM, fechaM);
			
			medida = new Medida(campaniaM, fechaM, horaM, modulo);
			if (!medida.getExiste()) {

				for (int i = 6; i <= 11; i++) {// añadimos las 5 variables // empieza en la posicion 6 lasvariables
					lineas = lista.get(i).split(": ");
					listaVars.add(lineas[1]);
				}
				medida.añadirVars(listaVars);
				String nombre = null;
				String unidad = null;
				Double valorMedio = null;
				Double valorInicial = null;
				Double valorFinal = null;
				String[] aux = null;
				String[] lineas3 = null;
				int i = 0; // contador de linea para los canales y puntos ya que a partir de aquí no
							// siempre es el mismo formato
				while (!(lista.get(i + 13).equals(""))) {// empieza en laposicion 13 las variables, puede q no todos los
															// archivos estén con el mismo
					try {
						lineas = lista.get(i + 13).split(": "); // numero de canales, por eso es hasta que llega a una
																// linea en blanco
						if (lineas.length >= 1) {
							aux = lineas[1].split(" ");
							if (aux.length == 2)
								lineas3 = new String[] { lineas[0], aux[0], aux[1] };
							if (aux.length == 1)
								lineas3 = new String[] { lineas[0], aux[0] };
						}

						if (Pattern.matches("[[^ ]+ ]+: [0-9-,-.]+ [^ ]+($)", lista.get(i + 13)) && i % 3 == 0) {
							nombre = lineas3[0];
							valorMedio = Double.parseDouble(lineas3[1].replaceAll(",", "."));
							unidad = lineas3[2]; // primera linea contiene unidad

						} else if (Pattern.matches("[[^ ]+ ]+: [0-9-,-.]+($)", lista.get(i + 13)) && i % 3 == 0) {
							nombre = lineas3[0];
							valorMedio = Double.parseDouble(lineas3[1].replaceAll(",", "."));

						} else if (Pattern.matches("[[^ ]+ ]+: [^ ]+($)", lista.get(i + 13)) && i % 3 == 0) {
							nombre = lineas3[0];
							unidad = lineas3[1];

						} else if ((Pattern.matches("[[^ ]+ ]+: [0-9-,-.]+ [^ ]+($)", lista.get(i + 13)) && i % 3 == 1)
								|| (Pattern.matches("[[^ ]+ ]+: [0-9-,-.]+($)", lista.get(i + 13)) && i % 3 == 1)) {
							valorInicial = Double.parseDouble(lineas3[1].replaceAll(",", "."));

						} else if ((Pattern.matches("[[^ ]+ ]+: [0-9-,-.]+ [^ ]+($)", lista.get(i + 13)) && i % 3 == 2)
								|| (Pattern.matches("[[^ ]+ ]+: [0-9-,-.]+($)", lista.get(i + 13)) && i % 3 == 2)) {
							valorFinal = Double.parseDouble(lineas3[1].replaceAll(",", "."));
						}
						i++;
						if (i % 3 == 0) {
							canal = new Canal(modulo, campaniaM, medida.getId(), nombre);
							canal.update(valorMedio, valorInicial, valorFinal, unidad);
							valorMedio = null;
							valorInicial = null;
							valorFinal = null;
							nombre = null;
						}
					} catch (Exception e) {
						valorMedio = null;
						valorInicial = null;
						valorFinal = null;
						nombre = null;
						i += 3 - i % 3;
					}
				}
				i = i + 3;
				String tension = null, corriente = null, potencia = null;
				while (!((lista.get(i + 13).equals("")))) { // mientras siga habiendo puntos
					lineas = lista.get(i + 13).split(" ");
					try {
						if ((lineas.length == 4)) {
							tension = lineas[1].replaceAll(",", ".");
							corriente = lineas[2].replaceAll(",", ".");
							potencia = lineas[3].replaceAll(",", ".");
							t.append(tension + " ");
							c.append(corriente+ " ");
							p.append(potencia + " ");
						}
						if ((lineas.length == 3)) {
							tension = lineas[1].replaceAll(",", ".");
							corriente = lineas[2].replaceAll(",", ".");
							potencia = null;
							t.append(tension+ " ");
							c.append(corriente + " ");
							p.append(" ");
						}
					} catch (Exception e) {
					}
					i++;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Punto(t.toString(), c.toString(), p.toString(), medida.getId());
	}

	private void leermodulo() throws FileNotFoundException {
		Scanner sc = new Scanner(fileO);
		List<String> lista = new LinkedList<String>();
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			lista.add(linea);
		}
		sc.close();
		try {
			modulo = lista.get(0);
			String alfa = lista.get(14);
			String beta = lista.get(16);
			String gamma = lista.get(18);
			String kappa = lista.get(20);
			String rs = lista.get(lista.size()-7);
			if (lista.get(15) == "-1") {
				alfa = alfa + " mA/ï¿½C";
			}
			if (lista.get(17) == "-1") {
				beta = beta + " mV/ï¿½C";
			}
			if (lista.get(19) == "-1") {
				gamma = gamma + " mW/ï¿½C";
			}
			if (lista.get(21) == "-1") {
				kappa = kappa + " mOhm/ï¿½C";
			}

			//new Modulo(modulo, alfa, beta, gamma, kappa, rs);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public String getModulo() {
		return modulo;
	}
}
