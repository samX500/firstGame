package characterSpecialisation;

import character.Characters;
import character.StatusEnum;
import environement.TurnManagement;

public class LanceMan extends Characters
{
	private static final String NAME = "Lanceman";
	private static final int RANGE = 8;
	private static final int SPEED = 4;
	private static final double MAX_HEALTH = 30;
	private static final double HEALING = 5;
	private static final double ARMOR = 5;
	private static final double DAMAGE = 5;
	public LanceMan(boolean pFacing, int pPosition)
	{
		super(NAME, RANGE,SPEED, MAX_HEALTH, HEALING,ARMOR, DAMAGE, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			setRange(20);
			attack(otherCharacter);
			setRange(5);
			setStatus(StatusEnum.STUNNED);
		}
	}
}
