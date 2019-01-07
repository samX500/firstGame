package obstacleTrait;

import character.Characters;
import character.StatusEnum;
import environement.Obstacle;

public class ToxicSwamp extends Obstacle
{
	private static final String SPRITE = "☣ ";
	
	public ToxicSwamp()
	{
		super(SPRITE);
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.setStatus(StatusEnum.POISONED);
		
	}

}
