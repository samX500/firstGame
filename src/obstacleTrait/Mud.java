package obstacleTrait;

import character.Characters;
import character.StatusEnum;
import environement.Obstacle;

public class Mud extends Obstacle
{

	public Mud()
	{
		super("~ ");
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.setStatus(StatusEnum.SLOWED);
		
	}

}
