import java.util.Arrays;
public class IntList {
	private int size = 0;
	private int[] numbers;

	public IntList() {
		numbers = new int[16];
	}

	private boolean isValidIndex(int index) {
		return 0 <= index && index < size;
	}

	public void add(int num) {
		if (size == numbers.length) {
			numbers = Arrays.copyOf(numbers, size * 2);
		}
		numbers[size] = num;
		size++;
	}

	public int get(int index) {
		if (!isValidIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		return numbers[index];
	}

	public int size() {
		return size;
	}
}