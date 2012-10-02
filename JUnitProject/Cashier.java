package price;

public class Cashier {
	public Envelope makeChange(Envelope e, int dollars, int cents) {
		
		int cmpChange = (dollars*100) + cents;
		
		
		/*int remCents = cents;
		int q = remCents / 25;
		remCents = remCents - (q*25);
		int d = remCents / 10;
		remCents = remCents - (d*10);
		int n = remCents / 5;
		remCents = remCents - (n*5);
		int p = remCents;*/
		
		int chng = e.getTotalCents() - cmpChange;
	
		
		String error = "Negative balance";
		if (cmpChange < 0) {
			try {
				throw new NegativeBalanceException(error);
			} catch (NegativeBalanceException e1) {
			}
		}
			
		return e;
		
		
	}
}
