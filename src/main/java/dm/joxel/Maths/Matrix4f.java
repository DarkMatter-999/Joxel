package dm.joxel.Maths;

import java.util.Arrays;

public class Matrix4f {
	public static final int SIZE = 4;
	public float[] element = new float[SIZE * SIZE];

	public static Matrix4f identity() {
		Matrix4f res = new Matrix4f();
		for(int i=0 ; i<SIZE ; i++) {
			for(int j=0; j<SIZE; j++) {
				res.set(i, j, 0.0f);
				if(i == j) {
					res.set(i, j, 1);
				}
			}
		}
		return res;
	}

	public static Matrix4f translate(Vector3f translation) {
		Matrix4f res = Matrix4f.identity();
		res.set(3,0, translation.x);
		res.set(3,1, translation.y);
		res.set(3,2, translation.z);

		return res;
	}
	
	public static Matrix4f rotate(float angle, Vector3f axis) {
		Matrix4f res = Matrix4f.identity();
		float cos = (float) Math.cos(Math.toRadians(angle));
		float sin = (float) Math.sin(Math.toRadians(angle));
		float C = 1 - cos;
		
		res.set(0, 0, cos + axis.x * axis.x * C);
		res.set(0, 1, axis.x * axis.y * C - axis.z * sin);
		res.set(0, 2, axis.x * axis.z * C + axis.y * sin);
		res.set(1, 0, axis.y * axis.x * C + axis.z * sin);
		res.set(1, 1, cos + axis.y * axis.y * C);
		res.set(1, 2, axis.y * axis.z * C - axis.x * sin);
		res.set(2, 0, axis.z * axis.x * C - axis.y * sin);
		res.set(2, 1, axis.z * axis.y * C + axis.x * sin);
		res.set(2, 2, cos + axis.z * axis.z * C);

		return res;		
	}

	public static Matrix4f scale(Vector3f scalar) {
		Matrix4f res = Matrix4f.identity();
		
		res.set(0, 0, scalar.x);
		res.set(1, 1, scalar.y);
		res.set(2, 2, scalar.z);
		
		return res;
	}

	public static Matrix4f multiply(Matrix4f matrix, Matrix4f other) {
		Matrix4f result = Matrix4f.identity();
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				result.set(i, j, matrix.get(i, 0) * other.get(0, j) + matrix.get(i, 1) * other.get(1, j) + matrix.get(i, 2) * other.get(2, j) + matrix.get(i, 3) * other.get(3, j));
			}
		}
		
		return result;
	}

	public float get(int r, int c) {
		return element[c * SIZE + r];
	}
	
	public void set(int r, int c, float val) {
		element[c * SIZE + r] = val;
	}

	public float[] getAll() {
		return element;
	}

	public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		Matrix4f result = Matrix4f.identity();
		
		Matrix4f translationMatrix = Matrix4f.translate(position);
		Matrix4f rotXMatrix = Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0));
		Matrix4f rotYMatrix = Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0));
		Matrix4f rotZMatrix = Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1));
		Matrix4f scaleMatrix = Matrix4f.scale(scale);
		
		Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix, Matrix4f.multiply(rotYMatrix, rotZMatrix));
		
		result = Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotationMatrix, scaleMatrix));
		
		return result;
	}

	public static Matrix4f perspective(float fov, float aspect, float near, float far) {
		Matrix4f res = Matrix4f.identity();

		float tanFOV = (float) Math.tan(Math.toRadians(fov/2));

		float zp = far + near;
		float zm = far - near;

		res.set(0,0, 1.0f / (aspect * tanFOV));
		res.set(1,1, 1 / (tanFOV));
		res.set(2,2, -( zp / zm ));
		res.set(3,3, 0.0f);
		res.set(2,3, -1.0f);
		res.set(3,2, - ((2.0f * far * near)/zm));

		return res; 
	}

	public static Matrix4f view(Vector3f position, Vector3f rotation) {
		Matrix4f result = Matrix4f.identity();
		
		Vector3f negposition = new Vector3f(-position.x, -position.y, -position.z);

		Matrix4f translationMatrix = Matrix4f.translate(negposition);
		Matrix4f rotXMatrix = Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0));
		Matrix4f rotYMatrix = Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0));
		Matrix4f rotZMatrix = Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1));
		
		Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix, Matrix4f.multiply(rotYMatrix, rotZMatrix));
		
		result = Matrix4f.multiply(translationMatrix, rotationMatrix);
		
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(element);
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
		Matrix4f other = (Matrix4f) obj;
		if (!Arrays.equals(element, other.element))
			return false;
		return true;
	}
}
