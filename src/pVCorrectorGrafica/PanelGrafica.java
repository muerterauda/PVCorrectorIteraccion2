package pVCorrectorGrafica;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import estructurasDatos.Tuple2;
import pVCorrectorMedidas.IMedida;
import pVCorrectorMedidas.Medida;

public class PanelGrafica  extends JPanel implements VistaGraficas{
	private IMedida med;
	private JLabel mensajeJL;
	private JComboBox<String> procedimientos;
	private JButton aplicarProcedimiento;
	private JPanel panelB;
	private JPanel panelC;
	private JPanel panelD;
	private JScrollPane scrollA;
	private ChartPanel grafica;
	private DefaultListModel <String> modeloGraficasCorregidas;
	private JList<String> graficasCorregidas;
	private double[] curvaX;
	private double[] curvaY;
	private XYDataset ds;
	private DefaultXYDataset dsdef;
	private JFreeChart chart;
	private List<IMedida>listaCorrecciones;
	private static InputStream  ficheroProcedimientos=PanelGrafica.class.getResourceAsStream("/procedimientos.txt");
	public PanelGrafica(List<double[]>curva,IMedida idM) { 
		med= idM;
		curvaX= new double[curva.size()];
		curvaY= new double[curva.size()];
		int i=0;
		for (double[] ds : curva) {
			curvaX[i]=ds[0];
			curvaY[i]=ds[1];
			i++;
		}
		ds= createDataset();
		chart =ChartFactory.createXYLineChart(med.getModulo(),
				                "V(Voltios)","I(Amperios)",  ds, PlotOrientation.VERTICAL, true, true,
				                false);
		grafica= new ChartPanel(chart);
		grafica.updateUI();
		aplicarProcedimiento= new JButton("Aplicar");
		panelB = new JPanel();
		panelC= new JPanel();
		panelD= new JPanel();
		mensajeJL = new JLabel("");
		String[] metodos=leer();
		procedimientos= new JComboBox<String>(metodos);
		crearJlistCurvasCorregidas();
		panelB.add(mensajeJL);
		panelC.setLayout(new BorderLayout());
		panelC.add(aplicarProcedimiento, BorderLayout.EAST);
		panelC.add(procedimientos, BorderLayout.CENTER);
		panelD.setLayout(new BorderLayout());
		scrollA= new JScrollPane(graficasCorregidas);
		scrollA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelD.add(scrollA);
		panelD.add(panelB, BorderLayout.SOUTH);
		setLayout(new BorderLayout());
		add(grafica,BorderLayout.CENTER);
		add(panelC,BorderLayout.NORTH);
		add(panelD, BorderLayout.SOUTH);
	}
	private void crearJlistCurvasCorregidas() {
		modeloGraficasCorregidas= new DefaultListModel<String>();
		listaCorrecciones=med.getMedidasCorregidas();
		for(IMedida k: listaCorrecciones) {
			List<String> listaParametrosCorrecion=k.getParametrosCorreccion();
			String n=k.getId()+" Temperatura objetivo: "+listaParametrosCorrecion.get(0)+" Irradiancia objetivo: "+listaParametrosCorrecion.get(1);
			modeloGraficasCorregidas.addElement(n);
		}
		graficasCorregidas= new JList<String>(modeloGraficasCorregidas);
		graficasCorregidas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		DefaultListCellRenderer cellRenderer = (DefaultListCellRenderer)graficasCorregidas.getCellRenderer();
//		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	}
	private String[] leer() {
		List<String> lista= new LinkedList<String>();
		Scanner sc= new Scanner(ficheroProcedimientos);
		while (sc.hasNextLine()) {
			lista.add((String) sc.nextLine());
		}
		sc.close();
		return lista.toArray(new String[lista.size()]);
	}
	@Override
	public void mensaje(String msg, Color j) {
		mensajeJL.setForeground(j);
		mensajeJL.setText(msg);
	}
 
	@Override
	public void controlador(ActionListener ctrmen, ListSelectionListenerGraficas ctrlist) {
		aplicarProcedimiento.setActionCommand(VistaGraficas.AplicarPorcedimiento);
		aplicarProcedimiento.addActionListener(ctrmen);
		graficasCorregidas.addListSelectionListener(ctrlist);
	}
	private XYDataset createDataset() {

		dsdef = new DefaultXYDataset();

        double[][] data = { curvaX, curvaY};
        dsdef.addSeries("Medida", data);

        return dsdef;
    }
	private void nuevaGrafica(String nombre,double[] x, double[] y) {
		double[][] data = {x,y};
		dsdef.addSeries(nombre, data);
	}
	private void borrarGrafica(String x) {
		try {
			if(x!=null)
				dsdef.removeSeries(x);
			else
				dsdef.removeSeries("Medida");
		}catch(Exception e) {
			
		}
	}
	@Override
	public int getMetodoSelected() {
		return  procedimientos.getSelectedIndex();
	}
	public String getMetodoSelect() {
		return  (String)procedimientos.getSelectedItem();
	}
	@Override
	public void añadirGrafica(String v) {
		modeloGraficasCorregidas.addElement(v);
		listaCorrecciones.add(new Medida(Integer.parseInt(v.split(" ")[0])));
	}
	@Override
	public Tuple2<String,String> seleccionarParametros() {
		Metodo1Panel panel=new Metodo1Panel();
		Tuple2<String,String> tuple=null;
		try {
		JOptionPane.showMessageDialog(null, panel, "Selecciona parametros para la correcion", JOptionPane.DEFAULT_OPTION);
		String x=panel.getTemperatura();
		String y=panel.getIrradiancia();
		tuple= new Tuple2<String,String>(x, y);
		}catch(Exception e) {
			
		}
		return tuple;
	}
	@Override
	public void actualizarGraficas() {
		List<String>listact=graficasCorregidas.getSelectedValuesList();
		borrar();
		for(String e: listact) {
			int i=Integer.parseInt(e.split(" ")[0]);
			boolean encontrado=false;
			int j=0;
			while(!encontrado) {
				if(i==listaCorrecciones.get(j).getId()) {
					encontrado=true;
				}else {
					j++;
				}
			}
			List<double[]> lis=listaCorrecciones.get(j).generarCurvaIV();
			nuevaGrafica("Grafica "+i,lis.get(0), lis.get(1));
		}
		grafica.updateUI();
	}
	private void borrar() {
		for(IMedida m: listaCorrecciones) {
			borrarGrafica("Grafica "+m.getId());
		}
	}
}
