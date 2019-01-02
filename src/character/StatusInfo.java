package character;

public class StatusInfo
{

	public static String statusString(StatusEnum pStatus)
	{
		String currentStatus = "";
		switch (pStatus)
		{
		case NO_STATUS:
			currentStatus = "no status";
			break;
		case BLOCKING:
			currentStatus = "blocking";
			break;
		case STUNNED:
			currentStatus = "stunned";
			break;
		case SLOWED:
			currentStatus = "slowed";
			break;
		case POISONED:
			currentStatus = "poisoned";
			break;
		case BURNED:
			currentStatus = "burned";
			break;
		}
		
		return currentStatus;
	}
}
