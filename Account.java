package ATM_Project;

import java.util.*;

public class Account {

	Scanner sc = new Scanner(System.in);
	TransactionValidator tv = new TransactionValidator();

	private String Username, Location, AccNo;
	private int pin;
	private double InitialBalance;

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		
		Username = username;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getAccNo() {
		return AccNo;
	}

	public void setAccNo(String accNo) {
		boolean exists = false;
		for (int i = 0; i < Admin.count; i++) {
			if (Admin.accounts[i] != null && Admin.accounts[i].getAccNo() != null
					&& Admin.accounts[i].getAccNo().equals(accNo)) {
				exists = true;
				break;
			}
		}

		if (exists) {
			setAccNo(Admin.random_Acc());

		} else {
			AccNo=accNo;
		}
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		if (String.valueOf(pin).length() == 4) {
			this.pin = pin;
		} else {
			System.out.println(ConsoleColors.RED + "Pin size must be 4" + ConsoleColors.RESET);
			System.out.println("Enter pin again");
			int pin1 = sc.nextInt();
			setPin(pin1);
		}

	}

	public double getInitialBalance() {
		return InitialBalance;
	}

	public void setInitialBalance(double initialBalance) {

		if (tv.isValidAmount(initialBalance)) {
			InitialBalance = initialBalance;
		} else {
			System.out.println(ConsoleColors.RED + "Balance must be mutliple of 100 enter again and greater than 0" + ConsoleColors.RESET);
			double am = sc.nextDouble();
			setInitialBalance(am);
		}

	}
	
	
	

	public String tostring() {
		return "Name:"+ConsoleColors.BLUE+ getUsername() +ConsoleColors.RESET+ "\nLocation:" +ConsoleColors.BLUE+ getLocation() +ConsoleColors.RESET+ "\nAccountNumber:" +ConsoleColors.BLUE+ getAccNo() +ConsoleColors.RESET+ "\nPin:"+ConsoleColors.BLUE+ getPin() +ConsoleColors.RESET+ "\nInitial Balance:" +ConsoleColors.BLUE+ getInitialBalance() +ConsoleColors.RESET+ "\n";
	}
	
	public String toString()
	{
		return "Name:"+getUsername()+ "\nLocation:"+getLocation() +"\nAccountNumber:" + getAccNo() + "\nPin:"+ getPin() + "\nInitial Balance:" + getInitialBalance() +"\n";
		
	}

}
