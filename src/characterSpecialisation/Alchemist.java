package characterSpecialisation;

import character.Characters;
import character.Status;

public class Alchemist extends Characters
{
	public Alchemist(boolean pFacing, int pPosition)
	{
		super("Alchemist", 5, 3, 30, 15, 5, 5, pFacing, pPosition);
	}
	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if(attackHit(otherCharacter.getPosition()))
		{
			setStatus(Status.SLOWED);
			setStatus(Status.BURNED);
			setStatus(Status.POISONED);
		}
		
	}

}
