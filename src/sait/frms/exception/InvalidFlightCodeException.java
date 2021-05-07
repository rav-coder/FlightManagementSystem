package sait.frms.exception;
/**
 * This class throws an exception if the user trys to find a flight without selecting all 
 * the dropdown fields
 *
 @Author: YunZe (David) Wei, Rafael Garcia Oporto
 */
public class InvalidFlightCodeException extends Exception
{
	public InvalidFlightCodeException()
	{
		super("Error: One or more invalid flight fields selected");
	}
}
