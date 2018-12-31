package obstacleTrait;

import character.Characters;
import character.Status;
import environement.Obstacle;

public class ToxicSwamp extends Obstacle
{

	public ToxicSwamp()
	{
		super("☣ ");
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.setStatus(Status.POISONED);
		
	}

}
