package application;

import java.util.ArrayList;

import character.AI;
import character.Characters;
import character.Status;
import character.StatusInfo;
import characterSpecialisation.Alchemist;
import characterSpecialisation.Archer;
import characterSpecialisation.Assasin;
import characterSpecialisation.LanceMan;
import characterSpecialisation.Shieldman;
import characterSpecialisation.Swordsman;
import environement.Field;
import environement.Obstacle;
import environement.TurnManagement;
import obstacleTrait.Mud;
import obstacleTrait.Spike;
import obstacleTrait.ToxicSwamp;
import obstacleTrait.WallOfFire;
import playerInteraction.Input;
import playerInteraction.Output;

public class Application
{
	public static final double OBSTACLE_RATIO = 0.2;

	static Characters player1 = null;
	static Characters player2 = null;
	static Field playField = null;
	static ArrayList<Obstacle> obstacle = new ArrayList<>();
	static boolean isObstacle = true;

	public static void play()
	{
		playField = createField();
		obstacle = createObstacle();
		if (obstacle == null)
		{
			isObstacle = false;
		}
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
			fieldLenght = Input.askInt("What field lenght do you want to play on? (lenght between 5 and 100)");
		} while (fieldLenght < Field.MIN_LENGHT || fieldLenght > Field.MAX_LENGHT);

		return new Field(fieldLenght);
	}

	public static ArrayList<Obstacle> createObstacle()
	{
		int areObstacle;
		ArrayList<Obstacle> newObstacle = new ArrayList<>();

		do
		{
			areObstacle = Input.askInt("Do you want obstacle on the field? \n\n0: No      1: Yes");
		} while (areObstacle <= 0 && areObstacle >= 1);

		if (areObstacle == 1)
		{
			do
			{
				ArrayList<Obstacle> obstacleList = new ArrayList<>();
				obstacleList.add(obstacleSpacing(1, newObstacle));
				obstacleList.add(obstacleSpacing(2, newObstacle));
				obstacleList.add(obstacleSpacing(3, newObstacle));
				obstacleList.add(obstacleSpacing(4, newObstacle));

				newObstacle.add(obstacleList.get((int) (Math.random() * (obstacleList.size()))));
			} while (moreObstacle(newObstacle));
		}
		return newObstacle;

	}

	public static Obstacle findObstacle(int pNumber)
	{
		Obstacle newObst = null;
		switch (pNumber)
		{
		case 1:
			newObst = new Spike();
			break;
		case 2:
			newObst = new Mud();
			break;
		case 3:
			newObst = new WallOfFire();
			break;
		case 4:
			newObst = new ToxicSwamp();
			break;
		}
		return newObst;
	}

	public static Obstacle obstacleSpacing(int pNumber, ArrayList<Obstacle> obstacleList)
	{
		Obstacle newObst = findObstacle(pNumber);

		for (int i = 0; i < obstacleList.size(); i++)
		{
			while (newObst.getDistance(obstacleList.get(i)) <= 5)
			{
				newObst.setPosition();
				i = 0;
			}
		}

		return newObst;
	}

	public static boolean moreObstacle(ArrayList<Obstacle> newObstacle)
	{
		int sum = 0;
		for (int i = 0; i < newObstacle.size(); i++)
		{
			sum += newObstacle.get(i).getLenght();
		}
		return sum < Field.getLenght() * OBSTACLE_RATIO;
	}

	public static int numberPlayer()
	{
		int numberOfPlayer = 0;
		do
		{
			numberOfPlayer = (Input
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
			startingPosition = Field.getLenght();
			player = "Player 2";
		}

		characterList.add(new Swordsman(isFirst, startingPosition));
		characterList.add(new LanceMan(isFirst, startingPosition));
		characterList.add(new Archer(isFirst, startingPosition));
		characterList.add(new Assasin(isFirst, startingPosition));
		characterList.add(new Shieldman(isFirst, startingPosition));
		characterList.add(new Alchemist(isFirst, startingPosition));

		String choice = "";
		if (isPlayer)
		{
			for (int i = 0; i < characterList.size(); i++)
			{
				choice += "\n\n" + (i + 1) + ": " + characterList.get(i);
			}
			do
			{
				choose = (Input.askInt(player + " choose your character" + choice));
			} while (choose <= 1 && choose >= characterList.size() + 1);

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

		updateStatus(currentCharacter);

		if (!isPlayer)
		{
			move = AI.aiTurn(currentCharacter, otherCharacter);
		} else
		{

			if (currentCharacter.getStatus(Status.STUNNED))
			{
				move = 0;
			} else
			{
				do
				{
					move = (Input.askInt(createDisplay(currentCharacter)));
				} while (move <= 1 && move >= 8);
			}

		}
		takeAction(currentCharacter, move);
		TurnManagement.newTurn();
		standOnObstacle(currentCharacter);

	}

	public static void takeAction(Characters currentCharacter, int pMove)
	{

		Characters otherCharacter = findOtherCharacter(currentCharacter);
		switch (pMove)
		{
		case 0:
			break;
		case 1:
			currentCharacter.moveForward(spaceMove(currentCharacter));
			break;
		case 2:
			currentCharacter.moveBackward(spaceMove(currentCharacter));
			break;
		case 3:
			currentCharacter.jump(jumpSide(currentCharacter));
			break;
		case 4:
			currentCharacter.attack(otherCharacter);
			break;
		case 5:
			currentCharacter.heals();
			break;
		case 6:
			currentCharacter.block();
			break;
		case 7:
			currentCharacter.grab(otherCharacter);
			break;
		case 8:
			currentCharacter.specialAttack(otherCharacter);
			break;

		}
	}

	public static int spaceMove(Characters currentCharacter)
	{
		int mouvement = 0;

		do
		{
			mouvement = (Input.askInt("How many space do you want to travel?\n\n Your speed is: "
					+ currentCharacter.getSpeed() + "\n\n the distance between you is: "
					+ currentCharacter.getDistance(findOtherCharacter(currentCharacter)) + "\n\n" + currentField()));
		} while (mouvement <= 0 && mouvement >= currentCharacter.getSpeed());

		return mouvement;
	}

	public static int jumpSide(Characters currentCharacter)
	{
		int side = 0;
		do
		{
			side = (Input.askInt(
					"Do you want to jump forward or backward?\n\n 0: forward     1: backward\n\n Your speed is: "
							+ currentCharacter.getSpeed() + "\n\n the distance between you is: "
							+ currentCharacter.getDistance(findOtherCharacter(currentCharacter)) + "\n\n"
							+ currentField()));
		} while (side <= 0 && side >= 1);
		return side;
	}

	public static void obstacleDetection(Characters currentCharacter, int lastPosition)
	{
		for (int i = 0; i < obstacle.size(); i++)
		{
			obstacle.get(i).passTrough(currentCharacter, lastPosition);
		}
	}

	public static void standOnObstacle(Characters currentCharacter)
	{
		for (int i = 0; i < obstacle.size(); i++)
		{
			obstacle.get(i).standOn(currentCharacter);
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

	public static String createDisplay(Characters currentCharacter)
	{
		String canHeal;
		String canBlock;
		String canSpecial;

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
		if (TurnManagement.canSpecial(currentCharacter.getFacing()))
		{
			canSpecial = "can special";
		} else
		{
			canSpecial = "can't special";
		}

		String message1 = "Turn " + TurnManagement.getTurn() + " " + player + "choose your move\n";
		String message2 = "1: Move forward   2: Move backward   3: Jump   4: Attack    5: Heal   6: Block   7: Grab   8: Special attack\n\n";
		String message3 = "Player 1 Health: " + player1.getHealth() + " Armor: " + player1.getArmor()
				+ "   Player 2 Health: " + player2.getHealth() + " Armor:     " + player2.getArmor();
		String message4 = "\n\n" + player + canHeal + "\n" + player + canBlock + "\n" + player + canSpecial;
		String message5 = "\n\nPlayer 1: " + player1.statusToString() + "   Player 2: " + player2.statusToString();
		String message6 = "\n\nPlayer 1: " + player1 + "\n\nPlayer 2: " + player2 + "\n\nThe distance between you is "
				+ currentCharacter.getDistance(findOtherCharacter(currentCharacter));

		return message1 + message2 + message3 + message4 + message5 + message6 + "\n" + currentField();
	}

	public static String currentField()
	{
		String oldField = "";
		String currentField = "";
		String obstacleField = "";
		if(!isObstacle)
		{
		for (int i = 0; i <= Field.getLenght(); i++)
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
		}
		else
		{
			for (int i = 0; i <= Field.getLenght(); i++)
			{

				oldField = obstacleField;
				if (i == player1.getPosition() && i == player2.getPosition())
				{
					obstacleField += "12";
				} else if (i == player1.getPosition())
				{
					obstacleField += "1_ ";
				} else if (i == player2.getPosition())
				{
					obstacleField += " 2 ";
				}

				else
				{
					for (int j = 0; j < obstacle.size(); j++)
					{

						if (i >= obstacle.get(j).getPosition()
								&& i < obstacle.get(j).getPosition() + obstacle.get(j).getLenght())
						{
							obstacleField += obstacle.get(j).getSprite();
						}
					}
				}
				if (oldField.equals(obstacleField))
				{
					obstacleField += "_ ";
				}

			}
		}
		return currentField + "\n" + obstacleField;
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

	public static void main(String[] args)
	{
		play();
	}

}
