package playerInteraction;

import javax.swing.JOptionPane;

public class Input
{
	public static String askInt(String pQuestion)
	{
		//TODO add condition
		String answer = "";
		answer = JOptionPane.showInputDialog(null, pQuestion);
		return answer;
	}
}
