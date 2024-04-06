import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

public class Wspp {
	public static void main (String[] args) {
		String word;
		int wordCount = 0;
		Map<String, IntList> map = new HashMap<>();
		List<String> words = new ArrayList<>();
		try {
			File in = new File(args[0]);
			Scanner sc = new Scanner(in);
			while (sc.hasNextWord()) {
				word = sc.nextWord().toLowerCase();
				wordCount++;
				if (!map.containsKey(word)) {
					words.add(word);
					IntList list = new IntList();
					list.add(wordCount);
					map.put(word, list);
				}
				else {
					map.get(word).add(wordCount);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found " + e.getMessage());
		}
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
			try {
				for (String currWord : words) {
					writer.write(currWord);
					writer.write(" ");
					IntList list = map.get(currWord);
					writer.write(Integer.toString(list.size()));
					for (int i = 0; i < list.size(); i++) {
						writer.write(" ");
						writer.write(Integer.toString(list.get(i)));
					}
					writer.newLine();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
	}	
}
