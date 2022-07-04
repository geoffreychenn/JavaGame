package unsw.dungeon;

public class AndGoal extends CompositeGoal
{
	public AndGoal() 
	{
		super();
	}

	@Override
	public boolean isSatisfied()
	{
		for (Goal g : getSubGoals())
			if (!g.isSatisfied()) return false;
		return true;
	}

	@Override
	public String toString()
	{
		String text = "";
		int size = getSubGoals().size();
		for (int i = 0; i < size; i++) 
		{
			text += getSubGoals().get(i).toString();
			if (i != size - 1) text += " AND";
			text += "\n";
		}
		return text;
	}
}
