package unsw.dungeon;

import java.util.ArrayList;

public class BoulderGoal implements Goal
{
	private ArrayList<Switch> switches;
	
	public BoulderGoal(ArrayList<Switch> switches)
	{
		super();
		this.switches = switches;
	}

	@Override
	public boolean isSatisfied()
	{
		for (Switch s : switches)
			if (!s.isActive()) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Place all boulders on switches";
	}
	
}
