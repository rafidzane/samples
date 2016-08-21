package com.zane.generic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ZaneFileUtil {
	
	
	private static final String LINE_BREAK = "\n";

	public static final String readStringFromFile(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder builder = new StringBuilder();
		while( (line=reader.readLine()) != null){
			builder.append(line+LINE_BREAK);
		}
		return builder.toString();	
	}

}
