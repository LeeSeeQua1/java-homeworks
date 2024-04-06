public class Sum {
    public static void main (String[] args) {
	int sum = 0;
	for (int i = 0; i < args.length; i++) {
	    int index = 0;
	    while (index < args[i].length()) {
		int start = index;
		while (index < args[i].length() && !Character.isWhitespace(args[i].charAt(index))) {
		    index++;
		}
		if (index > start) {
		    sum += Integer.parseInt(args[i].substring(start, index));
		}
		index++;
	    }
	}
	System.out.println(sum);
    }
}