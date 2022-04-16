package classes;

import ui.*;

import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

/*
* TO DO:
*
* add ability to 'undo' (have a log of previous changes) -- !!! THIS IS THE MOST IMPORTANT FILE TO DELETE UPON CLOSE !!!
*
*/

public class Main
{
	private Main() {} // no instances
	private static final String inputFile = "passwords.csv"; // password storage file (encrypt?)
	/**
	* debug function prints templated t
	* @param T item to print to stdout
	*/
	public static <T> void println(T t) {System.out.println(t);}
	/**
	* debug function eprints templated t
	* @param T item to print to stderr
	*/
	public static <T> void eprintln(T t) {System.err.println(t);}
	/**
	* checks if string input is valid integer in options
	* @param final String user input to check if valid
	* @param final String[] options to check if user input is in
	* @return boolean true if valid input else false
	*/
	public static boolean validInput(final String userInput, final String options[])
	{
		try
		{
			int n = Integer.parseInt(userInput);
			return (n > 0 && n <= options.length);
		} catch(Exception e) {e.printStackTrace();} // lezy
		return false;
	}
	/**
	* format function
	* @return String line of 50 '='s
	*/
	public static String line()
	{
		StringBuilder buffer = new StringBuilder("\n");
		for(int i = 0; i < 50; i++) {buffer.append("=");}
		buffer.append("\n");
		return buffer.toString();
	}
	/**
	* converts menu to string and attaches prompt
	* @param final String[] menu of options
	* @param final String prompt to attach
	* @return String menu as string + prompt
	*/
	public static String attachPrompt(final String menu[], final String prompt)
	{
		StringBuilder buffer = new StringBuilder("");
		for(String s : menu) {buffer.append(s).append("\n");}
		buffer.append(line()).append(prompt);
		return buffer.toString();
	}
	/**
	* main method -- for info about args, check 'arg-list.txt'
	* @params final String[] args coming in
	*/
	public static void main(final String a[])
	{
		Utils.mkdir("menu");
		if(!Utils.fileExists(Utils.mergePath("menu", "main-menu.txt"))) {Utils.writeFile("1. Display passwords\n2. Create new password\n3. Add existing password\n4. Copy password to clipboard\n5. Remove password\n6. Display stored applications\n7. Update password\n8. Exit", Utils.mergePath("menu", "main-menu.txt"), false);}
		if(!Utils.fileExists(Utils.mergePath("menu", "create-password.txt"))) {Utils.writeFile("Enter application\nEnter username\nEnter email\nEnter length", Utils.mergePath("menu", "create-password.txt"), false);}
		if(!Utils.fileExists(Utils.mergePath("menu", "add-existing.txt"))) {Utils.writeFile("Enter password\nEnter application\nEnter username\nEnter email", Utils.mergePath("menu", "add-existing.txt"), false);}
		HashMap<String, String> menus = new HashMap<String, String>();
		menus.put("mm", Utils.mergePath("menu", "main-menu.txt"));
		menus.put("cp", Utils.mergePath("menu", "create-password.txt"));
		menus.put("ae", Utils.mergePath("menu", "add-existing.txt"));
		String userInput;
		Interface ui = null;
		ArrayList<String> args = new ArrayList<String>(Arrays.asList(a));
		boolean debugMode = false;
		if(args.contains("-debug")) {debugMode = true;}
		println(new StringBuilder("debug mode?: ").append(Boolean.toString(debugMode)).toString());
		println(new StringBuilder("size of args: ").append(Integer.toString(args.size())).toString());
		if(args.size() == 0 || args.contains("cli")) {ui = new Cli();}
		else if(args.contains("gui")) {ui = new Gui();}
		else {ui = new Cli();}
		ui.clear();
		do
		{
			String mainMenu[] = Utils.readFile(menus.get("mm")).split("\n");
			do
			{
				userInput = ui.getUserInput(attachPrompt(mainMenu, "Enter option number"));
				if(!validInput(userInput, mainMenu))
				{
					ui.displayString(new StringBuilder(userInput).append(" is not a valid input").toString());
					ui.promptEnter();
					ui.clear();
				}
			} while(!validInput(userInput, mainMenu));
			int mode = Integer.parseInt(userInput);
			ui.clear();
			switch(mode)
			{
				case(1):
				{
					// display passwords
					userInput = ui.getUserInput("Press enter to view all passwords or enter an application to view its information");
					if(userInput.equals(""))
					{
						String line;
						int count = 1;
						do
						{
							line = Utils.readLine(inputFile, count++);
							if(!line.equals(""))
							{
								String tok[] = line.split(",");
								Password p = new Password(tok[1], tok[0], tok[2], tok[3], tok[4]);
								ui.displayString(p.formatDisplay());
							}
						} while(!line.equals(""));
					}
					else
					{
						Password p = Utils.getPasswordFromApp(userInput, inputFile);
						if(p != null) {println(p.formatDisplay());}
						else {ui.displayString(new StringBuilder(userInput).append(" was not found").toString());}
					}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(2):
				{
					String menu[] = Utils.readFile(menus.get("cp")).split("\n");
					int count = 0;
					Password p = new Password();
					boolean cancel = false;
					for(String s : menu)
					{
						String temp = ui.getUserInput(s);
						if(temp.equals("c"))
						{
							cancel = true;
							break;
						}
						switch(count)
						{
							case(0):
							{
								p.setApplication(temp);
								break;
							}
							case(1):
							{
								p.setUsername(temp);
								break;
							}
							case(2):
							{
								p.setEmail(temp);
								break;
							}
							case(3):
							{
								try {p.setLength(Integer.parseInt(temp));}
								catch(Exception e) {p.setLength(15);}
								break;
							}
							default:
							{
								eprintln("error in creating new password!");
								System.exit(-3);
								break;
							}
						}
						count++;
					}
					if(!cancel)
					{
						if(!Utils.passwordExists(p.getApplication(), inputFile)) {Utils.writeFile(p.formatWrite(), inputFile, true);}
						else
						{
							// confirm overwrite
							userInput = ui.getYesNo(new StringBuilder("Overwrite password for ").append(p.getApplication()).append("?").toString());
							if(userInput.equals("y"))
							{
								int lineNumber = Utils.getPasswordLineNumber(p.getApplication(), inputFile);
								Utils.removeLine(inputFile, lineNumber);
								Utils.writeFile(p.formatWrite(), inputFile, true);
							}
						}
					}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(3):
				{
					String menu[] = Utils.readFile(menus.get("ae")).split("\n");
					int count = 0;
					Password p = new Password();
					boolean cancel = false;
					for(String s : menu)
					{
						String temp = ui.getUserInput(s);
						if(temp.equals("c"))
						{
							cancel = true;
							break;
						}
						switch(count)
						{
							case(0):
							{
								p.setPassword(temp);
								break;
							}
							case(1):
							{
								p.setApplication(temp);
								break;
							}
							case(2):
							{
								p.setUsername(temp);
								break;
							}
							case(3):
							{
								p.setEmail(temp);
								break;
							}
							default:
							{
								eprintln("error adding existing password");
								// write error to log
								System.exit(-4);
								break;
							}
						}
						count++;
					}
					if(!cancel)
					{
						if(!Utils.passwordExists(p.getApplication(), inputFile)) {Utils.writeFile(p.formatWrite(), inputFile, true);}
						else
						{
							userInput = ui.getYesNo(new StringBuilder("Overwrite password for ").append(p.getApplication()).append("?").toString());
							if(userInput.equals("y"))
							{
								int lineNumber = Utils.getPasswordLineNumber(p.getApplication(), inputFile);
								Utils.removeLine(inputFile, lineNumber);
								Utils.writeFile(p.formatWrite(), inputFile, true);
							}
						}
					}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(4):
				{
					// to clipboard
					String app = ui.getUserInput("Select application to copy password for");
					Password p = Utils.getPasswordFromApp(app, inputFile);
					if(p != null)
					{
						boolean b = p.toClipboard();
						if(b) {ui.displayString(new StringBuilder("Copied password for ").append(app).append(" to clipboard").toString());}
						else {ui.displayString(new StringBuilder("Failed to copy password for ").append(app).append(" to clipboard").toString());}
					}
					else {ui.displayString(new StringBuilder("Password for ").append(app).append(" was not found").toString());}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(5):
				{
					// remove password
					String app = ui.getUserInput("Select application to remove password for");
					boolean b = Utils.removeLine(inputFile, Utils.getPasswordLineNumber(app, inputFile));
					if(b) {ui.displayString(new StringBuilder("Password for ").append(app).append(" was deleted").toString());}
					else {ui.displayString(new StringBuilder("Password for ").append(app).append(" failed to delete").toString());}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(6):
				{
					// display stored applications
					if(debugMode)
					{
						int count = 1;
						ui.displayString(new StringBuilder("DEBUG -- contents of ").append(inputFile).append(line()).append(Utils.readFile(inputFile)).toString());
					}
					else
					{
						int count = 1;
						String line;
						ui.displayStringNf(new StringBuilder("Stored passwords stored for the following applications:").append(line()).toString());
						do
						{
							line = Utils.readLine(inputFile, count++);
							if(!line.equals(""))
							{
								String tok[] = line.split(",");
								ui.displayString(tok[0]);
							}
						} while(!line.equals(""));
					}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(7):
				{
					String app = ui.getUserInput("Enter application of password to update");
					Password p = Utils.getPasswordFromApp(app, inputFile);
					if(p != null)
					{
						boolean valid = false;
						int newLength = 0;
						do
						{
							userInput = ui.getUserInput("Enter length of new password or press enter to use same length");
							if(!userInput.equals(""))
							{
								try
								{
									newLength = Integer.parseInt(userInput);
									if(newLength > 0) {valid = true;}
									else {ui.displayString(new StringBuilder(userInput).append(" is not a valid length (>0)").toString());}
								} catch(Exception e) {ui.displayString(new StringBuilder(userInput).append(" is not a valid integer").toString());}
							}
							else
							{
								newLength = p.getLength();
								valid = true;
							}
						} while(!valid);
						p.setLength(newLength);
						if(Utils.removeLine(inputFile, Utils.getPasswordLineNumber(app, inputFile))) {Utils.writeFile(p.formatWrite(), inputFile, true);}
						else
						{
							// log error
							ui.displayString(new StringBuilder("Failed to update password for ").append(app).toString());
						}
					}
					else {ui.displayString(new StringBuilder("password for ").append(app).append(" was not found").toString());}
					ui.promptEnter();
					ui.clear();
					break;
				}
				case(8):
				{
					userInput = "e";
					break;
				}
				default:
				{
					eprintln("You should never reach here");
					// write error to log
					System.exit(-2);
				}
			}
		} while(!userInput.equals("e"));
		ui.clear();
		Utils.deleteFile(Utils.mergePath("menu", "main-menu.txt"));
		Utils.deleteFile(Utils.mergePath("menu", "create-password.txt"));
		Utils.deleteFile(Utils.mergePath("menu", "add-existing.txt"));
		Utils.deleteFile("menu");
		return;
	}
}
