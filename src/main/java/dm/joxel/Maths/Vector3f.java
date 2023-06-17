package dm.joxel.Maths;

public class Vector3f {
	public float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Vector3f add(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z);
	}
	
	public static Vector3f subtract(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z);
	}
	
	public static Vector3f multiply(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
	}
	
	public static Vector3f divide(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z);
	}
	
	public static float length(Vector3f vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
	}
	
	public static Vector3f normalize(Vector3f vector) {
		float len = Vector3f.length(vector);
		return Vector3f.divide(vector, new Vector3f(len, len, len));
	}
	
	public static float dot(Vector3f vector1, Vector3f vector2) {
		return vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z;
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
		Vector3f other = (Vector3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}
}
