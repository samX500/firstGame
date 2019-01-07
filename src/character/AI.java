package character;

import java.util.ArrayList;

import application.Application;
import environement.Obstacle;

public class AI
{
	private static ArrayList<Integer> obstaclePosition = new ArrayList<>();
	public static int goal;

	public static void aiTurn(Characters currentCharacter, Characters otherCharacter)
	{
		aiMoveToOpponent(currentCharacter, otherCharacter);
	}

	public static boolean aiStandOn(int goal)
	{
		boolean standOn = true;
		for (int i = 0; i < Application.obstacle.size(); i++)
		{
			if (Application.obstacle.get(i).standOn(goal))
			{
				standOn = false;
			}
		}
		return standOn;
	}

	public static boolean aiPassTrough(Characters currentCharacter, int goal)
	{
		boolean passTrough = true;
		for (int j = 0; j < Application.obstacle.size(); j++)
		{
			if (Application.obstacle.get(j).passTrough(currentCharacter, goal) > 0)
			{
				passTrough = false;
			}
		}
		return passTrough;
	}

	public static void aiMoveToOpponent(Characters currentCharacter, Characters otherCharacter)
	{
		

		if (currentCharacter.getPosition() > otherCharacter.getPosition())
		{
			if ((otherCharacter.getPosition() + currentCharacter.getRange()) >= (currentCharacter.getPosition()
					- currentCharacter.getSpeed()))
			{
				goal = otherCharacter.getPosition() + currentCharacter.getRange();
			} else
			{
				goal = currentCharacter.getPosition() - currentCharacter.getSpeed();
			}

			if (aiStandOn(goal) && (currentCharacter.getPosition() - currentCharacter.getSpeed()) == goal)
			{
				currentCharacter.jump(0);
			} else if (aiPassTrough(currentCharacter, goal))
			{
				currentCharacter.moveForward(currentCharacter.getPosition() - goal - 1);
			} else if (aiStandOn(currentCharacter.getPosition() - 1))
			{
				int mouvement = 0;
				boolean exitLoop = false;
				while ((aiStandOn(currentCharacter.getPosition() - mouvement - 1)) && !exitLoop)
				{
					if (mouvement >= currentCharacter.getSpeed())
					{
						exitLoop = true;
					} else
					{
						mouvement++;
					}
				}
				currentCharacter.moveForward(mouvement);
			} else if (currentCharacter.validateMouvement(currentCharacter.getPosition() - currentCharacter.getSpeed()))
			{
				currentCharacter.jump(0);
			} else
			{
				currentCharacter.moveForward(currentCharacter.getPosition() - goal - 1);
			}

		} else
		{
			//TODO debug the backward thing
			if ((otherCharacter.getPosition() - currentCharacter.getRange()) <= (currentCharacter.getPosition()
					+ currentCharacter.getSpeed()))
			{
				goal = otherCharacter.getPosition() - currentCharacter.getRange();
			} else
			{
				goal = currentCharacter.getPosition() + currentCharacter.getSpeed();
			}

			if (aiStandOn(goal) && (currentCharacter.getPosition() + currentCharacter.getSpeed()) == goal)
			{
				currentCharacter.jump(1);
			} else if (aiPassTrough(currentCharacter, goal))
			{
				currentCharacter.moveBackward(goal - currentCharacter.getPosition() - 1);
			} else if (aiStandOn(currentCharacter.getPosition() + 1))
			{
				int mouvement = 0;
				boolean exitLoop = false;
				while ((aiStandOn(currentCharacter.getPosition() + mouvement + 1)) && !exitLoop)
				{
					if (mouvement >= currentCharacter.getSpeed())
					{
						exitLoop = true;
					} else
					{
						mouvement++;
					}
				}
				currentCharacter.moveBackward(mouvement);
			} else if (currentCharacter.validateMouvement(currentCharacter.getPosition() + currentCharacter.getSpeed()))
			{
				currentCharacter.jump(1);
			} else
			{
				currentCharacter.moveForward(goal - currentCharacter.getPosition() - 1);
			}

		}
	}
}
