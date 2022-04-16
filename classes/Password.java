package classes;

import java.util.Date;
import java.util.Calendar;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Password
{
	private String password, application, username, email, date;
	private int length;
	/**
	* generates new password with length stored in instance.length
	*/
	private void generatePassword()
	{
		StringBuilder buffer = new StringBuilder("");
		String lower = "abcdefghijklmnopqrstuvwxyz", upper = lower.toUpperCase(), nums = "1234567890", chars = "!@#$%^+";
		int weight;
		SecureRandom r = new SecureRandom();
		for(int i = 0; i < this.length; i++)
		{
			weight = r.nextInt(100);
			if(weight < 25) {buffer.append(lower.charAt(r.nextInt(lower.length())));}
			else if(weight < 50) {buffer.append(upper.charAt(r.nextInt(upper.length())));}
			else if(weight < 75) {buffer.append(nums.charAt(r.nextInt(nums.length())));}
			else {buffer.append(chars.charAt(r.nextInt(chars.length())));}
		}
		this.password = buffer.toString();
	}
	/**
	* creates a new password, caller functino for generatePassword()
	* @param final int length of password to generate
	*/
	private void createNewPassword(final int length)
	{
		this.length = length;
		this.date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		generatePassword();
	}
	/**
	* checks if date of password is 2 months and creates new password if it is
	*/
	private void checkDate()
	{
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		String update;
		try
		{
			c.setTime(f.parse(date));
			c.add(Calendar.DAY_OF_MONTH, 60);
			update = f.format(c.getTime());
			Date nextUpdate = f.parse(update), current = f.parse(date);
			if(current.compareTo(nextUpdate) > 0) {this.generatePassword();}
			System.out.println(new StringBuilder("Updated password for ").append(this.application).toString());
		} catch(ParseException e) {e.printStackTrace();}
		return;
	}
	/**
	* default constructor
	*/
	public Password()
	{
		application = username = password = email = "";
		length = 0;
		date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	}
	/**
	* constructor for new password
	*/
	public Password(final String application, final String username, final String email, final int length)
	{
		this.application = application;
		this.username = username;
		this.email = email;
		this.length = length;
		this.date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		this.password = "";
		generatePassword();
	}
	/**
	* constructor for existing password with date of day created
	*/
	public Password(final String password, final String application, final String username, final String email)
	{
		this.password = password;
		this.application = application;
		this.username = username;
		this.email = email;
		this.length = password.length();
		this.date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	}
	/**
	* constructor for existing password with existing date -- used for passwords read in from file
	*/
	public Password(final String password, final String application, final String username, final String email, final String date)
	{
		this.password = password;
		this.application = application;
		this.username = username;
		this.email = email;
		this.length = password.length();
		this.date = date;
		checkDate();
	}
	/**
	* test function -- debug only, shows all member variables of this instance
	*/
	public void test() {System.out.println(new StringBuilder("password: ").append(password).append("\napplication: ").append(application).append("\nusername: ").append(username).append("\nemail: ").append(email).append("\ndate created: ").append(date).append("\nLength: ").append(Integer.toString(length)).toString());}
	/**
	* password setter
	* @param final String password
	*/
	public void setPassword(final String password)
	{
		this.password = password;
		length = password.length();
	}
	/**
	* application setter
	* @param final String application
	*/
	public void setApplication(final String application) {this.application = application;}
	/**
	* username setter
	* @param final String username
	*/
	public void setUsername(final String username) {this.username = username;}
	/**
	* email setter
	* @param final String email
	*/
	public void setEmail(final String email) {this.email = email;}
	/**
	* date setter -- avoid manual date setting
	* @param final Date date object
	* @deprecated
	*/
	@Deprecated
	public void setDate(final Date date) {this.date = new SimpleDateFormat("MM/dd/yyyy").format(date);}
	/**
	* date setter -- sets to today's date
	*/
	public void setDateToday() {this.date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());}
	/**
	* length setter -- creates new password of this length
	* @param final int length
	*/
	public void setLength(final int length) {createNewPassword(length);}
	/**
	* password getter
	* @return final String password
	*/
	public final String getPassword() {return password;}
	/**
	* application getter
	* @return final String application
	*/
	public final String getApplication() {return application;}
	/**
	* username getter
	* @return final String username
	*/
	public final String getUsername() {return username;}
	/**
	* email getter
	* @return final String email
	*/
	public final String getEmail() {return email;}
	/**
	* date getter
	* @return final String date
	*/
	public final String getDate() {return date;}
	/**
	* length getter
	* @return final int length
	*/
	public final int getLength() {return length;}
	/**
	* gets string of this passwords member variables formatted for displaying to user
	* @return String formatted data for displaying
	*/
	public String formatDisplay() {return new StringBuilder("Application: ").append(application).append("\n\tUsername: ").append(username).append("\n\tPassword: ").append(password).append("\n\tEmail: ").append(email).append("\n\tDate created: ").append(date).toString();}
	/**
	* gets string of this passwords member variables formatted for writing to storage
	* @return String formatted data for storage
	*/
	public String formatWrite() {return new StringBuilder(application).append(",").append(password).append(",").append(username).append(",").append(email).append(",").append(date).toString();}
	/**
	* copies password to system clipboard
	*/
	public boolean toClipboard()
	{
		try
		{
			StringSelection s = new StringSelection(this.password);
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			c.setContents(s, s);
			return true;
		} catch(Exception e) {e.printStackTrace();}
		return false;
	}
}
