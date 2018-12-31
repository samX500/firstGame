package characterSpecialisation;

import character.Characters;
import character.Status;

public class Shieldman extends Characters
{

	public  Shieldman(boolean pFacing, int pPosition)
	{
		super("Shieldman", 3, 1, 30, 15, 30, 10, pFacing, pPosition);
	}
	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if(otherCharacter.getStatus(Status.BLOCKING))
		{
			otherCharacter.removeStatus(Status.BLOCKING);
			otherCharacter.removeStatus(Status.STUNNED);
		}
		attack(otherCharacter);
	}
}
