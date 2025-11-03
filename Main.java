package ATM_Project;


import java.io.IOException;
import java.util.*;

public class Main {

	public int count = 0;
	public static int acI;
	public static int acN;
	private static Scanner sc;
	
	
	public static void main(String[] args) throws IOException{
	   sc = new Scanner(System.in);
		System.out.println("Enter Your Bank size:");
		int size = sc.nextInt();
		Admin ad = new Admin(size);
		ATM am = new ATM();
		
		while (true) {
			System.out.println(ConsoleColors.GREEN + "******** options ********" + ConsoleColors.RESET);
			System.out.println("1.Admin Users\n2.Existing and New Accounts\n3.Exit\nChoose your Choice");
			String ch1 = sc.next();
			switch (ch1) {
			case "1": {

				System.out.println("Enter pin :");
				int pin = sc.nextInt();

				if (Admin.retry != 0) {
					if (ad.retry_pin(pin)) {

						boolean running = true;
						while (running) {
							System.out.println(ConsoleColors.GREEN + "******** options ********" + ConsoleColors.RESET);
							System.out.println("1.View All Accounts\n2.Delete Account\n3.Exit\nEnter your Option");
							String choice = sc.next();

							switch (choice) {
							case "1": {

								ad.viewAllAccounts();
								break;
							}
							case "2": {
								if (Admin.count > 0) {
									System.out.println("Enter Account Number:");
									String Acn = sc.next();
									ad.deleteAccount(Acn);
									break;
								} else {
									System.out.println(
											ConsoleColors.RED + "No Accounts to delete" + ConsoleColors.RESET);
									break;
								}
							}

							case "3": {

								running = false;
								break;
							}
							default:
								System.out.println(ConsoleColors.YELLOW + "Enter valid option" + ConsoleColors.RESET);
								break;

							}
						}

					} else {
						System.out.println(
								ConsoleColors.RED + " !!!! Pin is Incorrect try again !!!!" + ConsoleColors.RESET);
						System.out.println(ConsoleColors.YELLOW + "Chances left=" + Admin.retry + ConsoleColors.RESET);

					}
				} else {
					System.out.println(
							ConsoleColors.RED + "Sorry...! Your retry's are over(Maximum=3)" + ConsoleColors.RESET);
					System.out.println(ConsoleColors.RED + "Please run again and gain your losted 3 chances"
							+ ConsoleColors.RESET);
					System.exit(000);
					break;
				}

				break;
			}

			case "2": {
				boolean running = true;
				while (running) {
					System.out.println(ConsoleColors.GREEN + "******** options ********" + ConsoleColors.RESET);
					System.out.println("1.New User\n2.Existing User\n3.exit\nChoose option");
					String choice = sc.next();
					switch (choice) {
					case "1": {
						
						try {
					
							if(Admin.dir.listFiles().length == 0)
							{
								ad.createAccount();
							}
							else
							{
								ad.ExistingAccountInformation();
								ad.createAccount();
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							//System.out.println("Main switch case create handled");
						}
						break;
					}

					case "2": {

						
						if (Admin.dir.listFiles().length > 0) {
							System.out.println("Enter Account Number");
							String acn = sc.next();
							ad.ExistingAccountInformation();
							boolean Accountfound=false,pincorrect=false;
							for (int i = 0; i < Admin.count; i++) {
								//System.out.println("admin.acc="+Admin.accounts[i].getAccNo()+" acn="+acn);
								if (Admin.accounts[i].getAccNo().equals(acn)) {
									Accountfound=true;
									System.out.println("Enter Pin:");
									int pin = sc.nextInt();
									if (Admin.accounts[i].getPin()==pin) {
										pincorrect=true;
										acI = i;

										boolean run = true;
										while (run) {
											System.out.println(ConsoleColors.GREEN + "******** options ********"
													+ ConsoleColors.RESET);
											System.out.println(
													"1.Withdraw\n2.Deposit\n3.Balance Inquiry\n4.Exit\nChoose your Option");
											String choice1 = sc.next();

											switch (choice1) {
											case "1": {

												System.out.println("Enter amount to Withdraw");
												double amount = sc.nextDouble();
												try {
													am.withdraw(amount);
												} catch (InvalidException e) {
													e.printStackTrace();
												}
												
												break;
											}

											case "2": {
												System.out.println("Enter amount to Deposit");
												double amount = sc.nextDouble();
												try {
													am.deposit(amount);
												} catch (Exception e) {
													e.printStackTrace();
												}
												break;
											}
											case "3": {

												int an2 = (acn.length() / 2);
												String afn = acn.substring(0, an2);
												String afnr = afn.replaceAll(".", "x");
												String an22 = acn.substring(an2, acn.length());
												System.out.println("Your AccNo:" + ConsoleColors.BLUE + afnr + an22
														+ ConsoleColors.RESET);
												System.out.println("Your balance:" +ConsoleColors.BLUE +am.checkBalance()+ConsoleColors.RESET);
												break;

											}
											case "4": {
												run = false;
												break;
											}
											default:
												System.out.println(
														ConsoleColors.YELLOW + "Invalid option" + ConsoleColors.RESET);
												break;
											}
										}

									} 
										 

								}

								} 
							if(!Accountfound)
							{
								System.out.println(ConsoleColors.RED+"!! Account Not found !!!"+ConsoleColors.RESET);
							}
							
							if(!pincorrect)
							{
								System.out.println(ConsoleColors.RED+"!! Pin is incorrect !!!"+ConsoleColors.RESET);
							}

						} else {
							System.out.println(ConsoleColors.RED + "!!! No Accounts to Display !!!" + ConsoleColors.RESET);
						}

						break;

					}

					case "3": {
						running = false;
						break;
					}
					default:
						System.out.println(ConsoleColors.YELLOW + "Invalid option" + ConsoleColors.RESET);
						break;
					}
				}

				break;

			}
			case "3":
				System.out.println(
						ConsoleColors.GREEN + "*********  Thank you visit Again *************" + ConsoleColors.RESET);
				System.exit(00);
				break;
			default:
				System.out.println(ConsoleColors.YELLOW + "Enter a valid option" + ConsoleColors.RESET);
				break;
			}
		}
		
	}

}
