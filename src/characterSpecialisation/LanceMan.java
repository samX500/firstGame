package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class LanceMan extends Characters
{

	public LanceMan(boolean pFacing, int pPosition)
	{
		super("Lanceman", 5, 2, 25, 5, 10, 10, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			setRange(15);
			attack(otherCharacter);
			setRange(5);
			setStatus(Status.STUNNED);
		}
	}
}
