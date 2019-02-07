package characterSpecialisation;

import character.Characters;
import character.StatusEnum;
import environement.TurnManagement;

public class Assasin extends Characters
{private static final String NAME = "Assasin";
private static final int RANGE = 3;
private static final int SPEED = 15;
private static final double MAX_HEALTH = 30;
private static final double HEALING = 0;
private static final double ARMOR = 0;
private static final double DAMAGE = 0;
	public Assasin(boolean pFacing, int pPosition)
	{
		super(NAME, RANGE,SPEED, MAX_HEALTH, HEALING,ARMOR, DAMAGE, pFacing, pPosition);
	}

	@Override
	public void attack(Characters otherCharacter)
	{
		if (attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.setStatus(StatusEnum.STUNNED);
		}
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{

		if (TurnManagement.canSpecial(getFacing()))
		{
			TurnManagement.setLastSpecial(getFacing());
			if (otherCharacter.getStatus(StatusEnum.STUNNED) && attackHit(otherCharacter.getPosition()))
			{
				otherCharacter.changeHealth(-otherCharacter.getMaxHealth());
			}
		}

	}

}
