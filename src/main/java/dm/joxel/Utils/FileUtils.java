package dm.joxel.Utils;

import java.io.*;

public class FileUtils {
	public static String loadAsString(String path) {
		StringBuilder results = new StringBuilder();

		System.out.println(path);

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line = "";
			while( (line = reader.readLine()) != null ) {
				results.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not open file " + path);
		}


		return results.toString();
	}
}
