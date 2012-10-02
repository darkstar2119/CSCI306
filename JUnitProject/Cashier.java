package price;

public class Cashier {
	public Envelope makeChange(Envelope e, int dollars, int cents) {
		
		int cmpChange = (dollars*100) + cents;
		int chng = e.getTotalCents() - cmpChange;
		
		String error = "Negative balance.";
		if (chng < 0) {
			try {
				throw new NegativeBalanceException(error);
			} catch (NegativeBalanceException nbe) {
				System.out.println(error);
			}
		}
			
		int remChng = chng;
		int dollas = remChng / 100;
		remChng = remChng - (dollas*100);
		int q = remChng / 25;
		remChng = remChng - (q*25);
		int d = remChng / 10;
		remChng = remChng - (d*10);
		int n = remChng / 5;
		remChng = remChng - (n*5);
		int p = remChng;
		
		Envelope chngEnvelope = new Envelope(dollas,q,d,n,p);
		return chngEnvelope;
		
	}
}
