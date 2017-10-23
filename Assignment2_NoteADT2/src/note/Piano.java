package note;

import javax.sound.midi.*;

import exception.InvalidNoteException;

public class Piano
{
	//Constants
	public static final int		HIGHEST_MIDI_VALUE = 127;
	public static final int 	LOWEST_MIDI_VALUE = 0;
	
	//Attributes
	Synthesizer synthesizer;
	Instrument [] instruments;
	MidiChannel [] channels;
	
	//Constructor
	public Piano()
	{
		try
		{
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
		}
		catch(MidiUnavailableException mue)
		{
			mue.printStackTrace();
		}
			instruments = synthesizer.getDefaultSoundbank().getInstruments();
			synthesizer.loadInstrument(instruments[30]);
			channels = synthesizer.getChannels();
	}
	
    public void playSong(int rest, NoteADT musicNote)
    {
    	channels[1].noteOn(musicNote.getMIDIValue(), HIGHEST_MIDI_VALUE);
		channels[1].programChange(1);
		channels[1].noteOff(musicNote.getMIDIValue());
		
		try
		{
			Thread.sleep(rest);	
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
    }
 
    public void noteRest(int rest)
	{
		try
		{
			Thread.sleep(rest);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
    
    public void playAllNotes()
    {
    	for(int i=1; i<=127; i++)
    	{
	       channels[1].noteOn(i, 127);
	       //sets the instrument to play the note.
	       channels[1].programChange(12);
	       System.out.println(i);
	       try
	       {
		    Thread.sleep(100);
	       }
	       catch(InterruptedException ie)
	       {
	    	   ie.printStackTrace();
	       }
	       channels[1].noteOff(i,127);
    	}
    }
    
}