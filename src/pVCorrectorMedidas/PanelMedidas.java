package pVCorrectorMedidas;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class PanelMedidas  extends JPanel implements VistaMedidas{
	private JLabel mensajeJL;
	private JPanel panelA;
	private JPanel panelB;
	private JLabel moduloJL;
	private JLabel columnasJL[];
	private JButton filasJB[];
	private JLabel[][] medidasmetJL;
	private JScrollPane scrollA;
	public PanelMedidas(String modulo, List<IMedida>lista) {
		inicializar(modulo, lista);
	}
	private void inicializar(String modulo, List<IMedida> lista) {
		panelA= new JPanel();
		panelB = new JPanel();
		mensajeJL = new JLabel("");
		int filas=0;
		List<String> nombres2 = null;
		if(lista!=null&&lista.size()!=0){
			filas=lista.size();
			nombres2=lista.get(0).listacanalesM();
		}
		if(nombres2==null){
			columnasJL= new JLabel[nombres.length];
		}else{
			columnasJL= new JLabel[nombres.length+nombres2.size()*3];
		}
		filasJB= new JButton[filas];
		medidasmetJL= new JLabel[filasJB.length][columnasJL.length];
		panelA.setLayout(new GridLayout(filasJB.length+1, columnasJL.length+1));
		moduloJL= new JLabel(modulo);
		moduloJL.setHorizontalAlignment(SwingConstants.CENTER);
		panelA.add(moduloJL);
		cargarNombres(filas, nombres2);
		cargarValores(lista, nombres2);

		scrollA = new JScrollPane(panelA);
		scrollA.setPreferredSize(new Dimension(900,(int)scrollA.getPreferredSize().getHeight()+50));
        scrollA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelB.add(mensajeJL);
        try{
            JScrollBar barra=scrollA.getVerticalScrollBar();
            barra.setUnitIncrement(20);
        }catch(Exception e){
        }
		setLayout(new BorderLayout());
		add(scrollA, BorderLayout.CENTER);
		add(panelB, BorderLayout.SOUTH);
	}
	private void cargarValores(List<IMedida> lista, List<String> nombres2) {
		for (int i = 0; i < filasJB.length; i++) {
			filasJB[i]= new JButton("Ver Grafica");
			panelA.add(filasJB[i]);
			IMedida x=null;
			List<String>listavar = null;
			List<String[]> canalesM=null;
			if(lista!=null){
				x=lista.get(i);
				listavar=x.getVars();
			}
			for (int j = 0; j < nombres.length; j++) {
				String b="";
				medidasmetJL[i][j]=new JLabel();
				if(listavar!=null){
					b=listavar.get(j);
					medidasmetJL[i][j].setText(b);
				}
				medidasmetJL[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				panelA.add(medidasmetJL[i][j]);
			}
			int h=0;
			for (int j = nombres.length; j < columnasJL.length;j= j+3) {
				String[] b=null;
				try{
				if(nombres2.size()!=0){
					canalesM=Canal.getMedidasModulo(x.getId(), x.getModulo(), x.getCampania(), nombres2.get(h));
				}
				b=canalesM.get(0);
				medidasmetJL[i][j]=new JLabel();
				medidasmetJL[i][j].setText(b[0]);
				medidasmetJL[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				medidasmetJL[i][j+1]=new JLabel();
				medidasmetJL[i][j+1].setText(b[1]);
				medidasmetJL[i][j+1].setHorizontalAlignment(SwingConstants.CENTER);
				medidasmetJL[i][j+2]=new JLabel();
				medidasmetJL[i][j+2].setText(b[2]);
				medidasmetJL[i][j+2].setHorizontalAlignment(SwingConstants.CENTER);
				panelA.add(medidasmetJL[i][j]);
				panelA.add(medidasmetJL[i][j+1]);
				panelA.add(medidasmetJL[i][j+2]);
				h++;
			}catch(Exception e){
				medidasmetJL[i][j]=new JLabel();
				medidasmetJL[i][j].setText("");
				medidasmetJL[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				medidasmetJL[i][j+1]=new JLabel();
				medidasmetJL[i][j+1].setText("");
				medidasmetJL[i][j+1].setHorizontalAlignment(SwingConstants.CENTER);
				medidasmetJL[i][j+2]=new JLabel();
				medidasmetJL[i][j+2].setText("");
				medidasmetJL[i][j+2].setHorizontalAlignment(SwingConstants.CENTER);
				panelA.add(medidasmetJL[i][j]);
				panelA.add(medidasmetJL[i][j+1]);
				panelA.add(medidasmetJL[i][j+2]);
				h++;
			}
			}
		}
	}
	private void cargarNombres(int filas, List<String> nombres2) {
		if(filas!=0){
			for (int i = 0; i < nombres.length; i++) {
				columnasJL[i]= new JLabel(VistaMedidas.nombres[i]);
				columnasJL[i].setHorizontalAlignment(SwingConstants.CENTER);
				panelA.add(columnasJL[i]);
			}
			int h=0;
			for (int i = nombres.length; i < columnasJL.length; i=i+3) {
				columnasJL[i]= new JLabel(nombres2.get(h)+" Media");
				columnasJL[i].setHorizontalAlignment(SwingConstants.CENTER);
				columnasJL[i+1]= new JLabel(nombres2.get(h)+" Inicial");
				columnasJL[i+1].setHorizontalAlignment(SwingConstants.CENTER);
				columnasJL[i+2]= new JLabel(nombres2.get(h)+" Final");
				columnasJL[i+2].setHorizontalAlignment(SwingConstants.CENTER);
				panelA.add(columnasJL[i]);
				panelA.add(columnasJL[i+1]);
				panelA.add(columnasJL[i+2]);
				h++;
			}
		}
	}
	@Override
	public void mensaje(String msg, Color j) {
		mensajeJL.setForeground(j);
		mensajeJL.setText(msg);
	}
 
	@Override
	public void controlador(ActionListener ctrmenu) {
		for (int i = 0; i < filasJB.length; i++) {
			filasJB[i].setActionCommand(medidasmetJL[i][0].getText()+"\n"+medidasmetJL[i][1].getText()+"\n"+medidasmetJL[i][2].getText()+"\n"+moduloJL.getText());
			filasJB[i].addActionListener(ctrmenu);
		}
	}

}
