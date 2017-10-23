/**
 * 
 */
package utilities;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.InvalidNoteException;
import note.Note;

public class NoteTest
{
	private Note note;
	
	@Before
	public void setUp()
	{
		Note note;
	}
	
	@After
	public void tearDown()
	{
		note = null;
	}
	
	
	@Test
	public void testStringNote()
	{
		String n="C4";
		try
		{
			note = new Note(n);
		} 
		catch (InvalidNoteException e)
		{
			e.printStackTrace();
		}
		if(note.getMIDIValue() == 60)
		{
			assertTrue(true);
		}
		else
		{
			fail("Midi value incorrect");
		}
	}
	
	@Test
	public void testFrequencyNote()
	{
		double n = 440.00;
		try
		{
			note = new Note(n);
		} 
		catch (InvalidNoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(note.getMIDIValue() == 69)
		{
			assertTrue(true);
		}
		else
		{
			fail("Midi value incorrect");
		}
	}
	
	@Test
	public void testHalfSteps()
	{
		int n = 1;
		try
		{
			note = new Note(n);
		} 
		catch (InvalidNoteException e)
		{
			e.printStackTrace();
		}
		if(note.getMIDIValue() == 70)
		{
			assertTrue(true);
		}
		else
		{
			fail();
		}
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testStringNoteOver() throws InvalidNoteException
	{
		note = new Note("Z5");
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testStringNoteUnder() throws InvalidNoteException
	{
		note = new Note("A-5");
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testFrequencyNoteOver() throws InvalidNoteException
	{
		note = new Note(1000000.00);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testFrequencyNoteUnder() throws InvalidNoteException
	{
		note = new Note(0.224232);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testSemiToneOver() throws InvalidNoteException
	{
		note = new Note(69);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testSemiToneUnder() throws InvalidNoteException
	{
		note = new Note(-70);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testNoteStringMorethanOneLetter() throws InvalidNoteException
	{
		note = new Note("AAA");
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testNoteStringMoreSharps() throws InvalidNoteException
	{
		note = new Note("A##");
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testNoteStringTwoSharpsOneNumber() throws InvalidNoteException
	{
		note = new Note("A#1#");
	}
	
	@Test(expected = InvalidNoteException.class)
	public void testNoteStringTwoNumbers() throws InvalidNoteException
	{
		note = new Note("A24");
	}
	
	@Test
	public void testInvalidNoteExceptionString()
	{
		try
		{
			note = new Note("A#9");
			fail("Note constructor should have thrown an exception");
		}
		catch(InvalidNoteException errorEvent)
		{
			assertNotNull(errorEvent);
		}
	}
	
	@Test
	public void testInvalidNoteExceptionFrequency()
	{
		try
		{
			note = new Note(2.0);
			fail("Note constructor should have thrown an exception");
		}
		catch(InvalidNoteException errorEvent)
		{
			assertNotNull(errorEvent);
		}
	}
	
	@Test
	public void testInvalidNoteExceptionSemitones()
	{
		try
		{
			note = new Note(-80);
			fail("Note constructor should have thrown an exception");
		}
		catch(InvalidNoteException errorEvent)
		{
			assertNotNull(errorEvent);
		}
	}
	
	@Test
	public void testGetMIDIValue()
	{
		int n = 0;
		try
		{
			note = new Note(n);
		} 
		catch (InvalidNoteException e)
		{
			e.printStackTrace();
		}
		if(note.getMIDIValue() != 0)
		{
			assert(true);
		}
		else
		{
			fail("It must throw an invalid note");
		}
	}
	
	@Test
	public void testGetHalfSteps()
	{
		int n = 0;
		try
		{
			note = new Note(n);
		} catch (InvalidNoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(note.getMIDIValue() == 69)
		{
			assert(true);
		}
		else
		{
			fail();		
		}
	}
	
	@Test(expected=InvalidNoteException.class)
	public void testCompareTo() throws InvalidNoteException
	{
		note = new Note(70);
	}
	
	
}
