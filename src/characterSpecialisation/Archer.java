package characterSpecialisation;

import character.Characters;
import character.Status;

public class Archer extends Characters
{

	public Archer(boolean pFacing, int pPositon)
	{
		super("Archer", 10, 1, 25, 5, 5, 10, pFacing, pPositon);
	}
	
	@Override
	public void specialAttack(Characters otherCharacter)
	{
		attack(otherCharacter);
		if(attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.setStatus(Status.BURNED);
		}
		
	}

}
