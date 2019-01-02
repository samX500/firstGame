package character;

import environement.TurnManagement;

public class Status
{
	private static final double BURN_DAMAGE = 3;
	private static final double POIS_DAMAGE = 3;

	private int turnGottenP1;
	private int turnGottenP2;
	private int duration;
	private StatusEnum type;

	public Status(StatusEnum pType)
	{
		setType(pType);
		setDuration(type);
	}

	public int getTurnGotten(boolean facing)
	{
		return facing ? turnGottenP1 : turnGottenP2;
	}

	public void setTurnGotten(boolean facing, int pTurnGotten)
	{
		// TODO this can probably be done better
		if (facing)
		{
			turnGottenP1 = pTurnGotten;
		} else
		{
			turnGottenP2 = pTurnGotten;
		}
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(StatusEnum pStatus)
	{

		switch (pStatus)
		{
		case NO_STATUS:
			duration = 0;
			break;
		case BLOCKING:
			duration = 3;
			break;
		case STUNNED:
			duration = 3;
			break;
		case SLOWED:
			duration = 3;
			break;
		case POISONED:
			duration = 5;
			break;
		case BURNED:
			duration = 5;
			break;
		}

	}

	public StatusEnum getType()
	{
		return type;
	}

	public void setType(StatusEnum pType)
	{
		type = pType;
	}

	public void statusStart(boolean facing)
	{
		if (facing)
		{
			turnGottenP1 = TurnManagement.getTurn();
		} else
		{
			turnGottenP2 = TurnManagement.getTurn();
		}
	}

	public boolean statusEnd(boolean facing)
	{
		int timer = facing ? turnGottenP1 : turnGottenP2;
		return TurnManagement.getTurn() - timer >= duration;
	}

	public void statusActivate(Characters currentCharacter)
	{
		switch (type)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			break;
		case STUNNED:
			break;
		case SLOWED:
			break;
		case POISONED:
			poisDamage(currentCharacter);
			break;
		case BURNED:
			burnDamage(currentCharacter);
			break;
		}
	}

	public static void poisDamage(Characters currentCharacter)
	{
		currentCharacter.changeHealth(-POIS_DAMAGE);
	}

	public static void burnDamage(Characters currentCharacter)
	{
		currentCharacter.changeHealth(-BURN_DAMAGE);
	}

}
