package main;
import java.util.Random;

public class Goblin {
	
	public int goblin_max_health = 0;
	public int goblin_health = 0;
	public int goblin_attack = 0;
	public int goblin_defense = 0;
	
	public Goblin() {
		
		Random random = new Random();
		goblin_max_health = random.nextInt(10 - 5) + 5;
		goblin_health = goblin_max_health;
		goblin_attack = random.nextInt(3 - 2) + 2;
		goblin_defense = random.nextInt(2 - 1) + 1;
		
	}
}
