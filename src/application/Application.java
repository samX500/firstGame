package application;

import java.util.ArrayList;

import character.Characters;
import character.Status;
import character.StatusInfo;
import environement.Field;
import environement.TurnManagement;
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
			if (isPlayer)
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
				+ " health and " + winningPlayer.getArmor() + " armor remaining!");

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
		// name,range,speed,maxhealth,healing,armor,damage,facing,position,health,status
		characterList.add(new Characters("Swordsman", 2, 4, 25, 5, 10, 10, isFirst, startingPosition));
		characterList.add(new Characters("Lanceman", 5, 2, 25, 5, 10, 10, isFirst, startingPosition));
		characterList.add(new Characters("Archer", 10, 1, 25, 5, 5, 10, isFirst, startingPosition));
		characterList.add(new Characters("Assasin", 1, 8, 20, 0, 0, 20, isFirst, startingPosition));
		characterList.add(new Characters("Shieldman", 2, 1, 30, 5, 30, 10, isFirst, startingPosition));
		characterList.add(new Characters("Doctor", 2, 3, 30, 15, 5, 5, isFirst, startingPosition));
		String choice = "";
		if (isPlayer)
		{
			for (int i = 0; i < characterList.size(); i++)
			{
				choice += "\n\n" + (i + 1) + ": " + characterList.get(i);
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
		Characters otherCharacter = findOtherCharacter(currentCharacter);

		if (!isPlayer)
		{
			move = aiTurn(currentCharacter, otherCharacter);
		} else
		{
			updateStatus(currentCharacter);

			if (currentCharacter.getStatus(Status.STUNNED))
			{
				move = 0;
			} else
			{
				do
				{
					move = Integer.parseInt(Input.askInt(createDisplay(currentCharacter)));
				} while (move <= 1 && move >= 6);
			}
			
			
		}
		takeAction(currentCharacter, move);
		TurnManagement.newTurn();
		
	}

	public static void takeAction(Characters currentCharacter, int pMove)
	{

		Characters otherCharacter = findOtherCharacter(currentCharacter);
		switch (pMove)
		{
		case 0:
			break;
		case 1:
			currentCharacter.moveForward(currentCharacter.getFacing(),spaceMove(currentCharacter));
			break;
		case 2:
			currentCharacter.moveBackward(currentCharacter.getFacing(),spaceMove(currentCharacter));
			break;
		case 3:
			currentCharacter.attack(otherCharacter);
			break;
		case 4:
			currentCharacter.heals();
			break;
		case 5:
			currentCharacter.block();
			break;
		case 6:
			currentCharacter.grab(otherCharacter);
		}
	}

	public static int spaceMove(Characters currentCharacter)
	{
		return Integer.parseInt(Input.askInt("How many space do you want to travel?\n\n Your speed is: " + currentCharacter.getSpeed() + "\n\n the distance between you is: " + currentCharacter.getDistance(findOtherCharacter(currentCharacter)) + "\n\n" + currentField()));
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

	public static String createDisplay(Characters currentCharacter)
	{
		String canHeal;
		String canBlock;
		
		String player = "Player 2 ";
		if (currentCharacter == player1)
		{
			player = "Player 1 ";
		}
		if (TurnManagement.canHeal(currentCharacter.getFacing()))
		{
			canHeal = "can heal";
		} else
		{
			canHeal = "can't heal";
		}
		if (TurnManagement.canBlock(currentCharacter.getFacing()))
		{
			canBlock = "can block";
		} else
		{
			canBlock = "can't block";
		}
	
		String message1 = "Turn " + TurnManagement.getTurn() + " " + player + "choose your move\n";
		String message2 = "1: Move forward   2: Move backward   3: Attack    4: Heal   5: Block   6: Grab\n\n";
		String message3 = "Player 1 Health: " + player1.getHealth() + " Armor: " + player1.getArmor()
				+ "   Player 2 Health: " + player2.getHealth() + " Armor:     " + player2.getArmor();
		String message4 = "\n\n" + player + canHeal + "\n" + player + canBlock;
		String message5 = "\n\nPlayer 1: "+ player1.statusToString() +"   Player 2: " + player2.statusToString();
		String message6 = "\n\nPlayer 1: " + player1 + "\n\nPlayer 2: " + player2 + "\n\nThe distance between you is "
				+ currentCharacter.getDistance(findOtherCharacter(currentCharacter));

		return message1 + message2 + message3 + message4 + message5 + message6+ "\n" + currentField();
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

	public static void updateStatus(Characters currentCharacter)
	{
		for (int i = 1; i < Status.values().length; i++)
		{
			if (currentCharacter.getStatus(character.Status.values()[i]))
			{
				if (TurnManagement.statusEnd(character.Status.values()[i]))
				{
					currentCharacter.removeStatus(character.Status.values()[i]);
				} else
				{
					TurnManagement.statusActivate(character.Status.values()[i], currentCharacter);
				}
			}
		}
	}

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

	public static void main(String[] args)
	{
		play();
	}

}
