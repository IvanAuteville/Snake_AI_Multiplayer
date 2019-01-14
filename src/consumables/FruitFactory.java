package consumables;

import java.util.Random;

public class FruitFactory
{
    public Consumable makeFruit()
    {
	Random random = new Random();
	int probability = random.nextInt(100);

	if (probability >= 0 && probability <= 60)
	{
	    return new Apple();
	} else if (probability > 60 && probability <= 90)
	{
	    return new Banana();
	} else
	{
	    return new Cherry();
	}
    }
}