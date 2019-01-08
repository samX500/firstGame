package character;

import java.util.ArrayList;

import application.Application;
import environement.Obstacle;
import environement.TurnManagement;

public class AI
{
	public static int goal;

	public static void aiTurn(Characters currentCharacter, Characters otherCharacter)
	{
		//TODO put a good order to this and add special attack condition
		if (currentCharacter.getDistance(otherCharacter) == 0)
		{
			currentCharacter.grab(otherCharacter);
		}
		if (otherCharacter.getStatus(StatusEnum.STUNNED) && !otherCharacter.getStatus(StatusEnum.BLOCKING)
				&& currentCharacter.attackHit(otherCharacter.getPosition()))
		{
			currentCharacter.attack(otherCharacter);
		}
			if (currentCharacter.attackHit(otherCharacter.getPosition())
					&& otherCharacter.getHealth() < currentCharacter.getDamage() && otherCharacter.getArmor() == 0)
			{
				currentCharacter.attack(otherCharacter);
			}
		if ((otherCharacter.getName().equals("Assasin") || otherCharacter.getName().equals("Shieldman"))
				&& otherCharacter.attackHit(currentCharacter.getPosition())
				&& canEscape(currentCharacter, otherCharacter))
		{
			escape(currentCharacter, otherCharacter);
		}
		if ((otherCharacter.getDamage() > currentCharacter.getHealth()
				&& otherCharacter.getDamage() > currentCharacter.getHealth() + currentCharacter.getHealing())
				&& (currentCharacter.getArmor() < otherCharacter.getDamage() / 2)
				&& TurnManagement.canHeal(currentCharacter.getFacing()))
		{
			currentCharacter.heals();
		}
		if (currentCharacter.attackHit(otherCharacter.getPosition()) && !otherCharacter.getStatus(StatusEnum.BLOCKING))
		{
			currentCharacter.attack(otherCharacter);
		}
		if (currentCharacter.getDistance(otherCharacter) > otherCharacter.getSpeed() * 2
				&& otherCharacter.attackHit(currentCharacter.getPosition())
				&& TurnManagement.canBlock(currentCharacter.getFacing()))
		{
			currentCharacter.block();
		} else
		{
			aiMoveToOpponent(currentCharacter, otherCharacter);
		}
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
				goal = aiStandOn(otherCharacter.getPosition() + currentCharacter.getRange())
						? otherCharacter.getPosition() + currentCharacter.getRange()
						: currentCharacter.getPosition() - currentCharacter.getSpeed();
				// goal = otherCharacter.getPosition() + currentCharacter.getRange();
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
				currentCharacter.moveBackward(goal - currentCharacter.getPosition() - 1);
			}

		}

	}

	public static void escape(Characters currentCharacter, Characters otherCharacter)
	{
		if (currentCharacter.getPosition() > otherCharacter.getPosition())
		{
			if (aiStandOn(currentCharacter.getPosition() + currentCharacter.getSpeed()))
			{
				currentCharacter.jump(1);
			} else
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
				if (currentCharacter.getPosition() + mouvement > otherCharacter.getPosition()
						+ otherCharacter.getRange())
				{
					currentCharacter.moveBackward(mouvement);
				}

			}
		} else
		{
			if (aiStandOn(currentCharacter.getPosition() - currentCharacter.getSpeed()))
			{
				currentCharacter.jump(0);
			} else
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
				if (currentCharacter.getPosition() - mouvement > otherCharacter.getPosition()
						- otherCharacter.getRange())
				{
					currentCharacter.moveForward(mouvement);
				}
			}
		}
	}

	public static boolean canEscape(Characters currentCharacter, Characters otherCharacter)
	{
		boolean canEscape;
		if (currentCharacter.getPosition() > otherCharacter.getPosition())
		{
			if (aiStandOn(currentCharacter.getPosition() + currentCharacter.getSpeed()))
			{
				canEscape = true;
			} else
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
				if (currentCharacter.getPosition() + mouvement > otherCharacter.getPosition()
						+ otherCharacter.getRange())
				{
					canEscape = true;
				} else
				{
					canEscape = false;
				}
			}
		} else
		{
			if (aiStandOn(currentCharacter.getPosition() - currentCharacter.getSpeed()))
			{
				canEscape = true;
			} else
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
				if (currentCharacter.getPosition() - mouvement > otherCharacter.getPosition()
						- otherCharacter.getRange())
				{
					canEscape = true;
				} else
				{
					canEscape = false;
				}
			}
		}

		return canEscape;
	}
}
