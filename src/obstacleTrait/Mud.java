package obstacleTrait;

import character.Characters;
import character.Status;
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
		currentCharacter.setStatus(Status.SLOWED);
		
	}

}
