import java.util.ArrayList;

public class TheMotivator {
	private ArrayList<Integer> scores;
	TheMotivator() {
		scores = new ArrayList<Integer>();
	}
	public void createScores() {
		scores.add(100);
		scores.add(60);
		scores.add(95);
		scores.add(42);
	}
	public void feedback() {
		for(int score : scores) {
			if (score == 100)
				System.out.println("You're awesome");
			else if (score > 90)
				System.out.println("That's great");
			else if (score > 60)
				System.out.println("That's good ");
			else
				System.out.println("Well, what can I say?");
		}
			
	}
	public static void main(String[] args) {
		TheMotivator tm = new TheMotivator();
		tm.createScores();
		tm.feedback();
	}
}