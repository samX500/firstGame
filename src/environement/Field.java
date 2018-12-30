package environement;

import character.Characters;

public class Field
{

	private static int DEFAULT_LENGHT = 25;

	public static final int MIN_LENGHT = 5;
	public static final int MAX_LENGHT = 100;

	private static int lenght;

	public Field()
	{
		lenght = (int) (Math.random() * (MAX_LENGHT - MIN_LENGHT + 1) + MIN_LENGHT);
	}

	public Field(int pLenght)
	{
		if (validateLenght(pLenght))
		{
			setLenght(pLenght);
		} else
		{
			setLenght(DEFAULT_LENGHT);
		}
	}

	public static int getLenght()
	{
		return lenght;
	}

	private static void setLenght(int pLenght)
	{
		if (validateLenght(pLenght))
		{
			lenght = pLenght;
		}
	}

	private static boolean validateLenght(int pLenght)
	{
		return pLenght >= MIN_LENGHT && pLenght <= MAX_LENGHT;
	}
	
	public static boolean validatePosition(int pPosition)
	{
		return pPosition >= 0 && pPosition <= lenght;
	}

	@Override
	public String toString()
	{
		return "The field size is: " + lenght;
	}
}
