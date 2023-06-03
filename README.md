# Password Manager

## By John Wolf [<a href="mailto: jjwolf0330@tutanota.com">jjwolf0330@tutanota.com</a>]

### This is a lightweight Java password manager that will update passwords every 2 months
* This currently is a CLI program, the GUI is under development
* Being a Java program, this program requires Java to be installed (at least version 7)

## Contents:
1. Installation
2. Use
3. Troubleshooting

___

## Installation:
When installing this program, clone the repository

### If you have lost the JAR file, please do one of the following:

#### Windows/MacOS/Linux users:
Open your systems terminal in the directory
* Windows: PowerShell or CMD
* MacOS and Linux: Terminal

Enter the following commands:
```
javac classes/Main.java
jar cfm PasswordManager.jar manifest.mf classes/ ui/
```

#### Other Operating Systems
Currently, there is only a CLI version of the program. This may not work on your system. Please check your OS manual to see how to use the command line and to compile Java programs in .jar format

## Use
To run the program, use one of the following commands:

### Windows/MacOS/Linux:
Open your system's terminal in the directory of the JAR file

To run the program, run the following:
```
java -jar PasswordManager.jar
```

### Other Operating Systems
Please check your OS to see how to run a Java CLI in your operating system

## Troubleshooting
Please make sure you have all files cloned from the repository and that you are in the correct directory when compiling/running the JAR file

If you encounter a bug, please email me at <a href="mailto: jjwolf0330@tutanota.com">jjwolf0330@tutanota.com</a> and describe the bug and how you reached it in as much detail as you can
