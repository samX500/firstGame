package characterSpecialisation;

import character.Characters;
import character.StatusEnum;
import environement.TurnManagement;

public class Archer extends Characters
{
	private static final String NAME = "Archer";
	private static final int RANGE = 15;
	private static final int SPEED = 4;
	private static final double MAX_HEALTH = 30;
	private static final double HEALING = 5;
	private static final double ARMOR = 5;
	private static final double DAMAGE = 10;

	public Archer(boolean pFacing, int pPosition)
	{
		super(NAME, RANGE, SPEED, MAX_HEALTH, HEALING, ARMOR, DAMAGE, pFacing, pPosition);
	}

	@Override
	public void attack(Characters otherCharacter)
	{
		if (getDistance(otherCharacter) > 3)
		{
			doAttack(otherCharacter);
		} else
		{
			setDamage(getDamage() * 0.3);
			doAttack(otherCharacter);
			setDamage(getDamage() / 0.3);
		}
	}

	public void doAttack(Characters otherCharacter)
	{
		if (otherCharacter.getStatus(StatusEnum.BLOCKING))
		{
			setStatus(StatusEnum.STUNNED);
			otherCharacter.removeStatus(StatusEnum.STUNNED);
			otherCharacter.removeStatus(StatusEnum.BLOCKING);
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
				int knocback = (getPosition() < otherCharacter.getPosition()) ? (otherCharacter.getPosition() + 5)
						: (otherCharacter.getPosition() - 5);
				attack(otherCharacter);
				otherCharacter.setPosition(knocback);
			}
		}
	}

}
