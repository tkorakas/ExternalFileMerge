import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MergerAlgorithm {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile(
				"C:\\Users\\Thanos\\workspace\\Merger\\file.txt", "rw");
		RandomAccessFile nfile = new RandomAccessFile(
				"C:\\Users\\Thanos\\workspace\\Merger\\nfile.txt", "rw");
		long size = file.length();
		int fnumbers = (int) (size / 4);
		int ram = 1024;
		int numbers[] = null;
		int arrayp = 0;
		int times = fnumbers / ram;
		ArrayList<Integer> filePointer = new ArrayList<Integer>();
		ArrayList<Integer> confilePointer = new ArrayList<Integer>();
		if ((fnumbers % ram) != 0) {
			for (int i = 0; i < times; i++) {
				filePointer.add(i * ram * 4);
				confilePointer.add((i * ram * 4) + (ram * 4));
			}
			filePointer.add(times * ram * 4);
			confilePointer.add((times * ram * 4) + ((fnumbers % ram) * 4));
		} else {
			for (int i = 0; i < times; i++) {
				filePointer.add(i * ram * 4);
				confilePointer.add((i * ram * 4) + (ram * 4));
			}
		}
		for (int i = 0; i < times; i++) {
			numbers = new int[ram];
			arrayp = 0;
			for (int j = i * ram * 4; j < (i * ram * 4) + ram * 4; j += 4) {
				file.seek(j);
				numbers[arrayp] = file.readInt();
				arrayp++;
			}// j loop

			int low = 0;
			int high = numbers.length - 1;
			quickSort(numbers, low, high);
			arrayp = 0;
			for (int t = i * ram * 4; t < (i * ram * 4) + ram * 4; t += 4) {
				nfile.seek(t);
				nfile.writeInt(numbers[arrayp]);
				arrayp++;
			}// j loop
		}// i loop

		if ((fnumbers % ram) != 0) {
			arrayp = 0;
			int mod = fnumbers % ram;
			numbers = new int[mod];
			for (int i = (times * ram) * 4; i < (times * ram * 4) + (mod * 4); i += 4) {
				file.seek(i);
				numbers[arrayp] = file.readInt();
				arrayp++;
			}
			// quicksort
			int low = 0;
			int high = numbers.length - 1;
			quickSort(numbers, low, high);
			// write
			arrayp = 0;
			for (int i = (times * ram) * 4; i < (times * ram * 4) + (mod * 4); i += 4) {
				nfile.seek(i);
				nfile.writeInt(numbers[arrayp]);
				arrayp++;
			}
		}// if
			// merge
		int fileIndex = 0;

		for (int i = 0; i < file.length()/4; i++) {
			nfile.seek(filePointer.get(0));
			int min = nfile.readInt();
			int minIndex = 0;
			for(int j=1;j< filePointer.size();j++){
				nfile.seek(filePointer.get(j));
				int temp = nfile.readInt();
				if(min> temp){
					min = temp;
					minIndex = j;
				}
			}//find min
			filePointer.set(minIndex, filePointer.get(minIndex)+4);
			file.seek(fileIndex);
			file.writeInt(min);
			fileIndex+=4;
			for(int k=0;k<filePointer.size();k++){
				if(filePointer.get(k).equals(confilePointer.get(k))){
					filePointer.remove(k);
					confilePointer.remove(k);
				}
			}//check fp
		}// main for
		// MERGE

		file.close();
		nfile.close();

	}

	public static void show(int[] arr) {
		System.out.println("numbers len: " + arr.length);
		for (int i = 0; i < arr.length; i++) {
			System.out.println("index: " + i + " number: " + arr[i]);
		}
	}

	public static void quickSort(int[] arr, int low, int high) {

		if (arr == null || arr.length == 0)
			return;

		if (low >= high)
			return;

		int middle = low + (high - low) / 2;
		int pivot = arr[middle];

		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}

			while (arr[j] > pivot) {
				j--;
			}

			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
		if (low < j)
			quickSort(arr, low, j);

		if (high > i)
			quickSort(arr, i, high);
	}
}
