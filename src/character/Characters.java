package character;

import environement.Field;

public class Characters
{
	private static final String DEFAULT_NAME = "Farmer";
	private static final int DEFAULT_RANGE = 2;
	private static final int DEFAULT_SPEED = 2;
	private static final int DEFAULT_POSITION = 0;
	private static final double DEFAULT_HEALTH = 20;
	private static final double DEFAULT_DAMAGE = 5;
	private static final boolean DEFAULT_FACING = true;

	private static final int MIN_RANGE = 1;
	private static final int MAX_RANGE = 50;
	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 50;
	private static final double MIN_HEALTH = 1;
	private static final double MAX_HEALTH = 200;
	private static final double MIN_DAMAGE = 1;
	private static final double MAX_DAMAGE = 200;

	private String name;
	private int range;
	private int speed;
	private int position;
	private double health;
	private double damage;
	private boolean facingRight;

	public Characters()
	{
		this(DEFAULT_NAME, DEFAULT_RANGE, DEFAULT_SPEED, DEFAULT_POSITION, DEFAULT_HEALTH, DEFAULT_DAMAGE,
				DEFAULT_FACING);
	}

	public Characters(String pName, int pRange, int pSpeed, int pPosition, double pHealth, double pDamage,
			boolean pFacing)
	{
		if (validateDamage(pDamage) && validateHealth(pHealth) && validateName(pName)
				&& Field.validatePosition(pPosition) && validateRange(pRange) && validateSpeed(pSpeed))
		{
			setDamage(pDamage);
			setHealth(pHealth);
			setName(pName);
			setPosition(pPosition);
			setRange(pRange);
			setSpeed(pSpeed);
			setFacing(pFacing);
		} else
		{
			setDamage(DEFAULT_DAMAGE);
			setHealth(DEFAULT_HEALTH);
			setName(DEFAULT_NAME);
			setPosition(DEFAULT_POSITION);
			setRange(DEFAULT_RANGE);
			setSpeed(DEFAULT_SPEED);
			setFacing(DEFAULT_FACING);
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
		return pRange >= MIN_RANGE && pRange <= MAX_RANGE;
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
		return pSpeed >= MIN_SPEED && pSpeed <= MAX_SPEED;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int pPosition)
	{
		if (Field.validatePosition(pPosition))
		{
			position = pPosition;
		}
	}

//	public boolean validatePosition(int pPosition)
//	{
//		return pPosition > 0 && pPosition < Field.MAX_LENGHT;
//	}

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

	// TODO this might not be proper
	// This is a setHealth the is only use if you take damage so health can go under
	// 0
	public void takeDamage(double pHealth)
	{
		health = pHealth;
	}

	private boolean validateHealth(double pHealth)
	{
		// TODO check this out
		return pHealth >= MIN_HEALTH && pHealth <= MAX_HEALTH;
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
		return pDamage >= MIN_DAMAGE && pDamage <= MAX_DAMAGE;
	}

	public boolean getFacing()
	{
		return facingRight;
	}

	public void setFacing(boolean pFacing)
	{
		facingRight = pFacing;
	}

	public void moveForward(boolean pFacing)
	{
		int face = -1;
		if (pFacing)
		{
			face = 1;
		}

		if (Field.validatePosition(position + speed * face))
		{
			setPosition(position + (speed * face));
		}
	}

	public void moveBackward(boolean pFacing)
	{
		int direction = 1;
		if (pFacing)
		{
			direction = -1;
		}

		if (Field.validatePosition(position + (speed * direction)))
		{
			setPosition(position + (speed * direction));
		}
	}

	public void attack(Characters currentCharacter)
	{

		if (attackHit(currentCharacter))
		{
			currentCharacter.takeDamage(currentCharacter.getHealth() - damage);
		}
	}

	public boolean attackHit(Characters currentCharacter)
	{
		// TODO I might want to make a method to evaluate if characters are facing each
		// other
		boolean isHit = false;
//		int direction = -1;
//		if(facingRight)
//		{
//			direction = 1;
//		}
//	
//		if (position > currentCharacter.getPosition() && facingRight)
//		{
//			direction = -1;
//		} else if (position < currentCharacter.getPosition() && !facingRight)
//		{
//			direction = 1;
//		}
//		if (position == currentCharacter.getPosition())
//		{
//			isHit = true;
//		}

		//TODO on second thougth I don't think this works
		if (position + range >= currentCharacter.getPosition() || position - range <= currentCharacter.getPosition())
		{
			isHit = true;
		}

		return isHit;
	}

	public int getDistance(Characters currentCharacter)
	{
		int distance = position - currentCharacter.getPosition();
		if (distance < 0)
		{
			distance *= -1;
		}
		return distance;
	}
	public boolean die()
	{
		return health <= 0;
	}

	public String toString()
	{
		// TODO will have to review this later
		return name + ": [Speed: " + speed + "] [Range: " + range + "] [Health: " + health + "] [Damage: " + damage
				+ "]";
	}
}
