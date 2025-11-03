package ATM_Project;

import java.io.*; 
import java.util.*;

public class Admin {

	public static int count = 0, retry = 3;
	Scanner sc = new Scanner(System.in);
	private int AdminPin = 1234;
	static File dir = new File("Accounts");
	File f;

	public int size;
	
	public static Account[] accounts;
	public static File[] accountfiles;

	public Admin(int size) {
		this.size = size;
		if(!dir.exists())
		{
			dir.mkdir();
		}
		accounts = new Account[(dir.listFiles().length) + this.size];
		accountfiles = new File[(dir.listFiles().length) + (this.size)];
	}

	public int getAdminPin() {
		return AdminPin;
	}

	public static void ExistingAccountInformation() throws IOException {
		
		//accountfiles=dir.listFiles();
		accountfiles=dir.listFiles();
		if(dir.listFiles().length > count)
		{
			for(int i=0;i<dir.listFiles().length;i++)
			{
				Account acc = new Account();
				try(BufferedReader br = new BufferedReader(new FileReader(accountfiles[i])))
				{
					// name into accounts
					String line;

					while ((line = br.readLine()) != null) {
						if (line.startsWith("Name:")) {
							//System.out.println("username.len=" + line.length());
							acc.setUsername(line.substring(5).trim());
						}
						else if(line.startsWith("Location:"))
						{
							//System.out.println("Location.len=" + line.length());
							acc.setLocation(line.substring(9).trim());
						}
						else if(line.startsWith("AccountNumber"))
						{
							//System.out.println("Accno.len=" + line.length());
							acc.setAccNo(line.substring(14));
						}
						else if (line.startsWith("Pin:")) {
						    String pinStr = line.substring(4).trim();

						    // ✅ Keep only digits, remove colors/spaces/other chars
						    pinStr = pinStr.replaceAll("[^0-9]", "");  

						    // ✅ Directly set pin (it will always be just digits now)
						   // System.out.println("pin="+Integer.parseInt(pinStr));
						    acc.setPin(Integer.parseInt(pinStr));
						}

						else if(line.startsWith("Initial Balance:"))
						{
							//System.out.println("pin.len=" + line.length());
							acc.setInitialBalance(Double.parseDouble(line.substring(16)));
						}
					}
					accounts[count]=acc;
					count++;
					
				}catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Existing file function handled");
				}	
			}	
		}
	}

	public void createAccount() throws IOException {
		
		if(accounts.length > count)
		{
			try
			{	
				Account acc1= new Account();
				System.out.println("Enter Name:");
				String username = sc.next();
				acc1.setUsername(username);
				
				System.out.println("Enter Location:");
				String loc = sc.next();
				acc1.setLocation(loc);
				
				System.out.println("Enter Initial Balance:");
				Double ib = sc.nextDouble();
				acc1.setInitialBalance(ib);
				
				System.out.println("Enter Pin:");
				int pin = sc.nextInt();
				acc1.setPin(pin);
				
				String AccNo = random_Acc();
				acc1.setAccNo(AccNo);
				
				accounts[count]=acc1;
				
				File f = new File(dir,AccNo+".txt");
				if(!f.exists())
				{
					f.createNewFile();
				}
				FileWriter fw = new FileWriter(f);
				fw.write(acc1.toString());
				fw.close();
				
				accountfiles=dir.listFiles();
				System.out.println(ConsoleColors.GREEN+"=========Your Account is Successfully Created======="+ConsoleColors.RESET);
				System.out.println("=====Your Account Details=======");
				System.out.println(acc1.tostring());
				count++;
				
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Create Account function Handled");
			}
			
		}else
		{
			System.out.println(ConsoleColors.RED+"=========Bank is full of Users======="+ConsoleColors.RESET);
		}
		
			
	}

	public void setAdminPin(int adminPin) {
		if (String.valueOf(adminPin).length() == 4)
			AdminPin = adminPin;
		else
			System.out.println(ConsoleColors.RED + "Admin pin must be the size of 4" + ConsoleColors.RESET);
	}

	public void viewAllAccounts() throws IOException {
		
		ExistingAccountInformation();
		if(count > 0)
		{
			for(int i=0;i<count;i++)
			{
				System.out.println(ConsoleColors.YELLOW+"<--------- Account "+(i+1)+"-------------> "+ConsoleColors.RESET);
				System.out.println(accounts[i]);
			}
			
		}else
		{
			System.out.println(ConsoleColors.RED+"!!!No Accounts to Display(vallac) !!!"+ConsoleColors.RESET);
		}
	}

	public static void filesList() {
		accountfiles = dir.listFiles();
	}

	public static int no_of_Files() {
		return dir.listFiles().length;
	}

	public void deleteAccount(String AccountNumber) throws IOException {
	    ExistingAccountInformation();

	    if (count > 0) {
	        boolean nothere = true;

	        for (int i = 0; i < count; i++) {
	            if (accounts[i].getAccNo().equals(AccountNumber)) {  // ✅ compare account number
	                for (int j = i; j < count - 1; j++) {
	                    accounts[j] = accounts[j + 1];
	                }
	                count--;
	                nothere = false;

	                // Also delete the account file
	                DeleteFile(AccountNumber);
	                System.out.println(ConsoleColors.GREEN+"****** Account successfully Deleted *****"+ConsoleColors.RESET);
	                break;
	            }
	        }

	        if (nothere) {
	            System.out.println(ConsoleColors.RED + "!! There is no such type of Account !!" + ConsoleColors.RESET);
	        }
	    } else {
	        System.out.println(ConsoleColors.RED + "!!! No Accounts to Delete !!!" + ConsoleColors.RESET);
	    }
	}


	
	public void DeleteFile(String AccountNumber)
	{
		accountfiles=dir.listFiles();
		boolean nothere = true;
		for(int i=0;i<dir.listFiles().length;i++)
		{
			if(accountfiles[i].getName().equals(AccountNumber+".txt"))
			{
				accountfiles[i].delete();
				nothere = false;
			}
		}
		if(nothere)
		{
			System.out.println(ConsoleColors.RED+"!!!No Such type of Account File!!!"+ConsoleColors.RESET);
		}
	}
	
	static public String random_Acc() {
		Random r = new Random();
		int len = 8 + r.nextInt(5); // 8 to 12 digits

		String acc1 = new String("");

		for (int i = 0; i < len; i++) {
			acc1 = acc1.concat(String.valueOf(r.nextInt(9) + 1)); //
		}

		return acc1;
	}

	public boolean retry_pin(int pin) {
		boolean a = false;
		if (pin == AdminPin) {
			a = true;
		} else
			retry--;
		return a;
	}

}
