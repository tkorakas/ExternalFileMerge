package crf;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MAIN {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile(
				"C:\\Users\\Thanos\\workspace\\Merger\\file.txt", "rw");
		for(int i=0;i<10240;i+=4){
			file.seek(i);
			file.writeInt((int) (Math.random()*1000));// comment and uncomment print to print file change *1000 to change random range
//			System.out.println(file.readInt()); // show file
		}
		file.close();
	}

}
