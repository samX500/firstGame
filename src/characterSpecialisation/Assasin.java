package characterSpecialisation;

import character.Characters;
import character.Status;

public class Assasin extends Characters
{
	public Assasin(boolean pFacing, int pPosition)
	{
		super("Assasin", 1, 8, 20, 0, 0, 0, pFacing, pPosition);
	}
	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if(otherCharacter.getStatus(Status.STUNNED)&&attackHit(otherCharacter.getPosition()))
		{
			otherCharacter.changeHealth(-otherCharacter.getMaxHealth());
		}
		
	}
	
}
