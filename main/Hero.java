package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hero {
	
	public int hero_lvl = 1;
	public int hero_max_health = 0;
	public int hero_health = 0;
	public int hero_attack = 0;
	public int hero_defense = 0;
	public List<Integer> potions = new ArrayList<Integer>();
	public int gold = 0;
	
	public Hero() {
		
		Random random = new Random();
		
		hero_max_health = random.nextInt(30 - 20) + 20;
		hero_health = hero_max_health;
		hero_attack = random.nextInt(5 - 3) + 3;
		hero_defense = random.nextInt(5 - 1) + 1;
		
		
	}
}
