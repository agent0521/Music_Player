/**
 * 
 */
package note;

import exception.InvalidNoteException;

/**
 * @author Floyd Almazar
 * @version 1.0
 * 
 * Class Description: This is the note Class where the implementation of the methods in the NoteADT is being used. 
 */
public class Note extends NoteADT
{
	/**
	 * Note constructor that takes octaves.
	 * @param stringNote a String object representing the music note
	 * @throws InvalidNoteException an exception that handles an invalid note input
	 */
	public Note(String stringNote) throws InvalidNoteException
	{
		super(stringNote);
	}
	
	/**
	 * Note constructor that takes in semi-tones.
	 * @param halfSteps an integer value representing the semi tone
	 * @throws InvalidNoteException handles the invalid note
	 */
	public Note(int halfSteps) throws InvalidNoteException
	{
		super(halfSteps);
	}
	
	/**
	 * Note constructor that takes in frequency in cycles per second.
	 * -- Reference of the formula of getting the Standard MIDI Tuning
	 * 		** https://en.wikipedia.org/wiki/MIDI_Tuning_Standard
	 * @param frequency a Double value representing the frequency
	 * @throws InvalidNoteException handles the invalid note
	 */
	public Note(double frequency) throws InvalidNoteException
	{
		super(frequency);
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
	@Override
	public double getFrequencyInHz()
	{
		return noteNumber = (int) Math.round(CONCERT_PITCH_MIDI + (12 * Math.log(frequency/CONCERT_PITCH_FREQUENCY)
				/ Math.log(2)));
	}
	
	/**
	 * Accessor method to get the frequency in half steps.
	 * 
	 * Precondition: A valid note object exists.
	 * 
	 * Postcondition: A double value representing the current semi-tone value.
	 * 
	 * @return the integer value representing this note's semi tones.
	 */
	@Override
	public int getHalfSteps()
	{
		return noteNumber;
	}

	/**
	 * Accessor method to provide its frequency in MIDI absolute numbers.
	 * 
	 * Precondition: A valid note object exists.
	 * 
	 * Postcondition: An integer value representing the current midi absolute note number.
	 * 
	 * @return the integer value representing this note's midi absolute number 
	 */
	@Override
	public int getMIDIValue()
	{
		return noteNumber;
	}

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
	@Override
	public boolean formOctave(NoteADT note)
	{
		boolean flag = false;
		
		if(this.getMIDIValue() - note.getMIDIValue() == 12 || 
				this.getMIDIValue() - note.getMIDIValue() == -12)
		{
			flag=true;
			return flag;
		}
		else
		{
			return flag;
		}
	}
	
	/**
	 * Mutator method that modifies the note by number of semi-tones.
	 * 
	 * Precondition: A valid integer value representing a semi-tone ranging from 0 - 127.
	 * 
	 * Postcondition: This note is modified depending on the range of the 
	 * 					semi-tone.
	 * 
	 * @param halfSteps an integer value between 0 - 127
	 */
	@Override
	public void modifyNoteByHalfSteps(int halfSteps)
	{
		try
		{
			noteNumber = noteNumber + halfSteps;
			checkMidiRange(noteNumber);
			
		}catch (InvalidNoteException e)
		{
			e.printStackTrace();
			noteNumber = noteNumber - halfSteps;
		}
		
		
	}
	
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
	public int compareTo(NoteADT note)
	{
		return this.getMIDIValue() - note.getMIDIValue();
	}

}
