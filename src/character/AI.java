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
		// TODO put a good order to this and add special attack condition

		// If next attack kill, attack
		if (!otherCharacter.getStatus(StatusEnum.BLOCKING) && currentCharacter.attackHit(otherCharacter.getPosition())
				&& canKill(currentCharacter.getDamage(), otherCharacter.getArmor(), otherCharacter.getHealth()))
		{
			currentCharacter.attack(otherCharacter);
		}
		// if next attack kill && am Alch
		else if (currentCharacter.getName().equals("Alchemist")
				&& currentCharacter.attackHit(otherCharacter.getPosition())
				&& canKill(currentCharacter.getDamage(), otherCharacter.getArmor(), otherCharacter.getHealth()))
		{
			currentCharacter.attack(otherCharacter);
		}
		// if special kill && swordsman
		else if (!otherCharacter.getStatus(StatusEnum.BLOCKING) && currentCharacter.getName().equals("Swordsman")
				&& TurnManagement.canSpecial(currentCharacter.getFacing())
				&& currentCharacter.getDistance(otherCharacter) <= currentCharacter.getRange()
						+ currentCharacter.getSpeed()
				&& canKill(currentCharacter.getDamage() * 2, otherCharacter.getArmor(), otherCharacter.getHealth()))
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// if next attack kill && am Lancer
		else if (TurnManagement.canSpecial(currentCharacter.getFacing())
				&& !otherCharacter.getStatus(StatusEnum.BLOCKING)
				&& (currentCharacter.getDistance(otherCharacter) <= 20)
				&& canKill(currentCharacter.getDamage(), otherCharacter.getArmor(), otherCharacter.getHealth()))
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// If am assasin && enemie == stun, assasin.special
		else if (currentCharacter.getName().equals("Assasin") && otherCharacter.getStatus(StatusEnum.STUNNED)
				&& currentCharacter.attackHit(otherCharacter.getPosition()))
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// If enemie == stun, attack
		else if (otherCharacter.getStatus(StatusEnum.STUNNED) && !otherCharacter.getStatus(StatusEnum.BLOCKING)
				&& currentCharacter.attackHit(otherCharacter.getPosition()))
		{
			currentCharacter.attack(otherCharacter);
		}
		// TODO to debug, might want to put this after grab
		// If blocking go grab
		else if (AIGrab(currentCharacter, otherCharacter))
		{
			moveTo(currentCharacter, otherCharacter, otherCharacter.getPosition());
		}

		// Grab
		else if (currentCharacter.getDistance(otherCharacter) == 0)
		{
			currentCharacter.grab(otherCharacter);
		}

		// If you need to escape, escape
		else if ((otherCharacter.getName().equals("Assasin") || otherCharacter.getName().equals("Shieldman"))
				&& otherCharacter.attackHit(currentCharacter.getPosition())
				&& canEscape(currentCharacter, otherCharacter))
		{
			escape(currentCharacter, otherCharacter);
		}
		// swordsman special
		else if (!otherCharacter.getStatus(StatusEnum.BLOCKING) && currentCharacter.getName().equals("Swordsman")
				&& TurnManagement.canSpecial(currentCharacter.getFacing()) && currentCharacter
						.getDistance(otherCharacter) <= currentCharacter.getRange() + currentCharacter.getSpeed())
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// Shieldman special
		else if (currentCharacter.getName().equals("ShieldMan")
				&& TurnManagement.canSpecial(currentCharacter.getFacing())
				&& currentCharacter.attackHit(otherCharacter.getPosition())
				&& otherCharacter.getStatus(StatusEnum.BLOCKING))
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// Lanceman special
		else if (currentCharacter.getName().equals("LanceMan") && currentCharacter.getDistance(otherCharacter) <= 20
				&& currentCharacter.getDistance(otherCharacter) > otherCharacter.getSpeed())
		{
			currentCharacter.specialAttack(otherCharacter);
		}

		// Archer special
		else if (currentCharacter.getDistance(otherCharacter) <= 3 && currentCharacter.getName().equals("Archer")
				&& TurnManagement.canSpecial(currentCharacter.getFacing()))
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// Alchemist special
		else if (currentCharacter.attackHit(otherCharacter.getPosition())
				&& currentCharacter.getName().equals("Alchemist")
				&& TurnManagement.canSpecial(currentCharacter.getFacing()))
		{
			currentCharacter.specialAttack(otherCharacter);
		}
		// Alchemist || assasin attack
		else if (currentCharacter.attackHit(otherCharacter.getPosition())
				&& (currentCharacter.getName().equals("Alchemist") || currentCharacter.getName().equals("Assasin")))
		{
			currentCharacter.attack(otherCharacter);
		}
		// Normal atack
		else if (currentCharacter.attackHit(otherCharacter.getPosition())
				&& !otherCharacter.getStatus(StatusEnum.BLOCKING))
		{
			currentCharacter.attack(otherCharacter);
		}
		// Heal
		else if (TurnManagement.canHeal(currentCharacter.getFacing())
				&& (currentCharacter.getHealth() < currentCharacter.getMaxHealth())
				&& (!otherCharacter.attackHit(currentCharacter.getPosition())
						|| (currentCharacter.getHealing() > otherCharacter.getDamage())))
		{
			currentCharacter.heals();
		}
		// Block
		else if (currentCharacter.getDistance(otherCharacter) > otherCharacter.getSpeed()
				&& otherCharacter.attackHit(currentCharacter.getPosition())
				&& TurnManagement.canBlock(currentCharacter.getFacing()))
		{
			currentCharacter.block();
		}
		// Move to the opponents
		else
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

	public static boolean AIGrab(Characters currentCharacter, Characters otherCharacter)
	{
		boolean goGrab = false;

		if (otherCharacter.getStatus(StatusEnum.BLOCKING)
				&& currentCharacter.getDistance(otherCharacter) <= currentCharacter.getSpeed()
				&& (Characters.statusObject.get(1)
						.getTurnGotten(currentCharacter.getFacing()) == (TurnManagement.getTurn() - 1)))
		{
			if (currentCharacter.getDistance(otherCharacter) == currentCharacter.getPosition())
			{
				goGrab = true;
			} else if (aiPassTrough(currentCharacter, otherCharacter.getPosition()))
			{
				goGrab = true;
			} else if (currentCharacter.getHealth() > 9)
			{
				goGrab = true;
			}
		}

		return goGrab;
	}

	public static void moveTo(Characters currentCharacter, Characters otherCharacter, int goal)
	{
		if (currentCharacter.getPosition() > otherCharacter.getPosition())
		{
			if (goal == currentCharacter.getPosition() - currentCharacter.getSpeed())
			{
				currentCharacter.jump(0);
			} else
			{
				currentCharacter.moveForward(currentCharacter.getPosition() - goal);
			}
		} else
		{
			if (goal == currentCharacter.getPosition() + currentCharacter.getSpeed())
			{
				currentCharacter.jump(1);
			} else
			{
				currentCharacter.moveBackward(goal - currentCharacter.getPosition());
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

	public static boolean canKill(double damage, double armor, double health)
	{
		boolean canKill = false;
		double reducedHit = damage / 2;
		if (damage > armor * 2)
		{

			if (!Characters.validateArmor(armor - reducedHit))
			{
				reducedHit -= armor;
				if (reducedHit * 2 >= health)
				{
					canKill = true;
				}
			}

		}

		return canKill;
	}
}
