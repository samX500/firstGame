package character;

public class AI
{
	public static int aiTurn(Characters currentCharacter, Characters otherCharacter)
	{
		// TODO better AI
		int move;
		if (currentCharacter.attackHit(otherCharacter.getPosition()))
		{
			move = 3;
		} else if (otherCharacter.attackHit(currentCharacter.getPosition())
				&& !otherCharacter.attackHit(currentCharacter.getPosition() - currentCharacter.getSpeed()))
		{
			move = 2;

		}

		else if (!currentCharacter.attackHit(otherCharacter.getPosition())
				&& !otherCharacter.attackHit(currentCharacter.getPosition() + currentCharacter.getSpeed()))
		{
			move = 1;
		} else if (otherCharacter.attackHit(currentCharacter.getPosition())
				&& !otherCharacter.attackHit(currentCharacter.getPosition() + currentCharacter.getSpeed()))
		{
			move = 1;
		} else if (otherCharacter.attackHit(currentCharacter.getPosition())
				&& otherCharacter.attackHit(currentCharacter.getPosition() + currentCharacter.getSpeed()))
		{
			move = 1;
		} else
		{
			move = 3;
		}

		return move;
	}
}
