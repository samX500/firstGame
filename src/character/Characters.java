package character;

import environement.Field;
import environement.TurnManagement;

import java.util.ArrayList;

import application.Application;

public abstract class Characters
{
	private static final int MIN_RANGE = 0;
	private static final int MAX_RANGE = 50;
	private static final int MIN_SPEED = 0;
	private static final int MAX_SPEED = 50;
	private static final double MIN_HEALTH = 0;
	private static final double MAX_HEALTH = 200;
	private static final double MIN_HEALING = 0;
	private static final double MAX_HEALING = 100;
	private static final double MIN_ARMOR = 0;
	private static final double MAX_ARMOR = 100;
	private static final double MIN_DAMAGE = 0;
	private static final double MAX_DAMAGE = 200;

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
	private ArrayList<StatusEnum> status = new ArrayList<>();
	protected static ArrayList<Status> statusObject = new ArrayList<>();

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

			setStatusObject();
			setStatus(StatusEnum.NO_STATUS);
			setHealth(pMaxHealth);
			setPosition(pPosition);
			
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

	protected static boolean validateArmor(double pArmor)
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

	public static boolean validateDamage(double pDamage)
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

	public boolean getStatus(StatusEnum pStatus)
	{
		return status.contains(pStatus);
	}

	public void setStatus(StatusEnum pStatus)
	{
		if (getStatus(pStatus))
		{
			status.remove(pStatus);
			status.add(pStatus);
		} else
		{
			status.add(pStatus);
		}
		if (!noStatus())
		{
			removeStatus(StatusEnum.NO_STATUS);
		}
		statusObject.get(findObject(pStatus)).statusStart(facingRight);
	}

	public void removeStatus(StatusEnum pStatus)
	{
		status.remove(pStatus);
		if (noStatus())
		{
			setStatus(StatusEnum.NO_STATUS);
		}

	}

	public boolean noStatus()
	{
		boolean noStatus = true;
		for (int i = 1; i < StatusEnum.values().length; i++)
		{
			if (status.contains(StatusEnum.values()[i]))
			{
				noStatus = false;
			}
		}
		return noStatus;
	}
	
	public static ArrayList<Status> getStatusObject()
	{
		return statusObject;
	}
	public void setStatusObject()
	{
		for(int i = 0; i < StatusEnum.values().length; i++)
		{
			statusObject.add(new Status(StatusEnum.values()[i]));
		}
	}
	
	public static int findObject(StatusEnum pStatus)
	{
		int object;
		switch (pStatus)
		{
		case NO_STATUS:
			object = 0;
			break;
		case BLOCKING:
			object = 1;
			break;
		case STUNNED:
			object = 2;
			break;
		case SLOWED:
			object = 3;
			break;
		case POISONED:
			object = 4;
			break;
		case BURNED:
			object = 5;
			break;
			default:
			object = 0;
			break;
		}
		return object;
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

	//Cannot receive negative value
	public double armorDamage(double pDamage)
	{
		double reducedHit = Math.abs(pDamage / 2);
		if (validateArmor(armor - reducedHit))
		{
			armor -= reducedHit;
			reducedHit = 0;
		} else
		{
			reducedHit -= armor;
			setArmor(0);

		}
		return reducedHit * 2;
	}

	//Cannot receive negative value
	public void takeDamage(double pDamage)
	{
		double remainingDamage = Math.abs(pDamage);
		if (armor != 0)
		{
			remainingDamage = armorDamage(pDamage);
		}
		changeHealth(-remainingDamage);
	}

	public void moveForward(int pMouvement)
	{
		int direction = direction(facingRight);
		pMouvement = slowed(pMouvement);

		if (validateMouvement(pMouvement) && Field.validatePosition(position + pMouvement * direction))
		{
			Application.passTroughObst(this, (position + pMouvement * direction));
			setPosition(position + (pMouvement * direction));
		}

	}

	public void moveBackward(int pMouvement)
	{
		int direction = direction(facingRight);

		pMouvement = slowed(pMouvement);

		if (validateMouvement(pMouvement) && Field.validatePosition(position - (pMouvement * direction)))
		{
			Application.passTroughObst(this, (position - pMouvement * direction));
			setPosition(position - (pMouvement * direction));
		}

	}
	
	public void jump(int pJumpSide)
	{
		int jumpSide = pJumpSide == 0 ? 1 : -1;
		int direction = direction(facingRight);
		if (Field.validatePosition(position + (speed * direction*jumpSide)))
		{
			setPosition(position + (speed * direction * jumpSide));
		}
	}

	public static int direction(boolean pFacing)
	{
		return pFacing ? 1 : -1;
	}

	public boolean validateMouvement(int pMouvement)
	{
		return pMouvement >= 0 && pMouvement <= speed;
	}

	public void attack(Characters otherCharacter)
	{
		if (otherCharacter.getStatus(StatusEnum.BLOCKING))
		{
			setStatus(StatusEnum.STUNNED);
			otherCharacter.removeStatus(StatusEnum.STUNNED);
			otherCharacter.removeStatus(StatusEnum.BLOCKING);
		} else if (attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.takeDamage(damage);
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
		if (TurnManagement.canHeal(facingRight))
		{
			changeHealth(healing);
			TurnManagement.setLastHeal(facingRight);
		}

	}

	public void block()
	{
		if (TurnManagement.canBlock(facingRight))
		{
			setStatus(StatusEnum.BLOCKING);
			setStatus(StatusEnum.STUNNED);
			TurnManagement.setLastBlock(facingRight);
		}
	}

	public void grab(Characters otherCharacter)
	{
		if (position == otherCharacter.getPosition())
		{
			otherCharacter.setStatus(StatusEnum.STUNNED);
			if (otherCharacter.getStatus(StatusEnum.BLOCKING))
			{
				otherCharacter.removeStatus(StatusEnum.BLOCKING);
			}
		}
	}

	public abstract void specialAttack(Characters otherCharacter);

	public int slowed(int pMouvement)
	{
		if (this.getStatus(StatusEnum.SLOWED))
		{
			pMouvement /= 2;
		}
		return pMouvement == 0 ? 1 : pMouvement;
	}

	public int getDistance(Characters otherCharacter)
	{
		return Math.abs(position - otherCharacter.getPosition());
	}

	public boolean die()
	{
		return health <= 0;
	}

	public String statusToString()
	{
		String currentStatus = "";
		for (int i = 0; i < StatusEnum.values().length; i++)
		{
			if (getStatus(StatusEnum.values()[i]))
			{
				currentStatus += StatusEnum.values()[i].toString() + ", ";
			}
		}
		return currentStatus;
	}

	public String toString()
	{
		return name + ": [Speed: " + speed + "] [Range: " + range + "] [Maximum Health: " + maxHealth + "] [Healing: "
				+ healing + "] [Armor: " + armor + "] [Damage: " + damage + "]";
	}
}
