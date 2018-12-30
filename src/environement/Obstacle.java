package environement;

public class Obstacle
{
	private static final double MAX_DAMAGE = 10;
	private static final double MIN_DAMAGE = 0;
	private static final int MAX_LENGHT = 3;
	private static final int MIN_LENGHT = 1;
	private static final int MAX_POSITION = Field.getLenght() - 5;
	private static final int MIN_POSITION = 5;

	private int position;
	private int lenght;
	private double damage;

	public Obstacle(double pDamage)
	{
		setPosition();
		setLenght();
		setDamage(pDamage);
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition()
	{
		position = (int) (Math.random() * (MAX_POSITION - MIN_POSITION + 1) + MIN_POSITION);
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght()
	{
		lenght = (int) (Math.random() * (MAX_LENGHT - MIN_LENGHT + 1) + MIN_LENGHT);
	}

	public double getDamage()
	{
		return damage;
	}

	public void setDamage(double pDamage)
	{
		if (validateDamage(pDamage))
		{
			damage = pDamage;
		}
	}

	private boolean validateDamage(double pDamage)
	{
		return pDamage >= MIN_DAMAGE && pDamage <= MAX_DAMAGE;
	}

	@Override
	public String toString()
	{
		// TODO do a better toString
		return position + " " + lenght + " " + damage;
	}
}
