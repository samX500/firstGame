package obstacleTrait;

import character.Characters;
import character.StatusEnum;
import environement.Obstacle;

public class Mud extends Obstacle
{
	private static final String SPRITE = "~ ";

	public Mud()
	{
		super(SPRITE);
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.setStatus(StatusEnum.SLOWED);

	}

}
