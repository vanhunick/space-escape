package game.world.dimensions;

import game.ui.render.util.Vector3D;

/**
 * @author pondy & will
 * A NOW IMMUTABLE representation of a 3d point
 */
public class Point3D{
	// ---- PONDY'S COMMENTS:
	// I made a float version of this class so that vectors could be used in the
	// transform class and so it could
	// be faster for the 3d rendering

	public final float x;
	public final float y;
	public final float z;

	/**
	 * Construct a new vector, with the specified x, y, z components computes
	 * and caches the magnitude.
	 */
	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	/** Returns the distance to another point. */
	public Vector3D distanceTo(Point3D other) {
		return new Vector3D(getX() - other.getX(), getY() - other.getY(), getZ()
				- other.getZ());
	}

	public String toString() {
		StringBuilder ans = new StringBuilder("Point:");
		ans.append('(').append(getX()).append(',').append(getY()).append(',')
				.append(getZ()).append(')');
		return ans.toString();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point3D other = (Point3D) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}


	/**
	 * @param top
	 * @return a point that's flipped using top as the top
	 */
	public Point3D flipY(int top) {
		return new Point3D(x, top-y, z);
	}

}
