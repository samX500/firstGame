package application;

import java.util.ArrayList;

import character.Characters;
import environement.Field;
import playerInteraction.Input;

public class Application
{
	static Characters player1 = null;
	static Characters player2 = null;
	static Field playField = null;

	public static void play()
	{
		boolean isPlayer = false;
		playField = createField();
		int numberOfPlayer = numberPlayer();
		player1 = createPlayer(0, true);
		if (numberOfPlayer == 2)
		{
			isPlayer = true;
		}
		player2 = createPlayer(playField.getLenght(), isPlayer);

	}

	public static Field createField()
	{
		int fieldLenght;
		do
		{
			fieldLenght = Integer
					.parseInt(Input.askInt("What field lenght do you want to play on? (lenght between 15 and 100)"));
		} while (fieldLenght > Field.MIN_LENGHT && fieldLenght < Field.MAX_LENGHT);
		return new Field(fieldLenght);
	}

	public static int numberPlayer()
	{
		int numberOfPlayer = 0;
		do
		{
			numberOfPlayer = Integer.parseInt(Input
					.askInt("Do you want to play with a friend or against an AI?\n1: With a friend\t2: With an AI"));

		} while (numberOfPlayer != 1 || numberOfPlayer != 2);
		return numberOfPlayer;
	}

	public static Characters createPlayer(int startingPosition, boolean isPlayer)
	{
		int choose;
		// TODO merge list and object creation if I don't need the object name
		ArrayList<Characters> characterList = new ArrayList<>();
		Characters sword = new Characters("Swordsman", 2, 4, startingPosition, 25, 10);
		Characters lance = new Characters("Lanceman", 5, 2, startingPosition, 25, 10);
		Characters bow = new Characters("Archer", 10, 1, startingPosition, 25, 8);
		Characters assasin = new Characters("Assasin", 1, 8, startingPosition, 20, 20);
		Characters shield = new Characters("Shieldman", 2, 1, startingPosition, 50, 5);
		characterList.add(sword);
		characterList.add(lance);
		characterList.add(bow);
		characterList.add(assasin);
		characterList.add(shield);
		if (isPlayer)
		{
			choose = Integer.parseInt(Input.askInt("Choose your character\n1: " + sword + "2: " + lance + "3: " + bow
					+ "4: " + assasin + "5: " + shield));
		} else
		{
			choose = (int) (Math.random() * (characterList.size() + 1));
		}
		return characterList.get(choose + 1);
	}

	public static void main(String[] args)
	{

	}

}
