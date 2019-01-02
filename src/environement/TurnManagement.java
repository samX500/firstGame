package environement;

import character.Characters;
import character.StatusEnum;

public class TurnManagement
{
	private static int lastHealP1 = -10;
	private static int lastHealP2 = -10;
	private static int lastBlockP1 = -10;
	private static int lastBlockP2 = -10;
	private static int lastSpecialP1 = -10;
	private static int lastSpecialP2 = -10;

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

	public static void newTurn()
	{
		currentTurn++;
	}

	public static int getTurn()
	{
		return currentTurn;
	}
}
