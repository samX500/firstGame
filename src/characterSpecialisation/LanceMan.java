package characterSpecialisation;

import character.Characters;
import character.Status;

public class LanceMan extends Characters
{

	public LanceMan(boolean pFacing, int pPosition)
	{
		super("Lanceman", 5, 2, 25, 5, 10, 10, pFacing, pPosition);
	}
	
	@Override
	public void specialAttack(Characters otherCharacter)
	{
		setRange(30);
		attack(otherCharacter);
		setRange(5);
		setStatus(Status.STUNNED);
	}

}
