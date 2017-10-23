/**
 * 
 */
package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import exception.InvalidNoteException;
import note.Note;
import note.Piano;

/**
 * @author Floyd Almazar
 * @version 1.0
 * 
 * Class Description: A class called Song that will read an input from a text file from the res folder and play the next string 
 * 					that will be scanned within the file.
 */
public class Song
{
	/**
	 * This is the MAIN method of the class Song that will scan the strings in a .txt file located at the 
	 * res folder to play a song. 
	 * 
	 * PROGRAM PROCEDURE:
	 * 	-- A new Piano object has been created as midiPlayer
	 * 	-- Note object has been created as note that is initialized to null value
	 *  -- The program will then display a message to let the user enter the name of the text file of the song
	 *  -- A new Scanner object has been created an named as title to search for the song title in the res folder.
	 *  -- A new File object has been created and assigned to songs 
	 *  -- Another new Scanner object has been created in order to search into the file object named "song"
	 * LOOP PROCEDURE:
	 * 	-- while the searchSong has next line...
	 * 		** A new StringTokenizer object has been created called "st" that will read each batch of strings
	 * 		separated by comma.
	 * 		-- another while loop has been created to search for other strings inside that the variable "st" is holding.
	 * 		** inside the try block
	 * 			-- different variables has been declared that will be used for the methods inside the try block
	 * 			-- if statements has been implemented in order to check for the other possible conditions
	 * 			-- else execute the noteRest method to pass in the rest value to the midiPlayer
	 * 			-- another if statement to read if there is a string has a minus sign (-) in the first index
	 * 		** catch the InvalidNoteException if there is no match in the statements and conditions.
	 * 	-- close searchSong and title
	 * 		** catch the FileNotFoundException to handle the error if the file name is not entered correctly.
	 * @param args argument
	 */
	public static void main(String[] args)
	{
		try
		{
			Piano midiPlayer = new Piano();
			Note note = null;
			String invalidMessage = "Ooops! Invalid note entry has been skipped!\n\n";
			System.out.println("Enter the name of the text file of the song: ");
			
			Scanner title = new Scanner(System.in);
			File songs = new File("res/" + title.nextLine());
			
			Scanner searchSong = new Scanner(songs);
			while(searchSong.hasNextLine())
			{
				StringTokenizer st = new StringTokenizer(searchSong.nextLine(), ",");
				while(st.hasMoreTokens())
				{
					try
					{
						String midiString = st.nextToken();
						int rest = 200;
						char minusSign = '-';
						String dot = ".";
						String twoHundredMilli = "r";
						String fourHundredMilli = "r-";
						
						if(midiString.charAt(midiString.length()-1) == minusSign)
						{
							rest = 400;
							midiString = midiString.substring(0, midiString.length()-1);
						}
						
						if(!midiString.equals(twoHundredMilli))
						{
							if(Character.isLetter(midiString.charAt(0)))
							{
								note = new Note(midiString);
							}
							else if(!midiString.equals(fourHundredMilli))
							{
								note = new Note(Double.parseDouble(midiString));
							}
							else if(midiString.contains(dot))
							{
								note = new Note(Float.parseFloat(midiString));
							}
							midiPlayer.playSong(rest, note);
						}
						else
						{
							midiPlayer.noteRest(rest);
						}
						
						if(midiString.charAt(0) == minusSign)
						{
							System.out.println(invalidMessage);
							throw new InvalidNoteException();
						}
						System.out.println("Midi Note: "+midiString+"\t|| "+"Rest: "+rest);
					}
					catch (InvalidNoteException e)
					{
						System.out.println(invalidMessage);
					}
				}
			}
			searchSong.close();
			title.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
