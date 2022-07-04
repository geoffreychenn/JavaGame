package unsw.dungeon;

public class ExitGoal implements Goal
{
	private Player player;
	private Exit exit;

	public ExitGoal(Player player, Exit exit)
	{
		super();
		this.player = player;
		this.exit = exit;
	}

	@Override
	public boolean isSatisfied()
	{
		return player.getX() == exit.getX() && player.getY() == exit.getY();
	}

	@Override
	public String toString()
	{
		return "Get to the exit";
	}

}
