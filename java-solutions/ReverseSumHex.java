import java.util.Scanner;
import java.util.Arrays;
public class ReverseSumHex {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] currString = new int[64];
		int currSum;
		int n;
		int currEl;
		while (sc.hasNextLine()) {
			Scanner sc2 = new Scanner(sc.nextLine());
			currSum = 0;
			n = 0;
			while (sc2.hasNext()) {
				currEl = Integer.parseUnsignedInt(sc2.next(), 16);
				currSum += currEl;
				if (n == 0) {
					currString[0] += currEl;
				} else if (n < currString.length) {
					currString[n] += currSum;
				} else {
 					currString = Arrays.copyOf(currString, currString.length * 2);
					currString[n] = currString[n - 1] + currEl;
					for (int j = n + 1; j < currString.length; j++) {
						currString[j] = currString[n] - currSum;
					}
				}
				System.out.print(Integer.toHexString(currString[n]) + " ");
				n++;
			}
			for (int j = n; j < currString.length; j++) {
				currString[j] += currSum;
			}
			System.out.println();
		}

	}
}