package ui;

/*

This is an abstract class, it is useless on its own. DO NOT INSTANTIATE. It is protected so you will get an error if you attempt to

call Cli or Gui instead:
	Interface ui = new Cli();
	OR
	Interface ui = new Gui();

*/

public class Interface
{
	protected Interface() {}
	public String getUserInput(final String prompt) {return "interface type not specified -- instantiate using Interface interface = new Cli() || Interface interface = new Gui()";}
	public <T> void displayItem(final T t) {}
	public void displayString(final String s) {}
	public void displayStringNf(final String s) {}
	public void clear() {}
	public void promptEnter() {}
	public String getYesNo(final String s) {return "interface type not specified";}
}
