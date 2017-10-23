/**
 * 
 */
package utilities;
import java.io.IOException;

import javax.swing.JOptionPane;
import exception.InvalidNoteException;
import note.Note;
import note.Piano;

/**
 * @author Floyd Almazar
 * @version 1.0
 * 
 * Class Description: A class called Melody that will read an input from the argument and play the next 12 semi-tones 
 * 					from the note input. It will also determine if the note entered can make an octave.
 */
public class Melody
{
	/**
	 * This is the MAIN METHOD that has the capacity to play an octave of a starting note entered by the user in the argument.
	 * Program Procedure:
	 * -- Inside the try and catch block...
	 * 		-- A new Piano object has been created
	 * 		-- A new Note object has been created and initialized to a "null" value
	 * 		-- Sets of variables has been created and initialized:
	 * 			- rest is an integer value initialized to 200
	 * 			- minusSign is a char value initialized to -
	 * 			- dot is a String value initialized to .
	 * 			- twoHundredMilli is a String value initialized to r
	 * 			- fourHundredMilli is a String value initialized to r-
	 * 		-- Conditional Statements:
	 * 			-- If the user did not enter anything in argument then...
	 * 				** a message dialog box will appear explaining how to enter values in argument and
	 * 				the it will throw an IndexOutOfBounds Exception
	 * 			-- If the input value in args is equal to - (minusSign) then..
	 * 				** make the value of rest into 400
	 * 				** change the value of args
	 * 			-- If the input value in args is not equal to twoHundredMilli
	 * 				** go inside another sets of conditions...
	 * 				-- If the input value of args has . (dot) then...
	 * 					** assign a new value for the note Object
	 * 				-- If the input value of args is a letter...
	 * 					** assign a new value for the note Object
	 * 				-- If the input value of args is not equal to fourHundredMilli then...
	 * 					** assign a new value for the note Object
	 * 				-- A new object called "currentNote" has been created to use for the compareTo method
	 * 				-- while the note compared to the currenNote is less than or equal to 0 then...
	 * 					** assign notePlayer to the method called playSong from the Piano class to play the notes
	 * 					** assign new value for note to get the next semi-tone until it reach the 12th semi-tone 
	 * 					to be added in the input note
	 * 					** additional items: 
	 * 						** a counter variable to monitor how many semi-tones has been added.
	 * @param args the argument where the inputs will be entered 
	 * in order to get next 12 notes starting at the entered note
	 */
	public static void main(String[] args)
	{
		try
		{
			Piano notePlayer = new Piano();
			Note note = null;

			int rest = 200;
			char minusSign = '-';
			String dot = ".";
			String twoHundredMilli = "r";
			String fourHundredMilli = "r-";
			
			if(args.length == 0)
			{
				JOptionPane.showMessageDialog(null, "Ooops! You forgot to put inputs on the Command Line/Argument!"
						+ "\n\nTo put values in argument, follow this steps:"
						+ "\n\nIf you are using Eclipse:"
						+ "\n1. Right-click on Melody.java"
						+ "\n2. Hover on 'Run As' and choose 'Run Configurations..."
						+ "\n***A window will pop up***"
						+ "\n3. Click 'Arguments' tab"
						+ "\n4. Under 'Program arguments' type the desired input."
						+ "\n\nIf you are using the comand line:"
						+ "\nType the desired input after the Melody.java and hit enter"
						+ "\n\n\n                                          HAVE FUN!!!");
				throw new IndexOutOfBoundsException();
			}
			else
			{
				if(args[0].charAt(args[0].length()-1) == minusSign)
				{
					rest = 400;
					args[0] = args[0].substring(0, args[0].length()-1);
				}
				
				if(!args[0].equals(twoHundredMilli))
				{
					if(args[0].contains(dot))
					{
						note = new Note(Float.parseFloat(args[0]));
					}
					else if(Character.isLetter(args[0].charAt(0)))
					{
						note = new Note(args[0]);
					}
					else if(!args[0].equals(fourHundredMilli))
					{
						note = new Note(Integer.parseInt(args[0]));
					}
	
					System.out.println("\nArgs Input Value: " + args[0]+"\n");
					System.out.println("Display Notes:");
					
					int one_octave = 12;
					Note currentNote = new Note(note.getMIDIValue() - Note.CONCERT_PITCH_MIDI + one_octave);
					
					int counter = 0;
					while(note.compareTo(currentNote) <= 0)
					{
						System.out.println(counter+". " + "Note: " + note.getMIDIValue() + " || Rest: " + rest);
						System.out.println("******************************");
						notePlayer.playSong(rest, note);
						note = new Note(note.getMIDIValue() - Note.CONCERT_PITCH_MIDI + 1);
						counter++;
					}
				}
				else
				{
					System.out.println("No notes to has been entered.");
					notePlayer.noteRest(rest);
				}
			}
		}
		catch (InvalidNoteException error)
		{
			JOptionPane.showMessageDialog(null, "WAIT A MINUTE!!!\n"+args[0]+" is INVALID!");
			JOptionPane.showMessageDialog(null, "INVALID NOTE has been discarded. Press ok to continue\n");
			System.out.println("\n\n\n****VALID INPUTS****\n\nFor Frequency Input:\n"
					+ "a. 440.00\n"
					+ "b. 396.80\n"
					+ "c. 275.88\n"
					+ "\nFor Common Music Note:\n"
					+ "a. A4\n"
					+ "b. A-1\n"
					+ "c. Ab-1 or A#-1\n"
					+ "d. A4b or A4#\n"
					+ "e. A-1b or A-1#\n\n"
					+ "For MIDI Absolute Number:\n"
					+ "Any numbers between 1-45");
		}
	}
}