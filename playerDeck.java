package main;
import java.util.*;

public class playerDeck {
	
	
	public static void playCards(List<Integer> deck, List<Integer> d) {

		System.out.println("Current deck size: " + deck.size());
		
		Collections.shuffle(deck);
		
		System.out.println("How many cards would you like to draw?");
		Scanner s = new Scanner(System.in);
		int hand = s.nextInt();
		while (hand > deck.size()) {
			System.out.println("You can't draw more than the deck! Please choose another number to draw");
			hand = s.nextInt();
		}
		
		List<Integer> playedCards = new ArrayList<Integer>();
		
		for (int i = 0; i < hand; i++) {
			playedCards.add(deck.get(i));
		}
		
		System.out.println("The cards you drew are: " + playedCards);
		
		d.addAll(playedCards);
		
		deck.removeAll(playedCards);
		
		while(deck.size() > 0) {

			System.out.println(d);
			playCards(deck, d);
		}
		
		deck.addAll(d);
		d.clear();
		
		System.out.println("Out of Cards! Would you like to play again? yes or no");
		s.nextLine();
		String ans = s.nextLine();
		
		if (ans != "no" || ans != "No" || ans != "NO") {
			
			playCards(deck, d);
		} else {
			s.close();
			return;
		}
		
	}
	
	public static void main(String[] args) {
		
		List<Integer> deck = new ArrayList<Integer>();
		List<Integer> discard = new ArrayList<Integer>();
		
		for(int i = 1; i < 53; i++) {
			deck.add(i);
		}
		
		
		System.out.println("Let's play a game!");
		playCards(deck, discard);
		
		
	}
}
