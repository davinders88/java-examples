package com.big.size.data;
 
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class EfficientlyReadingHugeFileIO {
	
	private static String path = "G:\\workspace-tech\\Core\\src\\com\\big\\size\\data\\hugeTextFile.txt";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//readByteByByte();
		//readMultipleByteAtOnce();
		
		//readUsingBufferedReader();
		readUsingBufferedReader_withMultiThreading();
		//readUsingFileChannelByteBufferMultipleBytes();
		//readUsingFileChannelDirectByteBuffer();
		//appendToAFile();
		
	}
	
	private static void readUsingFileChannelDirectByteBuffer() throws IOException {
		System.out.println("*********************readUsingFileChannelDirectByteBuffer*********************");
		long startTime = System.currentTimeMillis();
		FileInputStream f = new FileInputStream( path );
		FileChannel ch = f.getChannel( );
		ByteBuffer bb = ByteBuffer.allocateDirect( 1024 * 1024 );
		byte[] barray = new byte[1024 * 1024];
		int nRead, nGet;
		 Integer linenumber = 1;
			StringBuilder result = new StringBuilder();
			StringBuilder sb = new StringBuilder();
		while ( (nRead=ch.read( bb )) != -1 )
		{
		    if ( nRead == 0 )
		        continue;
		    bb.position( 0 );
		    bb.limit( nRead );
		    while( bb.hasRemaining( ) )
		    {
		        nGet = Math.min( bb.remaining( ), 1024 * 1024 );
		        bb.get( barray, 0, nGet );
		        for ( int i=0; i<nGet; i++ ) {
		        	char charc = (char)barray[i];
			    	if (charc == '\n') {
			    		if (sb.toString().contains("CPU")) {
			    			result.append(linenumber);
			    		}
			    		linenumber++;
			    		sb = new StringBuilder();
			    	} else {
			    		sb.append(charc);
			    	}
		        }
		    }
		    bb.clear( );
		}
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "  " + result.length());
	}
	
	private static void readUsingFileChannelByteBufferMultipleBytes() throws IOException {
		System.out.println("*********************readUsingBufferedReader*********************");
		long startTime = System.currentTimeMillis();
		FileInputStream f = new FileInputStream( path );
		FileChannel ch = f.getChannel();
		byte[] barray = new byte[1024 * 1024];
		ByteBuffer bb = ByteBuffer.wrap(barray);
		int nRead;
		Integer linenumber = 1;
		StringBuilder result = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		while ( (nRead=ch.read( bb )) != -1 )
		{
		    for ( int i=0; i<nRead; i++ ) {
		    	char charc = (char)barray[i];
		    	if (charc == '\n') {
		    		if (sb.toString().contains("CPU")) {
		    			result.append(linenumber);
		    		}
		    		linenumber++;
		    		sb = new StringBuilder();
		    	} else {
		    		sb.append(charc);
		    	}
		    }
		    bb.clear( );
		}
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "  " + result.length());
	}
	
	private static void readUsingBufferedReader_withMultiThreading() throws IOException, InterruptedException {
		System.out.println("*********************readUsingBufferedReader_withMultiThreading*********************");
		long startTime = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder();
		String str;
		String wordToFind = "CPU";
		
		List<String> listStr1 = new ArrayList<>(190001);
		while((str = br.readLine()) != null) {
			listStr1.add(str);
		}
		
		int partition = listStr1.size() / 3;
		CountDownLatch cdl = new CountDownLatch(3);
		
		StringBuilder lineNumber1 = new StringBuilder();
		Thread t1 = new Thread(()-> {
			for (int i= 0 ; i < partition; i++) {
				if (listStr1.get(i).contains(wordToFind)) {
					lineNumber1.append(i);
				}
			}
			cdl.countDown();
		}
		);
		t1.start();
		
		StringBuilder lineNumber2 = new StringBuilder();
		Thread t2 =new Thread(()-> {
			for (int i= partition ; i < partition *2; i++) {
				if (listStr1.get(i).contains(wordToFind)) {
					lineNumber2.append( i);
				}
			}
			cdl.countDown();
		}
		);
		t2.start();
		
		StringBuilder lineNumber3 = new StringBuilder();
		Thread t3 = new Thread(()-> {
			for (int i= partition*2 ; i < listStr1.size(); i++) {
				if (listStr1.get(i).contains(wordToFind)) {
					lineNumber3.append(i);
				}
			}
			cdl.countDown();
		}
		);
		t3.start();
		
		cdl.await();
		
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "  " + lineNumber1.length() + " " + lineNumber2.length() + " "
				+ lineNumber3.length());
	}
	
	private static void readUsingBufferedReader() throws IOException {
		System.out.println("*********************readUsingBufferedReader*********************");
		long startTime = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder();
		String i;
		StringBuilder lineNumber = new StringBuilder();
		Integer linenum = 1;
		while((i = br.readLine()) != null) {
			
			if (i.contains("CPU")) {
				lineNumber.append(linenum);
			}
			linenum ++;
		}
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "  " + lineNumber.length());
	}
	
	private static void readMultipleByteAtOnce() throws IOException {
		System.out.println("*********************readMultipleByteAtOnce*********************");
		long startTime = System.currentTimeMillis();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		byte[] byteArray = new byte[100000];
		StringBuilder sb = new StringBuilder();
		int i;
		while((i = bis.read(byteArray,0,100000)) != -1) {
			for (byte b : byteArray) {
				sb.append((char)i);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime));
	}

	private static void readByteByByte() throws FileNotFoundException, IOException {
		System.out.println("*********************readByteByByte*********************");
		long startTime = System.currentTimeMillis();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		StringBuilder sb = new StringBuilder();
		int i;
		while((i = bis.read()) != -1) {
			sb.append((char)i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime));
	}
	
	private static void appendToAFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String data = "\r\n" + 
					"Later came multitasking which meant that computers could execute multiple programs (AKA tasks or processes) at\r\n" + 
					"the same time. It wasn't really \"at the same time\" though. The single CPU was shared between the programs. The \r\n" + 
					"operating system would switch between the programs running, executing each of them for a little while before switching.";
			File file = new File(path);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			int c = 0;
			while(c < 200000) {
				bw.write(data);
				c++;
			}
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
