package unsw.dungeon;

public class EnemyGoal implements Goal
{
	private Player player;
	private int killsRequired;

	public EnemyGoal(Player player, int killsRequired)
	{
		super();
		this.player = player;
		this.killsRequired = killsRequired;
	}

	@Override
	public boolean isSatisfied()
	{
		return player.getKillCount() == killsRequired;
	}

	@Override
	public String toString()
	{
		return "Kill all the enemies";
	}

}
