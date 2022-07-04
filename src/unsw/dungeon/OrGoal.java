package unsw.dungeon;

public class OrGoal extends CompositeGoal
{
	public OrGoal()
	{
		super();
	}

	@Override
	public boolean isSatisfied()
	{
		for (Goal g : getSubGoals())
			if (g.isSatisfied()) return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		String text = "(";
		int size = getSubGoals().size();
		for (int i = 0; i < size; i++) 
		{
			text += getSubGoals().get(i).toString();
			if (i != size - 1) text += " OR";
			else text += ")";
			text += "\n";
		}
		return text;
	}
}
