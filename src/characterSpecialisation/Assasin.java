package characterSpecialisation;

import character.Characters;
import character.Status;
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
			otherCharacter.setStatus(Status.STUNNED);
		}
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{

		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			if (otherCharacter.getStatus(Status.STUNNED) && attackHit(otherCharacter.getPosition()))
			{
				otherCharacter.changeHealth(-otherCharacter.getMaxHealth());
			}
		}

	}

}
