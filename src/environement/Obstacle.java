package environement;

import character.Characters;

public abstract class Obstacle
{
	private static final int MAX_LENGHT = 3;
	private static final int MIN_LENGHT = 1;
	private static final int MAX_POSITION = Field.getLenght() - 5;
	private static final int MIN_POSITION = 5;

	private int position;
	private int lenght;
	private String sprite;

	private int[] allPosition;

	public Obstacle(String pSprite)
	{
		setPosition();
		setLenght();
		setAllPosition();
		setSprite(pSprite);
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition()
	{
		position = (int) (Math.random() * (MAX_POSITION - MIN_POSITION + 1) + MIN_POSITION);
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght()
	{
		lenght = (int) (Math.random() * (MAX_LENGHT - MIN_LENGHT + 1) + MIN_LENGHT);
	}

	public int[] getAllPosition()
	{
		return allPosition;
	}

	public void setAllPosition()
	{
		allPosition = new int[lenght];
		for (int i = 0; i < lenght; i++)
		{
			allPosition[i] = position + i;
		}
	}

	public String getSprite()
	{
		return sprite;
	}

	public void setSprite(String pSprite)
	{
		sprite = pSprite;
	}

	public int getDistance(Obstacle otherObstacle)
	{
		return Math.abs(position - otherObstacle.getPosition());
	}

	public void passTrough(Characters currentCharacter, int lastPosition)
	{
		if (currentCharacter.getFacing())
		{
			for (int i = 0; i < lenght; i++)
			{
				if (currentCharacter.getPosition() < allPosition[i] && lastPosition > allPosition[i])
				{
					obstEffect(currentCharacter);
				}
			}
		}
		else
		{
			for (int i = 0; i < lenght; i++)
			{
				if (currentCharacter.getPosition() > allPosition[i] && lastPosition < allPosition[i])
				{
					obstEffect(currentCharacter);
				}
			}
		}
	}

	public void standOn(Characters currentCharacter)
	{
		for (int i = 0; i < lenght; i++)
		{
			if (currentCharacter.getPosition() == allPosition[i])
			{
				obstEffect(currentCharacter);
			}
		}
	}

	public abstract void obstEffect(Characters currentCharacter);

	@Override
	public String toString()
	{
		// TODO do a better toString
		return sprite;
	}
}
