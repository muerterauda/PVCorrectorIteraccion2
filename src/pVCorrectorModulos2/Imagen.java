package pVCorrectorModulos2;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Imagen extends JPanel{
	private String foto;
	public Imagen(String dir) {
		this.setSize(400,400);
		this.setMinimumSize(new Dimension(200, 200));
		foto=dir;
	}

	public void paint(Graphics grafico) {
		Dimension height = getSize();

		ImageIcon Img = new ImageIcon(getClass().getResource(foto));

		grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
		setOpaque(false);
		super.paintComponent(grafico);
	}
}
