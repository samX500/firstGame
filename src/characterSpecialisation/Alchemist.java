package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class Alchemist extends Characters
{
	public Alchemist(boolean pFacing, int pPosition)
	{
		super("Alchemist", 5, 3, 30, 15, 5, 5, pFacing, pPosition);
	}

	@Override
	public void attack(Characters otherCharacter)
	{
		if (attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.takeDamage(getDamage());
			otherCharacter.setStatus(Status.POISONED);
		}
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			if (attackHit(otherCharacter.getPosition()))
			{
				otherCharacter.takeDamage(getDamage());
				otherCharacter.setStatus(Status.SLOWED);
				otherCharacter.setStatus(Status.BURNED);
				otherCharacter.setStatus(Status.POISONED);
			}
		}
	}

}
