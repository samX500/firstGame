package environement;

import character.Characters;
import character.Status;

public class TurnManagement
{
	private static final int BLOCK_DURATION = 3;
	// TODO maybe this can be an issue
	private static final int STUN_DURATION = 4;
	private static final int SLOW_DURATION = 5;
	private static final int POIS_DURATION = 7;
	private static final int BURN_DURATION = 7;

	private static int lastHealP1 = -10;
	private static int lastHealP2 = -10;
	private static int lastBlockP1 = -10;
	private static int lastBlockP2 = -10;
	private static int lastSpecialP1 = -10;
	private static int lastSpecialP2 = -10;

	private static int statBlock = 0;
	private static int statStun = 0;
	private static int statSlow = 0;
	private static int statPois = 0;
	private static int statBurn = 0;

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
		setLastBlock(pFacing);
		return canSpecial;
	}
	

	public static void statusStart(Status pStatus)
	{
		switch (pStatus)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			blockingTime();
			break;
		case STUNNED:
			stunTime();
			break;
		case SLOWED:
			slowTime();
			break;
		case POISONED:
			poisTime();
			break;
		case BURNED:
			burnTime();
			break;

		}
	}

	public static boolean statusEnd(Status pStatus)
	{
		boolean satusEnd = false;
		switch (pStatus)
		{
		case NO_STATUS:
			break;
		case BLOCKING:
			satusEnd = blockOver();
			break;
		case STUNNED:
			satusEnd = stunOver();
			break;
		case SLOWED:
			satusEnd = slowOver();
			break;
		case POISONED:
			satusEnd = poisOver();
			break;
		case BURNED:
			satusEnd = burnOver();
			break;
		}
		return satusEnd;
	}

	public static void statusActivate(Status pStatus, Characters currentCharacter)
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
			// TODO create something that does speed/2
			break;
		case POISONED:
			currentCharacter.poisDamage();
			break;
		case BURNED:
			currentCharacter.burnDamage();
			break;
		}
	}

	private static void blockingTime()
	{
		statBlock = currentTurn;
	}

	private static boolean blockOver()
	{
		return currentTurn - statBlock >= BLOCK_DURATION;
	}

	private static void stunTime()
	{
		statStun = currentTurn;
	}

	private static boolean stunOver()
	{
		return currentTurn - statStun >= STUN_DURATION;
	}

	private static void slowTime()
	{
		statSlow = currentTurn;
	}

	private static boolean slowOver()
	{
		return currentTurn - statSlow >= SLOW_DURATION;
	}

	private static void poisTime()
	{
		statPois = currentTurn;
	}

	private static boolean poisOver()
	{
		return currentTurn - statPois >= POIS_DURATION;
	}

	private static void burnTime()
	{
		statBurn = currentTurn;
	}

	private static boolean burnOver()
	{
		return currentTurn - statBurn >= BURN_DURATION;
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
