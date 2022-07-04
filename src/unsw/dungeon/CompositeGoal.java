package unsw.dungeon;

import java.util.ArrayList;

public abstract class CompositeGoal implements Goal
{
	private ArrayList<Goal> subGoals;
	
	public CompositeGoal()
	{
		subGoals = new ArrayList<Goal>();
	}

	public ArrayList<Goal> getSubGoals()
	{
		return subGoals;
	}

	public void setSubGoals(ArrayList<Goal> subGoals)
	{
		this.subGoals = subGoals;
	}
	
	public void addGoal(Goal goal)
	{
		subGoals.add(goal);
	}
}
