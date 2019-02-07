package playerInteraction;

import javax.swing.JOptionPane;

public class Input
{
	public static int askInt(String pQuestion)
	{
		String answerString;
		int answer;
		answerString = JOptionPane.showInputDialog(null, pQuestion);
		if(answerString == null)
		{
			Output.message("The program will stop");
			System.exit(0);
		}
		answer = Integer.parseInt(answerString);
		return answer;
	}
}
