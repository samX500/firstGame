package obstacleTrait;

import character.Characters;
import character.StatusEnum;
import environement.Obstacle;

public class WallOfFire extends Obstacle

{

	private static final String SPRITE = "🔥 ";
	
	public WallOfFire()
	{
		
		super(SPRITE);
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.setStatus(StatusEnum.BURNED);
		
	}

}
