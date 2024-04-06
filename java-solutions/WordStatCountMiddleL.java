import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class WordStatCountMiddleL {
	public static void main(String[] args) {
		BufferedReader in = null;
		Map<String, Integer> map = new LinkedHashMap<>();
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
			String line = in.readLine();
			while (line != null) {
				int i = 0, start = 0;
				while (i < line.length()) {
					char ch = line.charAt(i);
					if (!isWordCharacter(ch)) {
						insertToMap(line.substring(start, i), map);
						start = i + 1;
					}
					i++;
				}
				if (i - start >= 5) {
					insertToMap(line.substring(start, i), map);
				}
				line = in.readLine();
			}
		} catch (FileNotFoundException e){
			System.err.println("Input file does not exist");
		} catch (IOException e) {
			System.err.println("IO error occured");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e){
					System.err.println("IO error occured");
				}
			}
		}
		BufferedWriter writer = null;
		List <Map.Entry<String, Integer>> list = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
			for (Map.Entry<String, Integer> e : list) {
				writer.write(e.getKey());
				writer.write(" ");
				writer.write(e.getValue().toString());
				writer.newLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Output file does not exist");
		} catch (IOException e) {
			System.err.println("IO error occured");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e){
					System.err.println("IO error occured");
				}
			}
		}
	}

	public static void insertToMap(String key, Map<String, Integer> map) {
		if (key.length() >= 5) {
			key = key.substring(2, key.length() - 2).toLowerCase();
			map.put(key, map.getOrDefault(key, 0) + 1);
		}
	}
	public static boolean isWordCharacter(char ch) {
		return Character.isLetter(ch) || ch == '\'' || Character.getType(ch) == Character.DASH_PUNCTUATION;
	}
}