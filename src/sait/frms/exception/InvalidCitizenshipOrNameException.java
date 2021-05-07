package sait.frms.exception;
/**
 * This class throws an exception if the user enters a name or citizenship that is null
 * or empty
 *
 @Author: YunZe (David) Wei, Rafael Garcia Oporto
 */
public class InvalidCitizenshipOrNameException extends Exception
{
	public InvalidCitizenshipOrNameException()
	{
		super("Error: Invalid Name or Citizenship entry");
	}
}
