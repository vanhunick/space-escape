 package test.render;

import game.ui.render.Res;
import game.ui.render.util.GameImage;
import game.ui.render.util.Transform;
import game.ui.render.util.Vector3D;
import game.world.dimensions.Point3D;
import game.world.util.Floor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * @author hardwiwill
 *
 * For testing rendering stuff!
 */
public class RotationTest extends JPanel{

	private Point3D point = new Point3D(100, 100, 20);
	private Point3D centre = new Point3D(200, 200, -100);

	private Floor floor;

	/**
	 * viewer direction will always be 0,0,1 (environment will move, not viewer)
	 * but may use a 'fake' viewer direction in future to make model closer to reality
	 */
	private Vector3D viewerDir = new Vector3D(0,0,(int)(-Math.PI*2));

	private int objSize = 50;
	private int centreSize = 10;

	private final int TRANSLATE = 200;

	private final String LEFT = "LEFT", RIGHT = "RIGHT";

	private Action rotateLeft = new AbstractAction(LEFT){
    	public void actionPerformed(ActionEvent e){
    		rotate(0, 0, -1);
    	}
    };

    private Action rotateRight = new AbstractAction(RIGHT){
    	public void actionPerformed(ActionEvent e){
    		rotate(0, 0, 1);
    	}
    };
	private GameImage image;

	public RotationTest(){
		WillMouseMotionListener mouseListener = new WillMouseMotionListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);

		this.getInputMap().put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), LEFT);

		this.getInputMap().put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),RIGHT);

		this.getActionMap().put(LEFT, rotateLeft);
		this.getActionMap().put(RIGHT, rotateRight);

		// define floor (for testing)
		Point3D[] floorPoints = new Point3D[]{ new Point3D(0,0,0), new Point3D(500, 0, 0), new Point3D(500, 300, 0), new Point3D(0, 300, 0)};
		floor = new Floor(floorPoints);
		image = new GameImage(Res.getImageFromName("table"), new Point3D( 400, 200, 200), new Dimension(50, 100));

	}
	
	public void rotate(int rotateX, int rotateY, int rotateZ){
		float scalar = 10;
		
		Transform rotateDir = Transform.newXRotation(rotateY/scalar).compose(
				Transform.newYRotation(rotateX/scalar)).compose(
						Transform.newZRotation(rotateZ/scalar));
		
		viewerDir = rotateDir.multiply(viewerDir);
		
		Transform translateToOrigin = Transform.newTranslation(-centre.getX(), -centre.getY(), -centre.getZ());
		Transform rotatePoint = Transform.newXRotation(viewerDir.y/scalar).compose(
				Transform.newYRotation(-viewerDir.x/scalar)).compose(
						Transform.newZRotation(0));
		Transform translateBack = Transform.newTranslation(centre.getX(), centre.getY(), centre.getZ());

		point = translateToOrigin.multiply(point);
		point = rotateDir.multiply(point);
		point = translateBack.multiply(point);

		floor.transform(translateToOrigin);
		floor.transform(rotateDir);
		floor.transform(translateBack);
		
		image = new GameImage(Res.getImageFromName("Table"), new Point3D( 400, 200, 200), new Dimension(50, 100));
		image.transform(translateToOrigin);
		image.transform(rotatePoint);
		image.transform(translateBack);

		repaint();
	}


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// z component used for size, not display

		Polygon floorPoly = floorToPolygon(floor);
		g.setColor(Color.blue);
		g.fillPolygon(floorPoly);

		g.setColor(Color.red);
		g.fillRect((int)point.getX()+TRANSLATE-(objSize/2), (int)point.getY()+TRANSLATE-(objSize/2) ,objSize, objSize);
		g.fillOval((int)centre.getX()+TRANSLATE-(centreSize/2), (int)centre.getY()+TRANSLATE-(centreSize/2), centreSize, centreSize);
		g.fillOval(TRANSLATE-(centreSize/2), TRANSLATE-(centreSize/2), centreSize, centreSize);

		g.drawImage(image.getImage(), (int)image.getPosition().x-image.getDimension().width/2, (int)image.getPosition().y-image.getDimension().height, 
				image.getDimension().width, image.getDimension().height, null);
		//System.out.println((int)point.getX()+TRANSLATE-(objSize/2));
		
		// draw viewerDir
		int offsetX = (int)image.getPosition().x;
		int offsetY = (int)image.getPosition().y;
		int scale = 30;
		//g.drawLine(offsetX, offsetY, offsetX+(int)(viewerDir.x*scale), offsetY+(int)(viewerDir.y*scale));
	}


	/**
	 * @param floor
	 * @return a polygon representing the floor
	 */
	private Polygon floorToPolygon(Floor floor){
		Point3D[] points = floor.getPoints();
		int[] xpoints = new int[points.length];
		int[] ypoints = new int[points.length];

		for (int i = 0; i < points.length; i++){
			Point3D point = points[i];
			xpoints[i] = (int)point.getX();
			ypoints[i] = (int)point.getY();
		}
		return new Polygon(xpoints, ypoints, points.length);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		JFrame frame = new JFrame();
		Dimension SCREEN_SIZE = new Dimension(700,700);
		frame.setSize(SCREEN_SIZE );
		frame.add(new RotationTest());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public class WillMouseMotionListener implements MouseListener, MouseMotionListener {

		int mouseX, mouseY;

		@Override
		public void mouseDragged(MouseEvent e) {

			int dx = e.getX()-mouseX;
			int dy = e.getY()-mouseY;

			rotate(dx, dy, 0);

			mouseX = e.getX();
			mouseY = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}

