package characterSpecialisation;

import character.Characters;
import character.Status;
import environement.TurnManagement;

public class Swordsman extends Characters
{
	public Swordsman(boolean pFacing, int pPosition)
	{

		super("Swordsman", 2, 4, 25, 5, 10, 10, pFacing, pPosition);
	}

	@Override
	public void specialAttack(Characters otherCharacter)
	{
		if (TurnManagement.canBeSpecialing(getFacing()))
		{
			if(otherCharacter.getPosition() > getPosition())
			{
				moveForward(getFacing(), getSpeed());
				setDamage(getDamage()*2);
				attack(otherCharacter);
				setDamage(getDamage()/2);
				
			}
			else
			{
				moveBackward(getFacing(), getSpeed());
				setDamage(getDamage()*2);
				attack(otherCharacter);
				setDamage(getDamage()/2);
			}
		
		}
	}

}
