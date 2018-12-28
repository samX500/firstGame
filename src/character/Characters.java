package character;

import environement.Field;

public class Characters
{
	//TODO I don't know what I am doing
	private static final String DEFAULT_NAME = "Farmer";
	private static final int DEFAULT_RANGE = 2;
	private static final int DEFAULT_SPEED = 2;
	private static final int DEFAULT_POSITION = 0;
	private static final double DEFAULT_HEALTH = 20;
	private static final double DEFAULT_DAMAGE = 5;

	private static final int MIN_RANGE = 1;
	private static final int MAX_RANGE = 10;
	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 10;
	private static final int MIN_HEALTH = 1;
	private static final int MAX_HEALTH = 50;
	private static final int MIN_DAMAGE = 1;
	private static final int MAX_DAMAGE = 50;

	private String name;
	private int range;
	private int speed;
	private int position;
	private double health;
	private double damage;

	public Characters()
	{
		this(DEFAULT_NAME, DEFAULT_RANGE, DEFAULT_SPEED, DEFAULT_POSITION, DEFAULT_HEALTH, DEFAULT_DAMAGE);
	}

	public Characters(String pName, int pRange, int pSpeed, int pPosition, double pHealth, double pDamage)
	{
		if (validateDamage(pDamage) && validateHealth(pHealth) && validateName(pName) && validatePosition(pPosition)
				&& validateRange(pRange) && validateSpeed(pSpeed))
		{
			setDamage(pDamage);
			setHealth(pHealth);
			setName(pName);
			//TODO might change that
			setPosition(pPosition);
			setRange(pRange);
			setSpeed(pSpeed);
		} else
		{
			setDamage(DEFAULT_DAMAGE);
			setHealth(DEFAULT_HEALTH);
			setName(DEFAULT_NAME);
			setPosition(DEFAULT_POSITION);
			setRange(DEFAULT_RANGE);
			setSpeed(DEFAULT_SPEED);
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String pName)
	{
		if (validateName(pName))
		{
			name = pName;
		}
	}

	private boolean validateName(String pName)
	{
		return pName != null && !(pName.isEmpty());
	}

	public int getRange()
	{
		return range;
	}

	public void setRange(int pRange)
	{
		if (validateRange(pRange))
		{
			range = pRange;
		}
	}

	private boolean validateRange(int pRange)
	{
		return pRange > MIN_RANGE && pRange < MAX_RANGE;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int pSpeed)
	{
		if (validateSpeed(pSpeed))
		{
			speed = pSpeed;
		}
	}

	private boolean validateSpeed(int pSpeed)
	{
		return pSpeed > MIN_SPEED && pSpeed < MAX_SPEED;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int pPosition)
	{
		if (validatePosition(pPosition))
		{
			position = pPosition;
		}
	}

	private boolean validatePosition(int pPosition)
	{
		// TODO remove hardcoding once I've made the environment
		return pPosition > 0 && pPosition < 10;
	}

	public double getHealth()
	{
		return health;
	}

	public void setHealth(double pHealth)
	{
		if (validateHealth(pHealth))
		{
			health = pHealth;
		}
	}

	private boolean validateHealth(double pHealth)
	{
		return pHealth > MIN_HEALTH && pHealth < MAX_HEALTH;
	}

	public double getDamage()
	{
		return damage;
	}

	public void setDamage(double pDamage)
	{
		if (validateDamage(pDamage))
		{
			damage = pDamage;
		}
	}

	private boolean validateDamage(double pDamage)
	{
		return pDamage > MIN_DAMAGE && pDamage < MAX_DAMAGE;
	}
	
//	private int fieldLenght()
//	{
//		
//	}

	public String toString()
	{
		// TODO will have to review this later
		return name + speed + range + health + damage;
	}
}
