package dm.joxel.Maths;

public class Vector2f {
	public float x, y, z;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static Vector2f add(Vector2f vector1, Vector2f vector2) {
		return new Vector2f(vector1.x + vector2.x, vector1.y + vector2.y);
	}
	
	public static Vector2f subtract(Vector2f vector1, Vector2f vector2) {
		return new Vector2f(vector1.x - vector2.x, vector1.y - vector2.y);
	}
	
	public static Vector2f multiply(Vector2f vector1, Vector2f vector2) {
		return new Vector2f(vector1.x * vector2.x, vector1.y * vector2.y);
	}
	
	public static Vector2f divide(Vector2f vector1, Vector2f vector2) {
		return new Vector2f(vector1.x / vector2.x, vector1.y / vector2.y);
	}
	
	public static float length(Vector2f vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
	}
	
	public static Vector2f normalize(Vector2f vector) {
		float len = Vector2f.length(vector);
		return Vector2f.divide(vector, new Vector2f(len, len));
	}
	
	public static float dot(Vector2f vector1, Vector2f vector2) {
		return vector1.x * vector2.x + vector1.y * vector2.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
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
		Vector2f other = (Vector2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

}
