package characterSpecialisation;

import character.Characters;
import character.Status;

public class Swordsman extends Characters
{
	public Swordsman(boolean pFacing, int pPosition)
	{
		
		super("Swordsman", 2, 4, 25, 5, 10, 10, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		attack(otherCharacter);
		if(attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.setStatus(Status.POISONED);
		}
		
	}
	
	
	
}
