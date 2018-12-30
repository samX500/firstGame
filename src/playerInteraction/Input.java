package playerInteraction;

import javax.swing.JOptionPane;

public class Input
{
	public static int askInt(String pQuestion)
	{
		//TODO add condition
		int answer;
		answer = Integer.parseInt(JOptionPane.showInputDialog(null, pQuestion));
		return answer;
	}
}
