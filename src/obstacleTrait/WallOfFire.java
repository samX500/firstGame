package obstacleTrait;

import character.Characters;
import character.StatusEnum;
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
		currentCharacter.setStatus(StatusEnum.BURNED);
		
	}

}
