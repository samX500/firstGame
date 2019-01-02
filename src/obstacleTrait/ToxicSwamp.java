package obstacleTrait;

import character.Characters;
import character.StatusEnum;
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
		currentCharacter.setStatus(StatusEnum.POISONED);
		
	}

}
