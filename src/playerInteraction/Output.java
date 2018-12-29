package playerInteraction;

import javax.swing.JOptionPane;

public class Output
{
	public static void message(String message)
	{
		JOptionPane.showMessageDialog(null, message, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
