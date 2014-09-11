package game.world.dimensions;

/**
 * Dimensions 
 * For use in room to define the floor space.
 * @author Shane Brewer.
 *
 */
public interface Dimension {
	
	/**
	 * checks weather a point is contained in this dimension.
	 * @param x - a point on the x axis.
	 * @param y - a point on the y axis
	 * @return - return true only if the point is in the axis.
	 */
	public boolean contains(double x, double y);
	
	/**
	 * Gets the bounding box for this Dimension.
	 * @return - return the bounding box that represents the dimensions.
	 */
	public Rectangle getBoundingBox();
	
	/**
	 * Gets the minimum most x corod in the Dimensions
	 * @return - returns X
	 */
	public double getX();
	
	/**
	 * Gets the minum most y Corod in the dimension
	 * @return - minmum y
	 */
	public double getY();
	
	/**
	 * Gets the maximum width from the dimension
	 * @return - max width.
	 */
	public double getWidth();
	
	/**
	 * Gets the max height from the dimension
	 * @return - max height.
	 */
	public double getHeight();
}
