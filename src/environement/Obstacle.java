package environement;

import character.Characters;

public abstract class Obstacle
{
	private static final int MAX_LENGHT = 3;
	private static final int MIN_LENGHT = 1;
	private static final int MAX_POSITION = Field.getLenght() - 7;
	private static final int MIN_POSITION = 5;

	private int position;
	private int lenght;
	private String sprite;

	private int[] allPosition;
	
	public Obstacle(String pSprite)
	{
		setLenght();
		setPosition();
		setSprite(pSprite);
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition()
	{
		position = (int) (Math.random() * (MAX_POSITION - MIN_POSITION + 1) + MIN_POSITION);
		setAllPosition();
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght()
	{
		lenght = (int) (Math.random() * (MAX_LENGHT - MIN_LENGHT + 1) + MIN_LENGHT);
	}

	public String getAllPosition()
	{
		String pAllPosition = "";
		for(int i = 0; i < lenght;i++)
		{
			pAllPosition += allPosition[i] + ", ";
		}
		return pAllPosition;
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

	public void passTroughHit(Characters currentCharacter, int lastPosition)
	{
		for(int i = 0; i < passTrough(currentCharacter, lastPosition);i++)
		{
			obstEffect(currentCharacter);
		}
		
	}
	public void standOnHit(Characters currentCharacter)
	{
		if(standOn(currentCharacter.getPosition()))
		{
			obstEffect(currentCharacter);
		}
	}
	
	public int passTrough(Characters currentCharacter, int lastPosition)
	{
		int passedTrough = 0;
		if (currentCharacter.getFacing())
		{
			for (int i = 0; i < lenght; i++)
			{
				if (lastPosition > currentCharacter.getPosition()
						&& (currentCharacter.getPosition() < allPosition[i] && lastPosition > allPosition[i]))
				{
					passedTrough++;
				} else if ((lastPosition < allPosition[i] && currentCharacter.getPosition() > allPosition[i]))
				{
					passedTrough++;
				}
			}
		} else
		{
			for (int i = 0; i < lenght; i++)
			{
				if (lastPosition < currentCharacter.getPosition()
						&& (currentCharacter.getPosition() > allPosition[i] && lastPosition < allPosition[i]))
				{
					passedTrough++;
				} else if ((lastPosition > allPosition[i] && currentCharacter.getPosition() < allPosition[i]))
				{
					passedTrough++;
				}
			}
		}
		return passedTrough;
	}

	public boolean standOn(int pPosition)
	{
		boolean standOn = false;
		for (int i = 0; i < lenght; i++)
		{
			if (pPosition == allPosition[i])
			{
				standOn = true;
			}
		}
		return standOn;
	}

	public abstract void obstEffect(Characters currentCharacter);

	@Override
	public String toString()
	{
		return sprite;
	}
}
