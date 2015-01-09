package crf;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MAIN {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile(
				"C:\\Users\\Thanos\\workspace\\Merger\\file.txt", "rw");
		for(int i=0;i<10240;i+=4){
			file.seek(i);
			System.out.println(file.readInt());
		}
		file.close();
	}

}
