package character;

public enum StatusEnum
{
	NO_STATUS("No status"), BLOCKING("Blocking"), STUNNED("Stunned"), SLOWED("Slowed"), POISONED("Poisoned"),
	BURNED("Burned");
	
	private final String stringStatus;
	
	private StatusEnum(String name)
	{
		stringStatus = name;
	}

	public String toString()
	{
		return stringStatus;
	}
}
