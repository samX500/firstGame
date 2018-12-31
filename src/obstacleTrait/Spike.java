package obstacleTrait;

import character.Characters;
import environement.Obstacle;

public class Spike extends Obstacle
{

	public static final double SPIKE_DAMAGE = 3;
	
	public Spike()
	{
		super("* ");
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.takeDamage(SPIKE_DAMAGE);
		
	}

}
