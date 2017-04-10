package banking.primitive.core;

public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	private int numWithdraws = 0;
	//bug #5 fix: created variables for often used literals
	private static final float MIN_AMT = 0.0f;
	private static final float DEPOSIT_FEE = 0.50f;
	private static final float WITHDRAW_FEE = 1.0f;

	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > MIN_AMT) {
			balance = balance + amount - DEPOSIT_FEE; //bug fix #5
			if (balance >= MIN_AMT) {  //bug fix #5
				setState(State.OPEN);
			}
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > MIN_AMT) {   //bug fix #5
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > 3)
				balance = balance - WITHDRAW_FEE;  //bug fix #5
			// KG BVA: should be < 0
			if (balance <= MIN_AMT) {       //bug fix #5
				setState(State.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	public String getType() { return "Checking"; }

	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
