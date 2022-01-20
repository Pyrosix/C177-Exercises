package main;
import java.util.Scanner;
import java.util.Random;

public class GoblinTower {
	
	public static int clamp(int val, int min, int max) {
		return Math.max(min,  Math.min(max,  val));
	}
	
	public static void combat(Hero h, Goblin g) {
		
		
			int clampedDamage = clamp(h.hero_attack - g.goblin_defense, 0, g.goblin_health);
			g.goblin_health = g.goblin_health - clampedDamage;
			System.out.println("You do " + clampedDamage + " damage to the goblin!");
			
			clampedDamage = clamp(g.goblin_attack - h.hero_defense, 0, h.hero_health);
			h.hero_health = h.hero_health - clampedDamage;
			System.out.println("You take " + clampedDamage + " damage from the goblin!");
			
			if (g.goblin_health <= 0) {
				System.out.println("The goblin has been slain! You gain 2 gold!\n");
				h.gold += 2;
			}
		
	}
	
	public static void climb() {
		
		boolean playing = true;
		
		while (playing) {
			
			Hero hero = new Hero();
			
			for (int i = 0; i < 5; i++) {
				hero.potions.add(2);
			}
			
			Scanner s = new Scanner(System.in);
			Random rand = new Random();
			
			System.out.println("\nWelcome to the Goblin Tower!");
			System.out.println("\n--------------------------------------------------------");
			
			
			
			while(hero.hero_health > 0) {
				
				int room_type = rand.nextInt(3 - 1) + 1;
				int step_counter = 0;
				
				step_counter++;
				
				if (step_counter % 10 == 0) {
					System.out.println("You've leveled up!");
					hero.hero_lvl++;
					System.out.println("You're now level " + hero.hero_lvl);
					hero.hero_attack += 2;
					hero.hero_defense++;
					hero.hero_max_health += 5;
					hero.hero_health = hero.hero_max_health;
					
					System.out.println("\nWould you like to buy a potion?");
					System.out.println("1: Yes");
					System.out.println("2: No");
					
					int inp = s.nextInt();
					while(inp != 1 || inp != 2) {
						System.out.println("Please choose a valid input");
						inp = s.nextInt();
					}
					
					if (inp == 1) {
						if (hero.gold < 4) {
							System.out.println("You don't have enough gold!");
						} else {
							System.out.println("You bought a potion!");
							hero.potions.add(2);
						}
					} else {
						
					}
				}
				
				switch (room_type) {
				
				
				case 1:
					
					System.out.println("An empty room....");
					System.out.println("Press Enter to continue");
					String input = s.nextLine();
					break;
			
				case 2:
					
					System.out.println("\nA goblin emerges from the shadows!");
					
					Goblin gob = new Goblin();
					
					while (gob.goblin_health > 0 || hero.hero_health > 0) {
						
						if (gob.goblin_health == 0) {
							System.out.println("Press Enter to continue");
							s.nextLine();
							input = s.nextLine();
							break;
						}
						
						System.out.println("\nYour HP: " + hero.hero_health);
						System.out.println("Goblin's HP: " + gob.goblin_health);
						System.out.println("\nChoose an action: ");
						System.out.println("1: Fight");
						System.out.println("2: Potion");
						System.out.println("3: Run");
						System.out.println();
						
						int choice = s.nextInt();
						
						while (choice > 3 || choice <= 0) {
							System.out.println("Please choose a valid input");
							choice = s.nextInt();
						}
						
						switch (choice) {
						case 1: 
							combat(hero, gob);
							break;
						case 2:
							if (hero.potions.size() <= 0) {
								System.out.println("You've used all your potions!");
								break;
							} else {
								System.out.println("You used a potion");
								hero.potions.remove(0);
								hero.hero_health = hero.hero_max_health;
								break;
							}
						case 3:
							System.out.println("You run away and live to fight another day...");
							System.out.println("Would you like to try again?");
							s.nextLine();
							String decision = s.nextLine();
							
							while (!"yes".equals(decision) && !"no".equals(decision)) {
								System.out.println("Please choose a valid input");
								System.out.println(decision);
								decision = s.next();
							}
							
							if ("yes".equals(decision)) {
								hero.hero_lvl = 1;
								hero.hero_max_health = rand.nextInt(30 - 20) + 20;
								hero.hero_health = hero.hero_max_health;
								hero.hero_attack = rand.nextInt(5 - 3) + 3;
								hero.hero_defense = rand.nextInt(5 - 1) + 1;
								System.out.println("\n--------------------------------------------------------");
								climb();
							} else {
								playing = false;
								s.close();
								return;
							}
						}	
					}	
					
				}	
			}
			
			System.out.println("You have died.....");
			System.out.println("Would you like to try again?");
			String decision = s.next();
			
			while (!"yes".equals(decision) && !"no".equals(decision)) {
				System.out.println("Please choose a valid input");
				System.out.println(decision);
				decision = s.next();
			}
			
			if ("yes".equals(decision)) {
				hero.hero_lvl = 1;
				hero.hero_max_health = rand.nextInt(30 - 20) + 20;
				hero.hero_health = hero.hero_max_health;
				hero.hero_attack = rand.nextInt(3 - 1) + 1;
				hero.hero_defense = rand.nextInt(5 - 1) + 1;
				System.out.println("\n--------------------------------------------------------");
				climb();
			} 
			
			playing = false;
			s.close();
		}
	}
	
	public static void main(String[] args) {
		
		climb();
		
	}
}
