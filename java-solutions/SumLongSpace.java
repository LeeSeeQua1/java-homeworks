public class SumLongSpace {
    public static void main (String[] args) {
	long sum = 0;
	for (int i = 0; i < args.length; i++) {
	    int index = 0;
	    while (index < args[i].length()) {
		int start = index;
		while (index < args[i].length() && Character.getType(args[i].charAt(index)) != Character.SPACE_SEPARATOR) {
		    index++;
		}
		if (index > start) {
		    sum += Long.parseLong(args[i].substring(start, index));
		}
		index++;
	    }
	}
	System.out.println(sum);
    }
}