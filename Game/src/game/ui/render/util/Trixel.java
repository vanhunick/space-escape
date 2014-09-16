package game.ui.render.util;

import game.world.dimensions.Point3D;

import java.awt.Color;

/**
 * @author hardwiwill
 * A 3D pixel/voxel/cube.
 */
public class Trixel {

	/**
	 * trixel width, height and depth
	 */
	public static final int SIZE = 10;
	/**
	 * The trixel's position in the trixel grid (trid)!
	 */
	private Trixition trixition;
	/**
	 * TODO Will need to be one of these for each face of the
	 */
	private Color colour;

	public Trixel (Trixition trisition, Color c){
		this.trixition = trisition;
		this.colour = c;
	}
	
	public Color getColor(){
		return colour;
	}
	
	public Trixition getTrixition(){
		return trixition;
	}
}