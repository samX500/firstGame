package characterSpecialisation;

import character.Characters;
import environement.TurnManagement;

public class Swordsman extends Characters
{private static final String NAME = "Swordsman";
private static final int RANGE = 5;
private static final int SPEED = 8;
private static final double MAX_HEALTH = 30;
private static final double HEALING = 5;
private static final double ARMOR = 10;
private static final double DAMAGE = 10;
	public Swordsman(boolean pFacing, int pPosition)
	{

		super(NAME, RANGE,SPEED, MAX_HEALTH, HEALING,ARMOR, DAMAGE, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canSpecial(getFacing()))
		{
			TurnManagement.setLastSpecial(getFacing());
			if(otherCharacter.getPosition() > getPosition())
			{
				moveForward(getSpeed());
				setDamage(getDamage()*2);
				attack(otherCharacter);
				setDamage(getDamage()/2);
				
			}
			else
			{
				moveBackward(getSpeed());
				setDamage(getDamage()*2);
				attack(otherCharacter);
				setDamage(getDamage()/2);
			}
		
		}
	}

}
