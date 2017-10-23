/**
 * 
 */
package exception;

/**
 * @author Floyd Almazar
 * @version 1.0
 * 
 * Class Description:
 */
public class InvalidNoteException extends Exception
{
	public InvalidNoteException()
	{
		
	}
	
	public InvalidNoteException(String message)
	{
		super(message);
	}
}
