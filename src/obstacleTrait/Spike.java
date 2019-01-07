package obstacleTrait;

import character.Characters;
import environement.Obstacle;

public class Spike extends Obstacle
{

	private static final String SPRITE = "* ";
	public static final double SPIKE_DAMAGE = 3;
	
	public Spike()
	{
		super(SPRITE);
	}

	@Override
	public void obstEffect(Characters currentCharacter)
	{
		currentCharacter.takeDamage(SPIKE_DAMAGE);
		
	}

}
