import java.util.Arrays;
public class Reverse {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] ints = new int[64];
		int[] length = new int[16];
		int currIndex = -1, currLine = 0, lastInLine = -1;
		while (sc.hasNextInt()) {
			while (currLine < sc.getLineCount()) {
				length[currLine] = currIndex - lastInLine;
				currLine++;
				if (currLine >= length.length) {
					length = Arrays.copyOf(length, length.length * 2);
				}
				lastInLine = currIndex;
			}
			currIndex++;
			if (currIndex >= ints.length) {
				ints = Arrays.copyOf(ints, ints.length * 2);
			}
			ints[currIndex] = sc.nextInt();
		}
		length[currLine] = currIndex - lastInLine;
		currLine++;
		for (int i = 0; i < sc.getLineCount() - currLine; i++) {
			System.out.println();
		}
		for (int x = currLine - 1; x >= 0; x--) {
			for (int y = length[x]; y > 0; y--) {
				System.out.print(ints[currIndex] + " ");
				currIndex--;
			}
			System.out.println();
		}

	}
}