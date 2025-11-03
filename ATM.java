package ATM_Project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ATM {

	TransactionValidator tv = new TransactionValidator();
	
	static FileReader fr;
	
	public boolean authenticateUser(String accountNumber, int pin) {
		boolean a = false;
		for (int i = 0; i < (Admin.count+Admin.accounts.length); i++) {

			if ((Admin.accounts[i].getAccNo().equals(accountNumber)) && (Admin.accounts[i].getPin()==pin)) {
				a = true;
				break;
			}

		}
		return a;

	}
	

	public void withdraw(double amount) throws InvalidException, IOException{
		if(tv.isValidAmount(amount))
		{
			
			if(tv.hasSufficientBalance(Admin.accounts[Main.acI].getInitialBalance(), amount))
			{
				double bal = Admin.accounts[Main.acI].getInitialBalance()-amount;
				Admin.accounts[Main.acI].setInitialBalance(bal);
				
				//Initial Balance into Accounts Updation
				
				Admin.filesList();
				/*
				 * System.out.println("accfiles="+Admin.accountfiles.length);
				 * System.out.println("accs="+Admin.accounts.length);
				 */
				for(int i=0;i<Admin.no_of_Files();i++)
				{
					if((Admin.accounts[Main.acI].getAccNo()+".txt").equals(Admin.accountfiles[i].getName())){
						
						StringBuilder sb = new StringBuilder();
						String balLine;
						
						BufferedReader br = new BufferedReader(new FileReader(Admin.accountfiles[i])); 
						while((balLine=br.readLine())!=null) {
							if(balLine.startsWith("Initial Balance:"))
							{	
								balLine="Initial Balance:"+bal;
							}
							sb.append(balLine).append("\n");
						}
						br.close();
						
						
						
						
						
						
						BufferedWriter bw = new BufferedWriter(new FileWriter(Admin.accountfiles[i]));
						 bw.write(sb.toString());
						 bw.close();
					}
				}
				
				System.out.println(ConsoleColors.GREEN+"*************** Withdrawal Successfully *************"+ConsoleColors.RESET);
				

				
			}
			else {
				throw new InvalidException(ConsoleColors.BLUE+"Insufficient Funds"+ConsoleColors.RESET);
			}
		}
		else
			throw new InvalidException(ConsoleColors.BLUE+"Amount should  be greater than 0 and also multiple of 100"+ConsoleColors.RESET);
	}
	
	public void deposit(double amount) throws InvalidException, IOException{
		// Validate deposit conditions

		if (tv.isValidAmount(amount)) {
			double final_ = Admin.accounts[Main.acI].getInitialBalance() + amount;
			Admin.accounts[Main.acI].setInitialBalance(final_);
			
			//Initial Balance into Accounts Updation
			
			Admin.filesList();
			for(int i=0;i<Admin.no_of_Files();i++)
			{
				if((Admin.accounts[Main.acI].getAccNo()+".txt").equals(Admin.accountfiles[i].getName())){
					
					StringBuilder sb = new StringBuilder();
					String balLine;
					
					BufferedReader br = new BufferedReader(new FileReader(Admin.accountfiles[i])); 
					while((balLine=br.readLine())!=null) {
						if(balLine.startsWith("Initial Balance:"))
						{	
							balLine="Initial Balance:"+final_;
						}
						sb.append(balLine).append("\n");
					}
					br.close();
					
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(Admin.accountfiles[i]));
					 bw.write(sb.toString());
					 bw.close();
				}
			}
			
			System.out.println(ConsoleColors.GREEN+"********* Deposited Successfully ************"+ConsoleColors.RESET);
		} 
		else
			throw new InvalidException(ConsoleColors.BLUE+"Amount should not be less than or Equal to 0"+ConsoleColors.RESET);
	}

	public double checkBalance() {
		
		return (Admin.accounts[Main.acI].getInitialBalance());
	}

}
