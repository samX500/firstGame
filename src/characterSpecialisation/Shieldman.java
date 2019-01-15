package characterSpecialisation;

import character.Characters;
import character.StatusEnum;
import environement.TurnManagement;

public class Shieldman extends Characters
{
	private static final String NAME = "Shieldman";
	private static final int RANGE = 4;
	private static final int SPEED = 4;
	private static final double MAX_HEALTH = 30;
	private static final double HEALING = 5;
	private static final double ARMOR = 30;
	private static final double DAMAGE = 15;

	public Shieldman(boolean pFacing, int pPosition)
	{
		super(NAME, RANGE, SPEED, MAX_HEALTH, HEALING, ARMOR, DAMAGE, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			if (otherCharacter.getStatus(StatusEnum.BLOCKING))
			{
				setDamage(getDamage() * 2);
				otherCharacter.removeStatus(StatusEnum.BLOCKING);
				otherCharacter.removeStatus(StatusEnum.STUNNED);
				attack(otherCharacter);
				setDamage(getDamage() / 2);
			} else
			{
				attack(otherCharacter);
			}

		}
	}
}
