package sait.frms.exception;
/**
 * This class throws an exception if the user trys to book a flight that has no more seats
 *
 @Author: YunZe (David) Wei, Rafael Garcia Oporto
 */
public class NoMoreSeatsException extends Exception 
{
	public NoMoreSeatsException()
	{
		super("Error: No more seats available for this flight");
	}
}
