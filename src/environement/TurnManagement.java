package environement;

import character.Characters;
import character.StatusEnum;

public class TurnManagement
{
	private static final int BLOCK_DURATION = 3;
	private static final int STUN_DURATION = 3;
	private static final int SLOW_DURATION = 3;
	private static final int POIS_DURATION = 5;
	private static final int BURN_DURATION = 5;

	private static int lastHealP1 = -10;
	private static int lastHealP2 = -10;
	private static int lastBlockP1 = -10;
	private static int lastBlockP2 = -10;
	private static int lastSpecialP1 = -10;
	private static int lastSpecialP2 = -10;

	private static int statBlockP1 = 0;
	private static int statBlockP2 = 0;
	private static int statStunP1 = 0;
	private static int statStunP2 = 0;
	private static int statSlowP1 = 0;
	private static int statSlowP2 = 0;
	private static int statPoisP1 = 0;
	private static int statPoisP2 = 0;
	private static int statBurnP1 = 0;
	private static int statBurnP2 = 0;

	private static int currentTurn = 1;

	public static void setLastHeal(boolean pFacing)
	{
		if (pFacing)
		{
			lastHealP1 = currentTurn;

		} else
		{
			lastHealP2 = currentTurn;
		}

	}

	public static boolean canHeal(boolean pFacing)
	{
		return pFacing ? currentTurn > (lastHealP1 + 2) : currentTurn > (lastHealP2 + 2);
	}

	public static boolean canBeHealing(boolean pFacing)
	{
		boolean canHeal = canHeal(pFacing);
		setLastHeal(pFacing);
		return canHeal;
	}

	public static void setLastBlock(boolean pFacing)
	{
		if (pFacing)
		{
			lastBlockP1 = currentTurn;

		} else
		{
			lastBlockP2 = currentTurn;
		}

	}

	public static boolean canBlock(boolean pFacing)
	{
		return pFacing ? currentTurn > (lastBlockP1 + 2) : currentTurn > (lastBlockP2 + 2);
	}

	public static boolean canBeBlocking(boolean pFacing)
	{
		boolean canBlock = canBlock(pFacing);
		setLastBlock(pFacing);
		return canBlock;
	}

	public static void setLastSpecial(boolean pFacing)
	{
		if (pFacing)
		{
			lastSpecialP1 = currentTurn;

		} else
		{
			lastSpecialP2 = currentTurn;
		}

	}

	public static boolean canSpecial(boolean pFacing)
	{
		return pFacing ? currentTurn > (lastSpecialP1 + 6) : currentTurn > (lastSpecialP2 + 6);
	}

	public static boolean canBeSpecialing(boolean pFacing)
	{
		boolean canSpecial = canSpecial(pFacing);
		setLastSpecial(pFacing);
		return canSpecial;
	}

	public static void statusStart(Characters currentCharacter, StatusEnum pStatus)
	{
		boolean isPlayer1 = false;
		if (currentCharacter.getFacing())
		{
			isPlayer1 = true;
		}

		switch (pStatus)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			break;
		case STUNNED:
			stunTime(isPlayer1);
			break;
		case SLOWED:
			slowTime(isPlayer1);
			break;
		case POISONED:
			poisTime(isPlayer1);
			break;
		case BURNED:
			burnTime(isPlayer1);
			break;

		}
	}

	public static boolean statusEnd(Characters currentCharacter, StatusEnum pStatus)
	{
		boolean satusEnd = false;
		switch (pStatus)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			satusEnd = blockOver(playerTimer(currentCharacter, pStatus));
			break;
		case STUNNED:
			satusEnd = stunOver(playerTimer(currentCharacter, pStatus));
			break;
		case SLOWED:
			satusEnd = slowOver(playerTimer(currentCharacter, pStatus));
			break;
		case POISONED:
			satusEnd = poisOver(playerTimer(currentCharacter, pStatus));
			break;
		case BURNED:
			satusEnd = burnOver(playerTimer(currentCharacter, pStatus));
			break;
		}
		return satusEnd;
	}

	public static int playerTimer(Characters currentCharacter, StatusEnum pStatus)
	{
		boolean isPlayer1 = false;
		int timer = 0;

		if (currentCharacter.getFacing())
		{
			isPlayer1 = true;
		}

		switch (pStatus)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			timer = isPlayer1 ? statBlockP1 : statBlockP2;
			break;
		case STUNNED:
			timer = isPlayer1 ? statStunP1 : statStunP2;
			break;
		case SLOWED:
			timer = isPlayer1 ? statSlowP1 : statSlowP2;
			break;
		case POISONED:
			timer = isPlayer1 ? statPoisP1 : statPoisP2;
			break;
		case BURNED:
			timer = isPlayer1 ? statBurnP1 : statBurnP2;
			break;
		}
		return timer;
	}

	public static void statusActivate(StatusEnum pStatus, Characters currentCharacter)
	{
		switch (pStatus)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			break;
		case STUNNED:
			break;
		case SLOWED:
			break;
		case POISONED:
			currentCharacter.poisDamage();
			break;
		case BURNED:
			currentCharacter.burnDamage();
			break;
		}
	}

	private static boolean blockOver(int timer)
	{
		return currentTurn - timer >= BLOCK_DURATION;
	}

	private static void stunTime(boolean isPlayer1)
	{
		if (isPlayer1)
		{
			statStunP1 = currentTurn;
		} else
		{
			statStunP2 = currentTurn;
		}
	}

	private static boolean stunOver(int timer)
	{
		return currentTurn - timer >= STUN_DURATION;
	}

	private static void slowTime(boolean isPlayer1)
	{
		if (isPlayer1)
		{
			statSlowP1 = currentTurn;
		} else
		{
			statSlowP2 = currentTurn;
		}
	}

	private static boolean slowOver(int timer)
	{
		return currentTurn - timer >= SLOW_DURATION;
	}

	private static void poisTime(boolean isPlayer1)
	{
		if (isPlayer1)
		{
			statPoisP1 = currentTurn;
		} else
		{
			statPoisP2 = currentTurn;
		}
	}

	private static boolean poisOver(int timer)
	{
		return currentTurn - timer >= POIS_DURATION;
	}

	private static void burnTime(boolean isPlayer1)
	{
		if (isPlayer1)
		{
			statBurnP1 = currentTurn;
		} else
		{
			statBurnP2 = currentTurn;
		}
	}

	private static boolean burnOver(int timer)
	{
		return currentTurn - timer >= BURN_DURATION;
	}

	public static void newTurn()
	{
		currentTurn++;
	}

	public static int getTurn()
	{
		return currentTurn;
	}
}
