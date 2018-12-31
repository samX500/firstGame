package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class Archer extends Characters
{

	public Archer(boolean pFacing, int pPositon)
	{
		super("Archer", 10, 1, 25, 5, 5, 10, pFacing, pPositon);
	}

	@Override
	public void attack(Characters otherCharacter)
	{
		if (getDistance(otherCharacter) > 3)
		{
			doAttack(otherCharacter);
		} else
		{
			setDamage(getDamage()*0.3);
			doAttack(otherCharacter);
			setDamage(getDamage()/0.3);
		}
	}

	public void doAttack(Characters otherCharacter)
	{
		if (otherCharacter.getStatus(Status.BLOCKING))
		{
			setStatus(Status.STUNNED);
			otherCharacter.removeStatus(Status.STUNNED);
			otherCharacter.removeStatus(Status.BLOCKING);
		} else if (attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.takeDamage(getDamage());
		}
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			if (getDistance(otherCharacter) <= 3)
			{
				int knocback = (getPosition() < otherCharacter.getPosition())
						? (otherCharacter.getPosition() + 5)
						: (otherCharacter.getPosition() - 5);
				attack(otherCharacter);
				otherCharacter.setPosition(knocback);
			}
		}
	}

}
