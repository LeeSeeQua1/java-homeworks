import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.nio.charset.StandardCharsets;

public class WsppSortedFirst {
	public static void main(String[] args) {
		String word;
		int wordCount = 0;
		int lastInLine = 0;
		int lineCount = 0;
		Map<String, SizeList> map = new HashMap<>();
		List<String> words = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new File(args[0]));
			try {
				while (sc.hasNextWord()) {
					while (lineCount < sc.getLineCount()) {
						lastInLine = wordCount;
						lineCount++;
					}
					word = sc.nextWord().toLowerCase();
					wordCount++;
					SizeList pair = map.get(word);
					if (pair == null) {
						words.add(word);
						IntList list = new IntList();
						list.add(wordCount);
						map.put(word, new SizeList(1, list));
					} else {
						pair.size++;
						if (pair.list.get(pair.list.size() - 1) <= lastInLine) {
							pair.list.add(wordCount);
						}
					}
				}
			} finally {
				sc.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found " + e.getMessage());
		}

		Collections.sort(words);
		try {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
            ))) {
                for (String currWord : words) {
                    writer.write(currWord);
                    writer.write(" ");
                    writer.write(Integer.toString(map.get(currWord).size));
                    IntList list = map.get(currWord).list;
                    for (int i = 0; i < list.size(); i++) {
                        writer.write(" ");
                        writer.write(Integer.toString(list.get(i)));
                    }
                    writer.newLine();
                }
            }
		} catch (FileNotFoundException e) {
			System.err.println("Output file not found");
		} catch (IOException e) {
			System.err.println("Error while writing to a file: " + e.getMessage());
		}
	}
}
