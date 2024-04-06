import java.util.Arrays;
public class ReverseSumHexAbc {
	public static int wordToInt(String word) {
		int num = 0, pow = 1;
		for (int i = word.length() - 1; i > 0; i--) {
			num += (word.charAt(i) - 'a') * pow;
			pow *= 10;
		}
		if (word.charAt(0) == '-') {
			num = num * (-1);
		} else {
			num += (word.charAt(0) - 'a') * pow;
		}
		return num;
	}
	public static String intToWord(int num) {
		String numStr = Integer.toString(num);
		StringBuilder sb = new StringBuilder();
		if (numStr.charAt(0) == '-') {
			sb.append('-');
		}
		else {
			sb.append((char)(numStr.charAt(0) - '0' + 'a'));
		}
		for (int i = 1; i < numStr.length(); i++) {
			sb.append((char)(numStr.charAt(i) - '0' + 'a'));
		}
		return sb.toString();
	}
	public static String type(String str) {
		int i = 0;
		if (str.charAt(0) == '-') i++;
		if (Character.isLetter(str.charAt(i))) {
			return "dec";
		} else {
			return "hex";
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] colSum = new int[64];
		int currAns = 0;
		int currLine = 0;
		int currEl;
		int currIndex = 0;
		StringBuilder line = new StringBuilder();
		while (sc.hasNextNumStr()) {
			while (currLine < sc.getLineCount()) {
				System.out.println(line);
				currLine++;
				currAns = 0;
				currIndex = 0;
				line = new StringBuilder();
			}
			String currToken = sc.nextNumStr();
			if (type(currToken).equals("hex")) {
				currEl = Integer.parseUnsignedInt(currToken.substring(2), 16);
			} else {
				currEl = wordToInt(currToken);
			}
			if (currIndex >= colSum.length) {
				colSum = Arrays.copyOf(colSum, colSum.length * 2);
			}
			colSum[currIndex] += currEl;
			currAns += colSum[currIndex];
			line.append(intToWord(currAns));
			line.append(" ");
			currIndex++;
		}
		System.out.println(line);
		currLine++;
		for (int i = 0; i < sc.getLineCount() - currLine; i++) {
			System.out.println();
		}
	}
}