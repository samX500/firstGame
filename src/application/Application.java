package application;

import java.util.ArrayList;

import character.Characters;
import environement.Field;
import playerInteraction.Input;
import playerInteraction.Output;

public class Application
{
	static Characters player1 = null;
	static Characters player2 = null;
	static Field playField = null;

	public static void play()
	{
		playField = createField();
		int numberOfPlayer = numberPlayer();
		boolean isPlayer = false;
		if (numberOfPlayer == 2)
		{
			isPlayer = true;
		}

		player1 = createPlayer(true, true);
		player2 = createPlayer(false, isPlayer);

		ArrayList<Characters> playerList = new ArrayList<>();
		playerList.add(player1);
		playerList.add(player2);

		boolean rotate = true;
		int value;
		while (!player1.die() && !player2.die())
		{
			value = rotate ? 0 : 1;
			if (playerList.get(value) == player1 || isPlayer)
			{
				takeTurn(playerList.get(value), true);
			} else
			{
				takeTurn(playerList.get(value), false);
			}

			rotate = !rotate;
		}

		String player;
		Characters winningPlayer = null;
		if (player1.die())
		{
			player = "Player 2";
			winningPlayer = player2;
		} else
		{
			player = "Player 1";
			winningPlayer = player1;
		}
		Output.message(player + " has won with the " + winningPlayer.getName() + " with " + winningPlayer.getHealth()
				+ " health remaining!");

	}

	public static Field createField()
	{
		int fieldLenght;
		do
		{
			fieldLenght = Integer
					.parseInt(Input.askInt("What field lenght do you want to play on? (lenght between 5 and 100)"));
		} while (fieldLenght < Field.MIN_LENGHT || fieldLenght > Field.MAX_LENGHT);
		return new Field(fieldLenght);
	}

	public static int numberPlayer()
	{
		int numberOfPlayer = 0;
		do
		{
			numberOfPlayer = Integer.parseInt(Input
					.askInt("Do you want to play with a friend or against an AI?\n1: With an AI   2: With a friend"));

		} while (numberOfPlayer != 1 && numberOfPlayer != 2);
		return numberOfPlayer;
	}

	public static Characters createPlayer(boolean isFirst, boolean isPlayer)
	{
		int choose;
		int startingPosition;
		String player;
		ArrayList<Characters> characterList = new ArrayList<>();
		if (isFirst)
		{
			startingPosition = 0;
			player = "Player 1";
		} else
		{
			startingPosition = playField.getLenght();
			player = "Player 2";
		}
		Characters sword = new Characters("Swordsman", 2, 4, startingPosition, 25, 10, isFirst);
		Characters lance = new Characters("Lanceman", 5, 2, startingPosition, 25, 10, isFirst);
		Characters bow = new Characters("Archer", 10, 1, startingPosition, 25, 8, isFirst);
		Characters assasin = new Characters("Assasin", 1, 8, startingPosition, 20, 20, isFirst);
		Characters shield = new Characters("Shieldman", 2, 1, startingPosition, 50, 5, isFirst);
		characterList.add(sword);
		characterList.add(lance);
		characterList.add(bow);
		characterList.add(assasin);
		characterList.add(shield);
		String choice = "";
		if (isPlayer)
		{
			for (int i = 0; i < characterList.size(); i++)
			{
				choice += "\n\n" + (i+1) + ": " + characterList.get(i);
			}
			choose = Integer.parseInt(Input.askInt(player + " choose your character" + choice));
		} else
		{
			choose = (int) (Math.random() * (characterList.size()) + 1);
			Output.message("The AI chose the character " + characterList.get(choose - 1));
		}
		return characterList.get(choose - 1);
	}

	public static void takeTurn(Characters currentCharacter, boolean isPlayer)
	{
		int move;
		String player = "Player 2 ";
		Characters otherCharacter = findOtherCharacter(currentCharacter);
		if (currentCharacter == player1)
		{
			player = "Player 1 ";
		}
		if (!isPlayer)
		{
			move = aiTurn(currentCharacter, otherCharacter);
		} else
		{
			do
			{
				move = Integer.parseInt(Input.askInt(
						player + "choose your move\n1: Move forward   2: Move backward   3: Attack\n\nPlayer 1 health: "
								+ player1.getHealth() + "   Player 2 health:" + player2.getHealth()
								+ "\n\nPlayer 1 range: " + player1.getRange() + "   Player 2 range: "
								+ player2.getRange() + "\nThe distance between you is "
								+ currentCharacter.getDistance(otherCharacter) + "\n" + currentField()));
			} while (move <= 1 && move >= 3);
		}
		switch (move)
		{
		case 1:
			currentCharacter.moveForward(currentCharacter.getFacing());
			break;
		case 2:
			currentCharacter.moveBackward(currentCharacter.getFacing());
			break;
		case 3:
			currentCharacter.attack(otherCharacter);
		}
	}

	public static Characters findOtherCharacter(Characters currentCharacter)
	{
		Characters otherCharacter;
		if (currentCharacter == player1)
		{
			otherCharacter = player2;
		} else
		{
			otherCharacter = player1;
		}
		return otherCharacter;
	}

	public static String currentField()
	{
		String currentField = "";
		for (int i = 0; i <= playField.getLenght(); i++)
		{
			if (i == player1.getPosition() && i == player2.getPosition())
			{
				currentField += "12";
			} else if (i == player1.getPosition())
			{
				currentField += "1_ ";
			} else if (i == player2.getPosition())
			{
				currentField += " 2 ";
			} else
			{
				currentField += "_ ";
			}
		}
		return currentField;
	}

	public static int aiTurn(Characters currentCharacter, Characters otherCharacter)
	{
		//TODO better AI
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

	public static void main(String[] args)
	{
		play();
	}

}
