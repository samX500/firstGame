package environement;

public class Field
{

	private static int DEFAULT_LENGHT = 25;

	public static final int MIN_LENGHT = 15;
	public static final int MAX_LENGHT = 100;

	private int lenght;

	public Field()
	{
		lenght = (int)(Math.random()*(MAX_LENGHT-MIN_LENGHT+1)+MIN_LENGHT);
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

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght(int pLenght)
	{
		if (validateLenght(pLenght))
		{
			lenght = pLenght;
		}
	}

	private boolean validateLenght(int pLenght)
	{
		return pLenght > MIN_LENGHT && pLenght < MAX_LENGHT;
	}
	
	public String toString()
	{
		//TODO might change this later
		return "The field size is: " + lenght;
	}
}
