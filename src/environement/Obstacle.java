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

	public void obstHit(Characters currentCharacter, int lastPosition)
	{
		//TODO what if current position = lastPosition
		if(passTrough(currentCharacter, lastPosition))
		{
			obstEffect(currentCharacter);
		}
		else if(standOn(currentCharacter.getPosition()))
		{
			obstEffect(currentCharacter);
		}
	}
	
	public boolean passTrough(Characters currentCharacter, int lastPosition)
	{
		boolean passedTrough = false;
		if (currentCharacter.getFacing())
		{
			for (int i = 0; i < lenght; i++)
			{
				if (lastPosition > currentCharacter.getPosition()
						&& (currentCharacter.getPosition() < allPosition[i] && lastPosition > allPosition[i]))
				{
					passedTrough = true;
				} else if ((lastPosition < allPosition[i] && currentCharacter.getPosition() > allPosition[i]))
				{
					passedTrough = true;
				}
			}
		} else
		{
			for (int i = 0; i < lenght; i++)
			{
				if (lastPosition < currentCharacter.getPosition()
						&& (currentCharacter.getPosition() > allPosition[i] && lastPosition < allPosition[i]))
				{
					passedTrough = true;
				} else if ((lastPosition > allPosition[i] && currentCharacter.getPosition() < allPosition[i]))
				{
					passedTrough = true;
				}
			}
		}
		return passedTrough;
	}

	public boolean standOn(int pPosition)
	{
		boolean passedTrough = false;
		for (int i = 0; i < lenght; i++)
		{
			if (pPosition == allPosition[i])
			{
				passedTrough = true;
			}
		}
		return passedTrough;
	}

	public abstract void obstEffect(Characters currentCharacter);

	@Override
	public String toString()
	{
		return sprite;
	}
}
