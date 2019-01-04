package character;

import java.util.ArrayList;

import application.Application;
import environement.Obstacle;

public class AI
{
	private static ArrayList<Integer> obstaclePosition = new ArrayList<>();

	public static void aiTurn(Characters currentCharacter, Characters otherCharacter)
	{

	}

	public static void findObstacle()
	{
		for (int i = 0; i < Application.obstacle.size(); i++)
		{
			for (int j = 0; j < Application.obstacle.get(i).getLenght(); i++)
			{
				obstaclePosition.add(Application.obstacle.get(i).getAllPosition()[j]);
			}
		}
	}

	public static boolean aiStandOn(int goal)
	{
		boolean standOn = true;
		for (int j = 0; j < Application.obstacle.size(); j++)
		{
			if (Application.obstacle.get(j).standOn(goal))
			{
				standOn = false;
			}
		}
		return standOn;
	}

	public static boolean aiPassTrough(Characters currentCharacter, int goal)
	{
		boolean standOn = true;
		for (int j = 0; j < Application.obstacle.size(); j++)
		{
			if (Application.obstacle.get(j).passTrough(currentCharacter, goal))
			{
				standOn = false;
			}
		}
		return standOn;
	}

	public static void aiMoveToOpponent(Characters currentCharacter, Characters otherCharacter)
	{
		int goal;
		// TODO the loop fuck shit up

		if (currentCharacter.getPosition() > otherCharacter.getPosition())
		{
			goal = otherCharacter.getPosition() + currentCharacter.getRange();

			if (goal <= (currentCharacter.getPosition() - currentCharacter.getSpeed()))
			{

				if (aiStandOn(goal))
				{
					currentCharacter.jump(0);
				} else if (aiPassTrough(currentCharacter, goal))
				{
					currentCharacter.moveForward(currentCharacter.getPosition() - goal + 1);
				}

			}
		} else
		{

		}
	}
}
