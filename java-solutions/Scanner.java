import java.io.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class Scanner {
	private final int bufferSize = 256;
	private final char[] buffer = new char[bufferSize];
	private int currPos = 0;
	private int bytesRead = 0;
	private final StringBuilder currToken = new StringBuilder(0);
	private final Reader reader;
	private final char sep = System.lineSeparator().charAt(System.lineSeparator().length() - 1);
	private long lineCount = 0;

	public Scanner (InputStream source) {
		reader = new InputStreamReader(source, StandardCharsets.UTF_8);
	}
	public Scanner (File source) throws FileNotFoundException{
		InputStream fStream = new FileInputStream(source);
		reader = new InputStreamReader(fStream, StandardCharsets.UTF_8);
	}
	private void skipNonReadable(Type type) {
		while (bytesRead != -1) {
			while(currPos < bytesRead && !isReadable(buffer[currPos], type)) {
				if (skipLineSeparator()) {
					lineCount++;
				} else {
					currPos++;
				}
			}
			if (currPos < bytesRead) {
				return;
			}
			readNew();
		}
	}
	private boolean isReadable(char ch, Type type) {
		if (type == Type.NUMSTR) {
			return Character.isDigit(ch) || Character.isLetter(ch) || ch == '-';
		} else if (type == Type.WORD) {
			return Character.isLetter(ch) || Character.getType(ch) == Character.DASH_PUNCTUATION || ch == '\'';
		} else if (type == Type.INT){
			return Character.isDigit(ch) || ch == '-';
		}
		return !Character.isWhitespace(ch);
	}
	private boolean hasNext(Type type) {
		skipNonReadable(type);
		if (currPos >= bytesRead) {
			return false;
		}
		return isReadable(buffer[currPos], type);
	}
	private String next(Type type) throws NoSuchElementException{
		if (!hasNext(type)) {
			throw new NoSuchElementException();
		}
		while (bytesRead != -1) {
			while (currPos < bytesRead && isReadable(buffer[currPos], type)) {
				currToken.append(buffer[currPos]);
				currPos++;
			}
			if (currPos < bytesRead) {
				break;
			}
			readNew();
		}
		String str = currToken.toString();
		currToken.setLength(0);
		return str;
	}
	public boolean hasNextInt() {
		return hasNext(Type.INT);
	}

	public boolean hasNextWord() {
		return hasNext(Type.WORD);
	}
	public boolean hasNextNumStr() {
		return hasNext(Type.NUMSTR);
	}
	public int nextInt() throws NoSuchElementException {
		return Integer.parseInt(next(Type.INT));
	}
	public String nextWord() throws NoSuchElementException {
		return next(Type.WORD);
	}
	public String nextNumStr() throws NoSuchElementException {
		return next(Type.NUMSTR);
	}

	private boolean skipLineSeparator() {
		if (buffer[currPos] == sep) {
			currPos++;
			if (currPos >= bytesRead) {
				readNew();
			}
			return true;
		}
		return false;
	}

	public long getLineCount() {
		return lineCount;
	}
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	private void readNew() {
		try {
			bytesRead = reader.read(buffer);
			currPos = 0;
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}