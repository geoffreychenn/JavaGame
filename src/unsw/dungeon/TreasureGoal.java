package unsw.dungeon;

public class TreasureGoal implements Goal
{
	private Player player;
	private int treasureRequired;
	
	public TreasureGoal(Player player, int treasureRequired)
	{
		super();
		this.player = player;
		this.treasureRequired = treasureRequired;
	}

	@Override
	public boolean isSatisfied()
	{
		return player.getTreasureCount() == treasureRequired;
	}

	@Override
	public String toString()
	{
		return "Collect all the treasure";
	}

}
