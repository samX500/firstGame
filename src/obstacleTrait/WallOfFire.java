package obstacleTrait;

import character.Characters;
import character.Status;
import environement.Obstacle;

public class WallOfFire extends Obstacle
{

	public WallOfFire()
	{
		super("🔥 ");
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.setStatus(Status.BURNED);
		
	}

}
