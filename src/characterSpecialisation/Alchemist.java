package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class Alchemist extends Characters
{
	private static final String NAME = "Alchemist";
	private static final int RANGE = 8;
	private static final int SPEED = 6;
	private static final double MAX_HEALTH = 30;
	private static final double HEALING = 15;
	private static final double ARMOR = 5;
	private static final double DAMAGE = 5;

	public Alchemist(boolean pFacing, int pPosition)
	{
		super(NAME, RANGE, SPEED, MAX_HEALTH, HEALING, ARMOR, DAMAGE, pFacing, pPosition);
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
