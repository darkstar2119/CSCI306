package price;

public class Cashier {
	public Envelope makeChange(Envelope e, int dollars, int cents) {
		Envelope newE = new Envelope(0,0,0,0,0);
		return newE;
		//just adding a comment
		/*int remCents = cents;
		int q = remCents % 25;
		remCents = remCents - (q*25);
		int d = remCents % 10;
		remCents = remCents - (d*10);
		int n = remCents % 5;
		remCents = remCents - (n*5);
		int p = remCents;
		
		int remDollars = e.getDollars() - dollars;
		int remQ = e.getQuarters() - q;
		int remD = e.getDimes() - d; 
		int remN = e.getNickels() - n;
		int remP = e.getPennies() - p;
		
		int eCents = (e.getQuarters()*25)+(e.getDimes()*10)+(e.getNickels()*5)+(e.getPennies());
		
		int chngDollars = dollars - e.getDollars();
		int chngCents = cents - eCents;
		
		if(chngDollars <= 0) {
			if (chngDollars < 0) {
				throw new NegativeBalanceException(chngDollars, chngCents);
			}
			
			if(chngCents < 0) {
				throw new NegativeBalanceException(chngDollars, chngCents);
			}
		}*/
		
		
	}
}
