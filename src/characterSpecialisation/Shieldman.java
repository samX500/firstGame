package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class Shieldman extends Characters
{

	public Shieldman(boolean pFacing, int pPosition)
	{
		super("Shieldman", 3, 1, 30, 15, 30, 10, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			if (otherCharacter.getStatus(Status.BLOCKING))
			{
				setDamage(getDamage()*2);
				otherCharacter.removeStatus(Status.BLOCKING);
				otherCharacter.removeStatus(Status.STUNNED);
			}
			attack(otherCharacter);
			setDamage(getDamage()/2);
		}
	}
}
