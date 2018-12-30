package character;

import environement.Field;
import environement.TurnManagement;

import java.util.ArrayList;

import application.Application;

public class Characters
{
	private static final String DEFAULT_NAME = "Farmer";
	private static final int DEFAULT_RANGE = 2;
	private static final int DEFAULT_SPEED = 2;
	private static final int DEFAULT_POSITION = 0;
	private static final double DEFAULT_MAX_HEALTH = 20;
	private static final int DEFAULT_HEALING = 5;
	private static final int DEFAULT_ARMOR = 5;
	private static final double DEFAULT_DAMAGE = 5;
	private static final boolean DEFAULT_FACING = true;

	private static final int MIN_RANGE = 1;
	private static final int MAX_RANGE = 50;
	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 50;
	private static final double MIN_HEALTH = 1;
	private static final double MAX_HEALTH = 200;
	private static final double MIN_HEALING = 0;
	private static final double MAX_HEALING = 100;
	private static final double MIN_ARMOR = 0;
	private static final double MAX_ARMOR = 100;
	private static final double MIN_DAMAGE = 1;
	private static final double MAX_DAMAGE = 200;
	
	private static final double BURN_DAMAGE = 3;
	private static final double POIS_DAMAGE = 3;

	private String name;
	private int range;
	private int speed;
	private double maxHealth;
	private double healing;
	private double armor;
	private double damage;
	private boolean facingRight;

	private int position;
	private double health;
	private ArrayList<Status> status = new ArrayList<>();

	public Characters()
	{
		this(DEFAULT_NAME, DEFAULT_RANGE, DEFAULT_SPEED, DEFAULT_MAX_HEALTH, DEFAULT_HEALING, DEFAULT_ARMOR,
				DEFAULT_DAMAGE, DEFAULT_FACING, DEFAULT_POSITION);
	}

	public Characters(String pName, int pRange, int pSpeed, double pMaxHealth, double pHealing, double pArmor,
			double pDamage, boolean pFacing, int pPosition)
	{

		if (validateDamage(pDamage) && validateMaxHealth(pMaxHealth) && validateName(pName)
				&& Field.validatePosition(pPosition) && validateRange(pRange) && validateSpeed(pSpeed)
				&& validateArmor(pArmor) && validateHealing(pHealing))
		{
			setName(pName);
			setRange(pRange);
			setSpeed(pSpeed);
			setMaxHealth(pMaxHealth);
			setHealing(pHealing);
			setArmor(pArmor);
			setDamage(pDamage);
			setFacing(pFacing);

			setStatus(Status.NO_STATUS);
			setHealth(pMaxHealth);
			setPosition(pPosition);

		} else
		{
			setName(DEFAULT_NAME);
			setRange(DEFAULT_RANGE);
			setSpeed(DEFAULT_SPEED);
			setMaxHealth(DEFAULT_MAX_HEALTH);
			setHealing(DEFAULT_HEALING);
			setArmor(DEFAULT_ARMOR);
			setDamage(DEFAULT_DAMAGE);
			setFacing(DEFAULT_FACING);

			setStatus(Status.NO_STATUS);
			setHealth(DEFAULT_MAX_HEALTH);
			setPosition(DEFAULT_POSITION);
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

	public double getMaxHealth()
	{
		return maxHealth;
	}

	public void setMaxHealth(double pMaxHealth)
	{
		if (validateMaxHealth(pMaxHealth))
		{
			maxHealth = pMaxHealth;
		}
	}

	private boolean validateMaxHealth(double pMaxHealth)
	{
		return pMaxHealth >= MIN_HEALTH && pMaxHealth <= MAX_HEALTH;
	}

	public double getHealing()
	{
		return healing;
	}

	public void setHealing(double pHealing)
	{
		if (validateHealing(pHealing))
		{
			healing = pHealing;
		}
	}

	private boolean validateHealing(double pHealing)
	{
		return pHealing >= MIN_HEALING && pHealing <= MAX_HEALING;
	}

	public double getArmor()
	{
		return armor;
	}

	public void setArmor(double pArmor)
	{
		if (validateArmor(pArmor))
		{
			armor = pArmor;
		}
	}

	private boolean validateArmor(double pArmor)
	{
		return pArmor >= MIN_ARMOR && pArmor <= MAX_ARMOR;
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

	public double getHealth()
	{
		return health;
	}

	public void setHealth(double pHealth)
	{
		if (validateMaxHealth(pHealth))
		{
			health = pHealth;
		}

	}

	public boolean getStatus(Status pStatus)
	{
		return status.contains(pStatus);
	}
	public void setStatus(Status pStatus)
	{
		//TODO I will have to test the shit out of this
		//TODO Status do nothing
		status.add(pStatus);
		if(!noStatus())
		{
			removeStatus(Status.NO_STATUS);
		}
		TurnManagement.statusStart(pStatus);
	}
	
	public void removeStatus(Status pStatus)
	{
		status.remove(pStatus);
		if(noStatus())
		{
			setStatus(Status.NO_STATUS);
		}
		
	}

	public boolean noStatus()
	{
		boolean noStatus = true;
		for(int i = 1; i < Status.values().length; i++)
		{
			if(status.contains(Status.values()[i]))
			{
				noStatus = false;
			}
		}
		return noStatus;
	}
	// Positive value = gain health
	// Negative value = loose health
	public void changeHealth(double pHealth)
	{
		if ((health + pHealth) > maxHealth)
		{
			setHealth(maxHealth);
		} else
		{
			health += pHealth;
		}

	}

	// TODO Cannot receive negative value
	// Can only remove armor
	public double armorDamage(double pDamage)
	{
		double reducedHit = pDamage / 2;
		if (validateArmor(armor - reducedHit))
		{
			armor -= reducedHit;
			reducedHit = 0;
		} else
		{
			setArmor(0);
			reducedHit -= armor;
		}
		return reducedHit * 2;
	}

	// TODO Cannot receive negative value
	// Only remove health/armor
	public void takeDamage(double pDamage)
	{
		double remainingDamage = pDamage;
		if (armor != 0)
		{
			remainingDamage = armorDamage(pDamage);
		}
		changeHealth(-remainingDamage);
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
		if(currentCharacter.getStatus(Status.BLOCKING))
		{
			setStatus(Status.STUNNED);
		}		
		else if (attackHit(currentCharacter.getPosition()))
		{
			currentCharacter.takeDamage(damage);
		}
	}

	public boolean attackHit(int pPosition)
	{
		boolean isHit = false;
		if ((position + range >= pPosition && position <= pPosition)
				|| (position - range <= pPosition && position >= pPosition))
		{
			isHit = true;
		}

		return isHit;
	}

	public void heals()
	{
		if (TurnManagement.canBeHealing(facingRight))
		{
			changeHealth(healing);
		}

	}

	public void block()
	{
		if (TurnManagement.canBeBlocking(facingRight))
		{
			//TODO this is so fucking sloppy
			setStatus(Status.BLOCKING);
		}
	}
	
	public void poisDamage()
	{
		changeHealth(-POIS_DAMAGE);
	}
	
	public void burnDamage()
	{
		changeHealth(-BURN_DAMAGE);
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

	public String statusToString()
	{
		String currentStatus = "";
		for(int i = 0; i < Status.values().length; i++)
		{
			if(getStatus(Status.values()[i]))
			{
				currentStatus += StatusInfo.statusString(Status.values()[i])+", ";
			}
		}
		return currentStatus;
	}
	public String toString()
	{
		// TODO will have to review this later
		return name + ": [Speed: " + speed + "] [Range: " + range + "] [Maximum Health: " + maxHealth + "] [Healing: "
				+ healing + "] [Armor: " + armor + "] [Damage: " + damage + "]";
	}
}
