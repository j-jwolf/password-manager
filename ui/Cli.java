package ui;

import java.util.Scanner;

public class Cli extends Interface
{
	private static Scanner userInput = new Scanner(System.in); // ui scanner for getting user input
	public Cli() {}
	/**
	* gets user input from provided prompt
	* @param final String user prompt
	* @return String user input
	*/
	public String getUserInput(final String prompt)
	{
		this.displayStringNf(new StringBuilder(prompt).append(": ").toString());
		return userInput.nextLine().toLowerCase();
	}
	/**
	* displays item to stdout -- use display String instead UNLESS you are attempting to display a non string item
	* @deprecated
	* @param final T object to display
	*/
	@Deprecated
	public <T> void displayItem(final T t) {System.out.println(t);}
	/**
	* displays string to stdout
	* @param final String string to display
	*/
	public void displayString(final String s) {System.out.println(s);}
	/**
	* displays string to stdout with no line feed
	* @param final String string to display
	*/
	public void displayStringNf(final String s) {System.out.print(s);}
	/**
	* clears screen with escape sequence
	*/
	public void clear()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	/**
	* prompts user to press enter -- used to hold information on screen until user is done viewing it
	*/
	public void promptEnter()
	{
		for(int i = 0; i < 50; i++) {System.out.print("=");}
		System.out.println("\nPress enter");
		userInput.nextLine();
	}
	/**
	* gets yes/no/cancel from user
	* @param final String user prompt
	* @return String y/n/c
	*/
	public String getYesNo(final String prompt)
	{
		String input;
		do
		{
			System.out.print(new StringBuilder(prompt).append(" (y/n): ").toString());
			input = userInput.nextLine().toLowerCase();
			System.out.println("");
			if(!input.equals("y") && !input.equals("n") && !input.equals("c")) {System.out.println(new StringBuilder(input).append(" is not valid response -- enter 'y' for yes, 'n' for no, or 'c' to cancel").toString());}
		} while(!input.equals("y") && !input.equals("n") && !input.equals("c"));
		return input;
	}
}
