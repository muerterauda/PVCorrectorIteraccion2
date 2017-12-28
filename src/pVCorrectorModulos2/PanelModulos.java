package pVCorrectorModulos2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PanelModulos extends JPanel implements VistaModulos{
	private JLabel mensajeJL;
	private boolean relleno=false;
	private JPanel panelA;
	private JScrollPane scrollA;
	private JPanel panelB;
	private JPanel panelC;
	private JScrollPane scrollB;
	private JPanel panelD;
	private JPanel panelE;
	private JPanel panelF;
	private JPanel panelG;
	private JPanel panelH;
	private JLabel Titulo;
	private List <JCheckBox > modulos;
	private List <JCheckBox > campanyas;
	private JButton borrarJB;
	private JButton editarJB;
	private JButton importarJB;
	private JButton verMedidasJB;
	private JLabel moduloJL;
	private JFrame frame;
	private Imagen uma;
	private Imagen pyro;
	public PanelModulos(List<String> valores, JFrame frame) {
		this.frame = frame;
		crearComponentes(valores);
		panelA.add(moduloJL);
		if (valores != null) {
			for (String st : valores) {
				JCheckBox modulo = new JCheckBox(st);
				modulos.add(modulo);
				panelA.add(modulo);
			}

		}
		uma = new Imagen("/uma.jpg");
		pyro= new Imagen("/pyro.jpg");
		Titulo.setFont(new Font("Serif", Font.PLAIN, 30));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelG.add(Titulo);
		panelB.add(mensajeJL);
		panelE.add(importarJB);
		panelE.add(verMedidasJB);
		panelE.add(editarJB);
		panelE.add(borrarJB);
		panelDImagenes();
		panelF.setLayout(new BorderLayout());
		panelF.add(moduloJL, BorderLayout.WEST);
		panelH.setLayout(new BorderLayout());
		panelH.add(panelD, BorderLayout.CENTER);
		panelH.add(panelF, BorderLayout.SOUTH);
		setLayout(new BorderLayout());
		scrollA = new JScrollPane(panelA);
		scrollA.setMinimumSize(new Dimension(500, 500));
		scrollA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollA, BorderLayout.CENTER);
		add(panelB, BorderLayout.SOUTH);
		add(panelH, BorderLayout.NORTH);
		colorearBordear();
	}
	private void panelDImagenes() {
		GridBagConstraints c;
		JPanel panelI = panelIunionPaneles();
		panelD.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.ipadx= 100;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight=2;
		c.gridwidth=2;
		c.fill= GridBagConstraints.BOTH;
		panelD.add(uma, c);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight=2;
		c.gridwidth=4;
		panelD.add(panelI, c);
		c.gridx = 7;
		c.gridy = 0;
		c.gridheight=2;
		c.gridwidth=2;
		c.fill = GridBagConstraints.BOTH;
		panelD.add(pyro, c);
	}
	private JPanel panelIunionPaneles() {
		JPanel panelI= new JPanel();
		panelI.setBackground(VistaModulos.FONDO);
		panelI.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight=1;
		c.gridwidth=4;
		panelI.add(panelG, c);
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight=1;
		c.gridwidth=4;
		panelI.add(panelE, c);
		return panelI;
	}
	private void crearComponentes(List<String> valores) {
		panelA = new JPanel();
		if (valores != null) {
			panelA.setLayout(new GridLayout(valores.size() + 1, 1));
		} else {
			panelA.setLayout(new GridLayout(1, 1));
		}
		panelB = new JPanel();
		panelD = new JPanel();
		panelE = new JPanel();
		panelF = new JPanel();
		panelG = new JPanel();
		panelH = new JPanel();
		mensajeJL = new JLabel("");
		moduloJL = new JLabel("     MODULOS");
		moduloJL.setHorizontalAlignment(SwingConstants.RIGHT);
		borrarJB = new JButton("Borrar");
		editarJB = new JButton("Editar");
		importarJB = new JButton("Importar");
		verMedidasJB = new JButton("Ver medidas");
		modulos = new LinkedList<JCheckBox>();
		Titulo= new JLabel("PVCORRECTOR");
	}
	private void colorearBordear() {
		for(JCheckBox modulo: modulos){
			modulo.setBackground(VistaModulos.FONDO);
		}
		panelA.setBackground(VistaModulos.FONDO);
		panelB.setBackground(VistaModulos.FONDO);
		panelD.setBackground(VistaModulos.FONDO);
		panelE.setBackground(VistaModulos.FONDO);
		panelF.setBackground(VistaModulos.FONDO);
		panelG.setBackground(VistaModulos.FONDO);
        scrollA.setBackground(VistaModulos.FONDO);
        scrollA.setBorder(null);
		setBackground(VistaModulos.FONDO);
	}
	@Override
	public void mensaje(String msg, Color j) {
		mensajeJL.setForeground(j);
		mensajeJL.setText(msg);
	}
 
	@Override
	public void controlador(ActionListener ctrmodulos) {
		int i=0;
		for(JCheckBox modulo: modulos){
			modulo.setActionCommand(Integer.toString(i));
			modulo.addActionListener(ctrmodulos);
			i++;
		}

		borrarJB.setActionCommand(VistaModulos.BORRAR);
		borrarJB.addActionListener(ctrmodulos);
		verMedidasJB.setActionCommand(VistaModulos.VER);
		verMedidasJB.addActionListener(ctrmodulos);
		importarJB.setActionCommand(VistaModulos.IMPORTAR);
		importarJB.addActionListener(ctrmodulos);
		editarJB.setActionCommand(VistaModulos.EDITAR);
		editarJB.addActionListener(ctrmodulos);
	}
	@Override
	public void setcampanyas(List<ICampanya> lista) {
		if(scrollB!=null) {
			frame.remove(scrollB);
			campanyas=null;
			if(panelC!=null)
				frame.remove(panelC);
			relleno=false;
		}	
		if(lista!=null) {
			panelC= new JPanel();
			panelC.setLayout(new GridLayout(lista.size(), 1));
			panelC.setBackground(FONDO);
			campanyas=new LinkedList<JCheckBox>();
			for(ICampanya campan: lista) {
				JCheckBox camp= new JCheckBox(campan.getNombre()+"-> Inicio:"+campan.getFechaInit()+", Fin:"+campan.getFechaFin());
				camp.setBackground(FONDO);
				panelC.add(camp);
				campanyas.add(camp);
			}
			if(scrollB==null) {
				scrollB= new JScrollPane(panelC);
				scrollB.setMinimumSize(new Dimension(500, 500));
				scrollB.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollB.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollB.setBackground(FONDO);
				scrollB.setBorder(null);
			}else {
				scrollB.add(panelC);
			}
			frame.add(scrollB, BorderLayout.EAST);
			relleno=true;
		}
	}

	@Override 
	public void borrarmodulos() {
		int h=0;
		List<Integer>listaborrar= new LinkedList<>();
		for(int i=0; i<modulos.size(); i++){
			JCheckBox modulo= modulos.get(i);
			if(modulo.isSelected()){
				listaborrar.add(i-h);
				panelA.remove(modulo);
				modulos.remove(modulo);
				h++;
			}else{
				modulo.setActionCommand(Integer.toString(Integer.parseInt(modulo.getActionCommand())-h));
			}
		}
		limpiarcampanyas();
		updateUI();
	}
	private void limpiarcampanyas() {
		if(relleno){
			campanyas=null;
			frame.remove(scrollB);
			scrollB=null;
		}
        relleno=false;
	}
	
	public int modulomarcados() {
		int j=0;
		for(int i=0; i<modulos.size(); i++){
			if(modulos.get(i).isSelected()){
				j++;
			}
		}
		return j;
	}
	@Override
	public int getMarcado() {
		int j=-1;
		for(int i=0; i<modulos.size(); i++){
			if(modulos.get(i).isSelected()){
				j=i;
				break;
			}
		}
		return j;
	}
	@Override
	public boolean marcado(int n) {
		return modulos.get(n).isSelected();
	}
	@Override
	public File importar() {
		JFileChooser fichero= new JFileChooser();
		int i=fichero.showOpenDialog(this);
		if(i==JFileChooser.APPROVE_OPTION){
			return fichero.getSelectedFile();
		}else{
			return null;
		}
	}
	@Override
	public String ModuloMarcado(int n) {
		return modulos.get(n).getText();
	}
	@Override
	public void importar(String c,ActionListener k) {
		boolean esta=esta(c);
		if(!esta){
		JCheckBox modulonuevo=new JCheckBox(c);
		modulonuevo.setBackground(FONDO);
		modulos.add(modulonuevo);
		modulonuevo.setActionCommand(Integer.toString(modulos.size()));
		modulonuevo.addActionListener(k);
		panelA.removeAll();
		panelA.setLayout(new GridLayout(modulos.size(), 1));
		for(JCheckBox x:modulos){
			panelA.add(x);
		}
		}
		limpiarcampanyas();
		panelA.validate();
	}
	private boolean esta(String c) {
		boolean encontrado=false;
		for(JCheckBox b: modulos){
			if(c.equals(b.getText())){
				encontrado=true;
				break;
			}
		}
		return encontrado;
	}
	@Override
	public List<String> modulosSeleccionados() {
		List<String> lista= new LinkedList<String>();
		for(int i=0; i<modulos.size(); i++){
			JCheckBox modulo= modulos.get(i);
			if(modulo.isSelected()){
				lista.add(modulo.getText());
			}
		}
		return lista;
	}
	@Override
	public int getCampaSelecc() {
		int numCam=0;
		if(scrollB!=null&&panelC!=null&&campanyas!=null) {
			for (JCheckBox jCheckBox : campanyas) {
				if(jCheckBox.isSelected()) {
					numCam++;
				}
			}
		}
		return numCam;
	}
	@Override
	public List<String> getCampanyas() {
		List<String>lista= new LinkedList<String>();
		if(scrollB!=null&&panelC!=null&&campanyas!=null) {
			for (JCheckBox jCheckBox : campanyas) {
				if(jCheckBox.isSelected()) {
					lista.add(jCheckBox.getText().split("->")[0]);
				}
			}
		}
		return lista;
	}

}
