package ATM_Project;

public class TransactionValidator {
	
	public boolean isValidAmount(double amount) {
	    return (amount >= 0) && ((int)amount % 100 == 0);
	}


	public boolean hasSufficientBalance(double balance, double amount) {
	    return balance >= amount;
	}

}
