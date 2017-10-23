/**
 * 
 */
package note;

import java.util.HashMap;

import exception.InvalidNoteException;

/**
  * This is the contract specification for a musical note.
  * A note can be completely specified as one of the following:
  * 1. The frequency (number of cycles per second (in Hz)).
  * OR
  * 2. The number of half steps above a commonly agreed upon pitch
  *    (concert pitch=440Hz = A = MIDI 69).
  * OR
  * 3. The common music note name (C, D, E, F, G, A, B) with the
  *    the suffix indicating the stringNote number [-1,9] and an additional
  *    suffix prefix '#' for sharp notes and 'b' for flat notes.
  * OR
  * 4. The MIDI value [0,127] where 60 is middle C.
  *
  * Higher notes have higher frequencies. Two notes are an stringNote apart
  * (12 half steps) if one's frequency is twice the other. A half step is
  * approximately an increase in pitch (frequency) of 1.06 times higher.
  */
public abstract class NoteADT implements Comparable<NoteADT>
{
	//Constants
	/**
	 * A half step is approximately an increase in pitch of 1.06 times higher.
	 */
	public static final double HALFSTEP_INCREASE_IN_PITCH = Math.pow(2.0,1.0/12.0);
	
	/**
	 * The agreed upon pitch (note) "modern concert pitch".
	 */
	public static final double CONCERT_PITCH_FREQUENCY = 440.0; //Hz
	public static final int    CONCERT_PITCH_MIDI = 69;
	  
	/**
	 * The high and low limits on the range of midi values.
	 */
	public static final int    LOW_MIDI_VALUE = 0;
	public static final int    HIGH_MIDI_VALUE = 127;
	  
	//Attributes
	protected int 				midiValue = -1;
	double 						frequency;
	String 						stringNote;
	int 						noteNumber; 
	
	/**
	 * Note constructor that takes in frequency in cycles per second.
	 * -- Reference of the formula of getting the Standard MIDI Tuning
	 * 		** https://en.wikipedia.org/wiki/MIDI_Tuning_Standard
	 * @param frequency a Double value representing the frequency
	 * @throws InvalidNoteException handles the invalid note 
	 */
	public NoteADT(double frequency) throws InvalidNoteException
	{
		// Math.round = any results of this equation will be rounded into the nearest ones
		noteNumber = (int) Math.round(CONCERT_PITCH_MIDI + (12 * Math.log(frequency/CONCERT_PITCH_FREQUENCY)
				/ Math.log(2))); 
		checkMidiRange(noteNumber);
		// Underneath is the print test to see the values I get if the program goes for this condition
		//System.out.println("Input hz--value of noteNumber:"+noteNumber);
	}

	/**
	 * Note constructor that takes octaves.
	 * Program Procedure:
	 * 		-- Declare variables:
	 * 			** noteLetter
	 * 			** octaveNumber
	 * 			** flatOrSharp
	 * 		-- set the input coming in the variable "stringNote" into uppercase
	 * 			** this will prevent the program from throwing invalid note exception from invalid specific letter
	 * 		-- Create a HashMap Object called "notesChart"
	 * 			| C  | 0  |
	 * 			| C# | 1  |
	 * 			| Db | 1  |
	 * 			| D  | 2  |
	 * 			| D# | 3  |
	 * 			| Eb | 3  |
	 * 			| E  | 4  |
	 * 			| F  | 5  |
	 * 			| F# | 6  |
	 * 			| Gb | 6  |
	 * 			| G  | 7  |
	 * 			| G# | 8  |
	 * 			| Ab | 8  |
	 * 			| A  | 9  |
	 * 			| A# | 10 |
	 * 			| Bb | 10 |
	 * 			| B  | 11 |
	 * 			| B# | 0  |
	 * 		-- Conditional Statements: These are the sets of if conditions to test and determine the format of input from the
	 * 		the argument that will be assigned to the variable "stringNote" using "matches" and then the regular expressionss.
	 * 			1. If the stringNote matches this format: 
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a number from 0 - 9
	 * 				* example: A4
	 * 			2. If the stringNote matches this format: 
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a character either b or #
	 * 				* example: Ab or A#
	 * 			3. If the stringNote matches this format: 
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a minus sign ( - )
	 * 				* index 2: number 1
	 * 				* example: A-1
	 * 			4. If the stringNote matches this format: 
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a character either b or #
	 * 				* index 2: a minus sign ( - )
	 * 				* index 3: number 1
	 * 				* example: Ab-1 or A#-1
	 * 			5. If the stringNote matches this format: 
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a character either b or #
	 * 				* index 2: a number between 0 - 9
	 * 				* example: Ab5 or A#5
	 * 			6. If the stringNote matches this format: 
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a number between 0 - 9
	 * 	 			* index 2: a character either b or #
	 * 				* example: A5b or A5#
	 * 			7. If the stringNote matches this format:
	 * 				* index 0: a letter from A - G
	 * 				* index 1: a minus sign ( - ) 
	 * 				* index 2: number 1 
	 * 				* index 3: a character either b or #
	 * 			-- else, if none of these conditions match...
	 * 				** throw an InvalidNoteException
	 *		-- Get the default value of the noteNumber and throw an NullPointerException
	 *		if the noteNumber is not in the Note Chart
	 * @param stringNote a String object representing the music note
	 * @throws InvalidNoteException an exception that handles an invalid note input
	 */
	public NoteADT(String stringNote) throws InvalidNoteException
	{
		String noteLetter = stringNote.charAt(0) + ""; // noteLetter: C,D,E,F,G,A,B
		int octaveNumber = -2; // octaveNumber: -1,0,1,2,3,4,5,6,7,8,9
		char flatOrSharp = '\0'; // flatOrSharp: #,b
		stringNote = stringNote.toUpperCase();
		
		HashMap<String, Integer> notesChart = new HashMap<String, Integer>();
		notesChart.put("C", 0);
		notesChart.put("C#", 1);
		notesChart.put("DB", 1);
		notesChart.put("D", 2);
		notesChart.put("D#", 3);
		notesChart.put("EB", 3);
		notesChart.put("E", 4);
		notesChart.put("F", 5);
		notesChart.put("F#", 6);
		notesChart.put("GB", 6);
		notesChart.put("G", 7);
		notesChart.put("G#", 8);
		notesChart.put("AB", 8);
		notesChart.put("A", 9);
		notesChart.put("A#", 10);
		notesChart.put("BB", 10);
		notesChart.put("B", 11);
		notesChart.put("B#", 0); 
		
		// test the notes being read
		if(stringNote.matches("([A-G])([0-9])"))// Test: G4
		{
			noteLetter = stringNote.charAt(0) + "";
			octaveNumber = Character.getNumericValue(stringNote.charAt(1));
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A1");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("octaveNumber value: "+octaveNumber);
			
		}
		else if(stringNote.matches("([A-G])([B#])"))// Test: Gb / G#
		{
			noteLetter = stringNote.charAt(0) + "";
			flatOrSharp = stringNote.charAt(1);
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A1");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("flatOrSharp value: "+flatOrSharp);
			
		}
		else if(stringNote.matches("([A-G])([-])([1])"))// Test: G-1
		{
			noteLetter = stringNote.charAt(0)+"";
			octaveNumber = midiValue;
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A-1");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("octaveNumber value: "+octaveNumber);
			
		}
		else if(stringNote.matches("([A-G])([B#])([-])([1])"))// Test: Gb-1 or G#-1
		{
			noteLetter = stringNote.charAt(0)+"";
			flatOrSharp = stringNote.charAt(1);
			octaveNumber = midiValue;
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A#-1 or Ab-1");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("flatOrSharp value: "+flatOrSharp);
			//System.out.println("octaveNumber value: "+octaveNumber);
		}
		else if(stringNote.matches("([A-G])([B#])([0-9])"))// Test: Gb5 or G#5
		{
			noteLetter = stringNote.charAt(0) + "";
			flatOrSharp = stringNote.charAt(1);
			octaveNumber = Character.getNumericValue(stringNote.charAt(2));
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A#5 or Ab5");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("flatOrSharp value: "+flatOrSharp);
			//System.out.println("octaveNumber value: "+octaveNumber);
		}
		else if(stringNote.matches("([A-G])([0-9])([B#])"))// Test: G5#
		{
			noteLetter = stringNote.charAt(0) + "";
			octaveNumber = Character.getNumericValue(stringNote.charAt(1));
			flatOrSharp = stringNote.charAt(2);
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A5# or A5b");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("octaveNumber value: "+octaveNumber);
			//System.out.println("flatOrSharp value: "+flatOrSharp);
		}
		
		else if(stringNote.matches("([A-G])([-])([1])([B#])"))// Test: A-1# or A-1b
		{
			noteLetter = stringNote.charAt(0)+"";
			octaveNumber = -1;
			flatOrSharp = stringNote.charAt(3);
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("Test for A#-1 or Ab-1");
			//System.out.println("noteLetter value: "+noteLetter);
			//System.out.println("octaveNumber value: "+octaveNumber);
			//System.out.println("flatOrSharp value: "+flatOrSharp);
		}
		else
		{
			throw new InvalidNoteException("Values are not in range!");
		}
		// get the default value of the note
		try
		{
			noteNumber = notesChart.get(noteLetter + (flatOrSharp > 0 ? flatOrSharp : "")) + (12*(octaveNumber+1));
			// Underneath are the print test to see the values I get if the program goes for this condition
			//System.out.println("\nMidi Value of the Note: "+noteNumber);
			//System.out.println("Octave number: "+octaveNumber);
			checkMidiRange(noteNumber);
		}
		catch (NullPointerException e)
		{
			throw new InvalidNoteException("The Value is not in the Note Chart!");
		}
	}
	
	/**
	 * Note constructor that takes in semi-tones.
	 * @param halfSteps an integer value representing the semi tone
	 * @throws InvalidNoteException handles the invalid note
	 */
	public NoteADT(int halfSteps) throws InvalidNoteException
	{
		noteNumber = CONCERT_PITCH_MIDI + halfSteps;
		checkMidiRange(noteNumber);
		// Underneath is the print test to see the values I get if the program goes for this condition
		//System.out.println("halfSteps--value of noteNumber: "+noteNumber);
	}
	
	public static void checkMidiRange(int noteNumber ) throws InvalidNoteException
	{
		if(noteNumber<LOW_MIDI_VALUE || noteNumber>HIGH_MIDI_VALUE)
		{
			throw new InvalidNoteException("Sorry, the note entered is out of range.");
		}
	}
	
	/**
	   * Transformer method that returns the frequency of note in cycles per second - Hertz (Hz)
	   * 
	   * Preconditions: A valid NoteADT object exists.
	   * 
	   * Postconditions: A double value representing the frequency of the note is returned.
	   * 
	   * @return The frequency of note in Hz.
	   */
	   public abstract double getFrequencyInHz();
	
	
	/**
	 * Accessor method to get the frequency in half steps.
	 * 
	 * Precondition: A valid note object exists.
	 * 
	 * Postcondition: A double value representing the current semi-tone value.
	 * 
	 * @return the integer value representing this note's semi tones.
	 */
	public abstract int getHalfSteps();
	
	/**
	 * Accessor method that returns the MIDI value of note.
	 * 
	 * Preconditions: A valid NoteADT object exists.
	 * 
	 * Postconditions: An integer value representing the MIDI value of the note is returned.
	 *  
	 * @return The MIDI value
	 */
	public abstract int getMIDIValue();
	
	/**
	 * Accessor method to find out if the two notes form an stringNote.
	 * 
	 * Precondition: The paramitized note must be a valid note with midi values 
	 * 				ranging from 0 - 127.
	 * 
	 * Postcondition: The boolean value indicating whether this note is an
	 * 				octaval or not.
	 * 
	 * @param note the note used to determine if this note is octaval
	 * @return boolean value representing whether this is an octaval or not
	 */
	public abstract boolean formOctave(NoteADT note);
	
	/**
	 * Mutator method that modifies the note by number of semi-tones.
	 * 
	 * Precondition: A valid integer value representing a semi-tone ranging from 0 - 127.
	 * 
	 * Postcondition: This note is modified depending on the range of the 
	 * 					semi-tone.
	 * 
	 * @param halfSteps an integer value between 0 - 127
	 * @throws InvalidNoteException handles the invalid note
	 */
	public abstract void modifyNoteByHalfSteps(int halfSteps) throws InvalidNoteException;
	
	/**
	 * Accessor method to compare the notes using the Comparable interface.
	 * 
	 * Precondition: A valid Note object to compare to the current Note object.
	 * 
	 * Postcondition: An integer value representing the distance of the two
	 * 				notes in half steps.
	 * @param note the note being compared.
	 * @return the distance of the two notes being compared
	 */
	@Override
	public abstract int compareTo(NoteADT note);
}