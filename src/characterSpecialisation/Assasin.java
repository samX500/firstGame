package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class Assasin extends Characters
{
	public Assasin(boolean pFacing, int pPosition)
	{
		super("Assasin", 1, 8, 20, 0, 0, 0, pFacing, pPosition);
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
