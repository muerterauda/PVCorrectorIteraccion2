package pVCorrectorModulos2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

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
	private List <JCheckBox > modulos;
	private List <JCheckBox > campanyas;
	private JButton borrarJB;
	private JButton editarJB;
	private JButton importarJB;
	private JButton verMedidasJB;
	private JLabel moduloJL;
	private JFrame frame;
//	private Map<String, List<String>> valores1;
	public PanelModulos(List<String> valores, JFrame frame) {
		this.frame=frame;
//		valores1=valores;
		panelA= new JPanel();
		if(valores!=null){
			panelA.setLayout(new GridLayout(valores.size()+1, 1));
		}else{
			panelA.setLayout(new GridLayout(1, 1));
		}
		panelB = new JPanel();
		panelD= new JPanel();
		panelE= new JPanel();
		panelF= new JPanel();
		mensajeJL = new JLabel("");
		moduloJL= new JLabel("     MODULOS");
		moduloJL.setHorizontalAlignment(SwingConstants.RIGHT);
		borrarJB= new JButton("Borrar");
		editarJB= new JButton("Editar");
		importarJB= new JButton("Importar");
		verMedidasJB= new JButton("Ver medidas");
		modulos= new LinkedList<JCheckBox>();
		panelA.add(moduloJL);
		if(valores!=null){
		for(String st: valores){
			JCheckBox modulo= new JCheckBox(st);
			modulos.add(modulo);
			panelA.add(modulo);
		}
		
	}
		panelB.add(mensajeJL);
		panelD.setLayout(new BorderLayout());
		panelE.add(importarJB);
		panelE.add(verMedidasJB);
		panelE.add(editarJB);
		panelE.add(borrarJB);
		panelD.add(panelE, BorderLayout.CENTER);
		panelF.setLayout(new BorderLayout());
		panelF.add(moduloJL,BorderLayout.WEST);
		panelD.add(panelF, BorderLayout.SOUTH);
		setLayout(new BorderLayout());
		scrollA = new JScrollPane(panelA);
		scrollA.setMinimumSize(new Dimension(500, 500));
        scrollA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollA, BorderLayout.CENTER);
		add(panelB, BorderLayout.SOUTH);
		add(panelD, BorderLayout.NORTH);
		colorearBordear();
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
	public void controlador(ActionListener ctrmodulos,ListSelectionListener campanya) {
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
			if(panelC!=null)
				frame.remove(panelC);
			relleno=false;
		}	
		if(lista!=null) {
			panelC= new JPanel();
			panelC.setBackground(FONDO);
			for(ICampanya campan: lista) {
				JCheckBox camp= new JCheckBox(campan.getNombre()+"-> Inicio:"+campan.getFechaInit()+", Fin:"+campan.getFechaFin());
				camp.setBackground(FONDO);
				panelC.add(camp);
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
	public void ver() {
		
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
		panelA.validate();
		}
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

}
