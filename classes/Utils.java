package classes;

import java.io.*;
import java.util.*;

public class Utils
{
	private Utils() {} // no instances
	/**
	* reads entire file and returns contents
	* @param final String file name
	* @return String contents of file
	*/
	public static String readFile(final String fn)
	{
		StringBuilder buffer = new StringBuilder("");
		try
		{
			FileReader reader = new FileReader(new File(fn));
			BufferedReader file = new BufferedReader(reader);
			String line;
			while((line = file.readLine()) != null) {buffer.append(line).append("\n");}
			reader.close();
		} catch(IOException e) {e.printStackTrace();}
		return buffer.toString();
	}
	/**
	* writes or appends data to file
	* @param final String data to write
	* @param final String file name
	* @param boolean append file?
	* @return boolean true if successful else false
	*/
	public static boolean writeFile(final String data, final String fn, boolean append)
	{
		File f = new File(fn);
		if(append) {append = f.exists();}
		try
		{
			String myData;
			if(!data.endsWith("\n")) {myData = new StringBuilder(data).append("\n").toString();}
			else {myData = data;}
			FileWriter file = new FileWriter(f, append);
			file.write(myData);
			file.close();
			return true;
		} catch(IOException e) {e.printStackTrace();}
		return false;
	}
	/**
	* counts line in file -- will be deprecated at some point
	* @param final String file name
	* @return int number of lines in file
	*/
	public static int countLines(final String fn)
	{
		int count = 0;
		try
		{
			FileReader reader = new FileReader(new File(fn));
			BufferedReader file = new BufferedReader(reader);
			String line;
			while((line = file.readLine()) != null) {count++;}
			reader.close();
		} catch(IOException e) {e.printStackTrace();}
		return count;
	}
	/**
	* reads in single line from file -- linear time yet saves memory
	* @param final String file name
	* @param final int line number to read
	* @return String line number lineNumber from file or empty string if not found
	*/
	public static String readLine(final String fn, final int lineNumber)
	{
		String data = "";
		int lineCount = countLines(fn);
		if(lineNumber <= lineCount)
		{
			try
			{
				FileReader reader = new FileReader(new File(fn));
				BufferedReader file = new BufferedReader(reader);
				String line;
				int count = 0;
				while((line = file.readLine()) != null && count < lineNumber && count < lineCount)
				{
					if(count++ == lineNumber-1) {data = line;}
				}
				reader.close();
			} catch(IOException e) {e.printStackTrace();}
		}
		return data;
	}
	/**
	* gets password object that has matching application -- reads line by line to save memory
	* @param final String application to fine
	* @param final String file name
	* @return Password password object if found else null
	*/
	public static Password getPasswordFromApp(final String application, final String fn)
	{
		Password p = null;
		try
		{
			FileReader reader = new FileReader(new File(fn));
			BufferedReader file = new BufferedReader(reader);
			String line;
			while((line = file.readLine()) != null)
			{
				String tok[] = line.split(",");
				if(tok[0].equals(application)) {p = new Password(tok[1], tok[0], tok[2], tok[3], tok[4]);}
			}
			reader.close();
		} catch(IOException e) {e.printStackTrace();}
		return p;
	}
	/**
	* checks if application is found in storage
	* @param final String application to find
	* @param final String file name
	* @return boolean true if found else false
	*/
	public static boolean passwordExists(final String application, final String fn)
	{
		boolean found = false;
		try
		{
			FileReader reader = new FileReader(new File(fn));
			BufferedReader file = new BufferedReader(reader);
			String line;
			while((line = file.readLine()) != null)
			{
				String tok[] = line.split(",");
				if(tok[0].equals(application)) {found = true;}
			}
			reader.close();
		} catch(IOException e) {e.printStackTrace();}
		return found;
	}
	/**
	* copies file from one to another
	* @deprecated
	* @param final String original file
	* @param final String copy file
	* @return boolean true if successful else false
	*/
	@Deprecated
	public static boolean copyFile(final String original, final String copy)
	{
		File originalFile = new File(original);
		if(originalFile.exists())
		{
			try
			{
				FileReader reader = new FileReader(originalFile);
				FileWriter c = new FileWriter(copy);
				BufferedReader o = new BufferedReader(reader);
				String line;
				while((line = o.readLine()) != null) {c.write(new StringBuilder(line).append("\n").toString());}
				reader.close();
				c.close();
				return true;
			} catch(IOException e) {e.printStackTrace();}
		}
		return false;
	}
	/**
	* removes line from file
	* @param final String file name
	* @param final int line number to remove
	* @return boolean true if successful else false
	*/
	public static boolean removeLine(final String fn, final int lineNumber)
	{
		StringBuilder buffer = new StringBuilder("");
		int lineCount = countLines(fn);
		if(lineNumber <= lineCount)
		{
			try
			{
				FileReader reader = new FileReader(new File(fn));
				BufferedReader file = new BufferedReader(reader);
				String line;
				int count = 0;
				while(count < lineCount && (line = file.readLine()) != null)
				{
					if(count++ != (lineNumber-1)) {buffer.append(line).append("\n");}
				}
				reader.close();
				writeFile(buffer.toString(), fn, false);
				return true;
			} catch(IOException e) {e.printStackTrace();}
		}
		return false;
	}
	/**
	* get line number of password with matching application
	* @param final String application to find
	* @param final String file name
	* @return int line number of password or -1 if not found
	*/
	public static int getPasswordLineNumber(final String application, final String fn)
	{
		int lineNumber = -1;
		try
		{
			FileReader reader = new FileReader(new File(fn));
			BufferedReader file = new BufferedReader(reader);
			String line;
			int count = 0;
			while((line = file.readLine()) != null)
			{
				count++;
				String tok[] = line.split(",");
				if(tok[0].equals(application)) {lineNumber = count;}
			}
			reader.close();
		} catch(IOException e) {e.printStackTrace();}
		return lineNumber;
	}
	/**
	* checks if file exists
	* @param final String file name
	* @return boolean true if exists else false
	*/
	public static boolean fileExists(final String fn) {return new File(fn).exists();}
	/**
	* checks if directory exists
	* @param final String path to directory
	* @return boolean true if exists else false
	*/
	public static boolean dirExists(final String path) {return new File(path).isDirectory();}
	/**
	* makes directory if it does not exist
	* @param final String path to directory to create
	* @return boolean true if successful else false
	*/
	public static boolean mkdir(final String path)
	{
		if(!dirExists(path))
		{
			try {return new File(path).mkdir();}
			catch(Exception e) {e.printStackTrace();}
		}
		return false;
	}
	/**
	* deletes file
	* @param final String file to delete
	* @return boolean true if deleted else false
	*/
	public static boolean deleteFile(final String fn)
	{
		File file = new File(fn);
		if(file.exists()) {return file.delete();}
		return false;
	}
	/**
	* attaches file name to path
	* @param final String path to attach file to
	* @param final String file name
	* @return String merged path+file name
	*/
	public static String mergePath(final String path, final String fn) {return new StringBuilder(path).append(System.getProperty("file.separator")).append(fn).toString();}
}
