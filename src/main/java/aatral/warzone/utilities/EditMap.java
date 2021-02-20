package aatral.warzone.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;

public class EditMap {
	private Scanner ob=new Scanner(System.in);
	private ValidateMap validateOb = new ValidateMap();
	public EditMap() {
			boolean flag = true;
			while (flag) {
				System.out.println("\nChoose which one to edit \n1.Continent\n2.Country\n3.Neighbour\n4.None");
				int editOption = ob.nextInt();
				switch (editOption) {
				case 1:
					System.out.println("Edit continent");
					editContinentMap();
					flag = false;
					break;
				case 2:
					System.out.println("Edit Country");
					editCountryMap();
					flag = false;
					break;
				case 3:
					System.out.println("Edit Neighbour");
					editNeighbourMap();
					flag = false;
					break;
				case 4:
					System.out.println("None is selected");
					flag = false;
					break;
				default:
					System.out.println("Invalid option entered.Try again!!");
					break;
				}
			}
	}
	public void editNeighbourMap() {
		System.out.println("\n1.Add country ID and its neighbour ID's \n2.Remove country ID and neighbour ID's");
		int switchInput = ob.nextInt();
		switch(switchInput){
			case 1:
				addNeighbours();
				break;
			case 2:
				removeNeighbours();
				break;
			default:
				break;
		}
	}
	public void editContinentMap() {
			System.out.println("\n1.Add continent ID and Value \n2.Remove Continent ID");
			int switchInput = ob.nextInt();
			switch(switchInput){
				case 1:
					addContinent();
					break;
				case 2:
					removeContinent();
					break;
				default:
					break;
		}
	}
	public void editCountryMap() {
			System.out.println("\n1.Add country ID with its continent ID \n2.Remove country ID");
			int switchInput = ob.nextInt();
			switch(switchInput){
				case 1:
					addCountry();
					break;
				case 2:
					removeCountry();
					break;
				default:
						
			}
	}
	public void writeCountryFile(List<Country> updateCountry) {
		String FILE_NAME = "src/main/resources/canada-countries.txt";
		StreamFactory factory = StreamFactory.newInstance();
		StreamBuilder builderCSV = new StreamBuilder("countryWrite")
				.format("delimited")
				.parser(new DelimitedParserBuilder(' '))
				.addRecord(Country.class);
		factory.define(builderCSV);

		File deleteFile = new File(FILE_NAME); 
		deleteFile.delete();

		BeanWriter out = factory.createWriter("countryWrite", new File("src/main/resources/canada-countries.txt"));

		for (Country country : updateCountry) {
			out.write(country);
		} 
		out.flush();
		out.close();
	}
	public void writeContinentFile(List<Continent> updateContinent) {
		String FILE_NAME = "src/main/resources/canada-continents.txt";
		StreamFactory factory = StreamFactory.newInstance();
		StreamBuilder builderCSV = new StreamBuilder("continentWrite")
				.format("delimited")
				.parser(new DelimitedParserBuilder(' '))
				.addRecord(Continent.class);
		factory.define(builderCSV);

		File deleteFile = new File(FILE_NAME); 
		deleteFile.delete();

		BeanWriter out = factory.createWriter("continentWrite", new File("src/main/resources/canada-continents.txt"));

		for (Continent continent : updateContinent) {
			out.write(continent);
		} 
		out.flush();
		out.close();
	}
	public void writeBordersFile(List<Borders> updateBorder) {
		String FILE_NAME = "src/main/resources/canada-borders.txt";
		StreamFactory factory = StreamFactory.newInstance();
		StreamBuilder builderCSV = new StreamBuilder("continentWrite")
				.format("delimited")
				.parser(new DelimitedParserBuilder(' '))
				.addRecord(Continent.class);
		factory.define(builderCSV);

		File deleteFile = new File(FILE_NAME); 
		deleteFile.delete();

		BeanWriter out = factory.createWriter("continentWrite", new File("src/main/resources/canada-borders.txt"));

		for (Borders borders : updateBorder) {
			out.write(borders);
		} 
		out.flush();
		out.close();
	}
	public boolean addNeighbours() {
		try {
			boolean addNeighboursFlag=true;
			while(addNeighboursFlag) {
				System.out.print("\nEnter the country ID");
				int countryID = ob.nextInt();
				if(!validateOb.validateCountryID(countryID)) {
					boolean flag=true;
//					ob.nextLine();
					while(flag)
					{
						System.out.print("\nEnter the neighbouring country ID");
						int neighbourCountryID = ob.nextInt();
						if(!validateOb.validateContinentID(neighbourCountryID)){
							List<Borders> bordersList = new CountryBorderReader().mapCountryBorderReader();
							int count=0;
							for(Borders borderObject : bordersList) {
								if(borderObject.getCountryId().equalsIgnoreCase(countryID+"")) {
									bordersList.get(bordersList.indexOf(borderObject)).getAdjacentCountries().add(neighbourCountryID+"");
									count++;
								}else if(borderObject.getCountryId().equalsIgnoreCase(neighbourCountryID+"")) {
									bordersList.get(bordersList.indexOf(borderObject)).getAdjacentCountries().add(countryID+"");
									count++;
								}
								if(count==2)
									break;
							}
							System.out.println("Border File Successfuly updated");
							return true;
						}else {
							boolean switchIPFlag = true;
							while(switchIPFlag) {
							System.out.println("The country ID is invalid...Try again..."
							+ "\nPress\n1. Enter new ID\n2. Return back");
							int switchIP = ob.nextInt();
							switch(switchIP)
							{
							case 1:
								switchIPFlag=false;
								continue;
							case 2:
								return false;
							default:
								System.out.println("Your selected option is not valid... Try again");
							}
							}
						}
					}
				}else {
					boolean switchIPFlag = true;
					while(switchIPFlag) {
						System.out.println("The Entered country ID is invaild, kindly enter a valid ID"
								+ "\nPress\n1. Enter new ID\n2. Return back");
						int switchIP = ob.nextInt();
						switch(switchIP)
						{
						case 1:
							switchIPFlag=false;
							continue;
						case 2:
//							switchIPFlag=false;
//							addContinentFlag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}ob.hasNextLine();
			}
		}catch(InputMismatchException e) {
			System.out.println("Ïnput must be an integer");
		}
		return false;
	}
	public boolean removeNeighbours() {
		try {
			boolean removeNeighboursFlag=true;
			while(removeNeighboursFlag) {
				System.out.print("\nEnter the country ID");
				int countryID = ob.nextInt();
				if(!validateOb.validateCountryID(countryID)) {
					boolean flag=true;
//					ob.nextLine();
					while(flag)
					{
						System.out.print("\nEnter the neighbouring country ID");
						int neighbourCountryID = ob.nextInt();
						if(!validateOb.validateContinentID(neighbourCountryID)){
							List<Borders> bordersList = new CountryBorderReader().mapCountryBorderReader();
							int count=0;
							for(Borders borderObject : bordersList) {
								if(borderObject.getCountryId().equalsIgnoreCase(countryID+"")) {
									bordersList.get(bordersList.indexOf(borderObject)).getAdjacentCountries().remove(neighbourCountryID+"");
									count++;
								}else if(borderObject.getCountryId().equalsIgnoreCase(neighbourCountryID+"")) {
									bordersList.get(bordersList.indexOf(borderObject)).getAdjacentCountries().remove(countryID+"");
									count++;
								}
								if(count==2)
									break;
							}
							System.out.println("Border File Successfuly updated");
							return true;
						}else {
							boolean switchIPFlag = true;
							while(switchIPFlag) {
							System.out.println("The country ID is invalid...Try again..."
							+ "\nPress\n1. Enter new ID\n2. Return back");
							int switchIP = ob.nextInt();
							switch(switchIP)
							{
							case 1:
								switchIPFlag=false;
								continue;
							case 2:
								return false;
							default:
								System.out.println("Your selected option is not valid... Try again");
							}
							}
						}
					}
				}else {
					boolean switchIPFlag = true;
					while(switchIPFlag) {
						System.out.println("The Entered country ID is invaild, kindly enter a valid ID"
								+ "\nPress\n1. Enter new ID\n2. Return back");
						int switchIP = ob.nextInt();
						switch(switchIP)
						{
						case 1:
							switchIPFlag=false;
							continue;
						case 2:
//							switchIPFlag=false;
//							addContinentFlag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}ob.hasNextLine();
			}
		}catch(InputMismatchException e) {
			System.out.println("Ïnput must be an integer");
		}
		return false;
	}
	public boolean addCountry() {
		try {
			boolean addCountryFlag=true;
			while(addCountryFlag) {
				System.out.print("\nEnter the country ID");
				int countryID = ob.nextInt();
				if(validateOb.validateCountryID(countryID)) {
					boolean flag=true;
					ob.nextLine();
					System.out.print("\nEnter the country Name");
					String countryName = ob.nextLine();
					while(flag)
					{
						System.out.print("\nEnter a continent ID");
						int continentID = ob.nextInt();
						if(!validateOb.validateContinentID(continentID)){
							List<Country> addCountryList = new CountryMapreader().readCountryMap();
							addCountryList.add(new Country(countryID+"", countryName, continentID+"", null, 0));
							writeCountryFile(addCountryList);
							System.out.println("Country File Successfuly updated");

							List<Borders> addBorderList = new CountryBorderReader().mapCountryBorderReader();
							addBorderList.add(new Borders(countryID+"", new ArrayList<>()));
							writeBordersFile(addBorderList);
							System.out.println("Border File Successfuly updated");
							return true;
						}else {
							boolean switchIPFlag = true;
							while(switchIPFlag) {
							System.out.println("The continent ID is invalid...Try again..."
							+ "\nPress\n1. Enter new ID\n2. Return back");
							int switchIP = ob.nextInt();
							switch(switchIP)
							{
							case 1:
								switchIPFlag=false;
								continue;
							case 2:
								return false;
							default:
								System.out.println("Your selected option is not valid... Try again");
							}
							}
						}
					}
				}else {
					boolean switchIPFlag = true;
					while(switchIPFlag) {
						System.out.println("The Entered country ID is invaild, kindly enter a new ID"
								+ "\nPress\n1. Enter new ID\n2. Return back");
						int switchIP = ob.nextInt();
						switch(switchIP)
						{
						case 1:
							switchIPFlag=false;
							continue;
						case 2:
//							switchIPFlag=false;
//							addContinentFlag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}ob.hasNextLine();
			}
		}catch(InputMismatchException e) {
			System.out.println("Ïnput must be an integer");
		}
		return false;
	}
	public boolean addContinent() {
		try {
			boolean addContinentFlag=true;
			while(addContinentFlag) {
				System.out.print("\nEnter the continent ID : ");
				int continentID = ob.nextInt();
				if(validateOb.validateContinentID(continentID)) {
					boolean flag=true;
					while(flag)
					{
						System.out.print("\nEnter the continent Name for the ID "+continentID+" : ");
						ob.nextLine();
						String continentName = ob.nextLine();
						if(!validateOb.validateContinentName(continentName)){
							boolean flag1=true;
							while(flag1) {
								System.out.print("\nThe continent name "+continentName+" is already present in different continentID"
										+ "\nPress 1.To proceed\n2.Change the continentName");
								int switchInput=ob.nextInt();
								switch(switchInput) {
								case 1:
									flag=false;
									flag1=false;
									break;
								case 2:
									flag1=false;
									break;
								default:
									System.out.println("Your selected option is not valid... Try again");
								}
							}
							if(flag)
								continue;
						}
						List<Continent> addContinentList = new ContinentMapReader().readContinentFile();
						addContinentList.add(new Continent(continentID+"",continentName));
						writeContinentFile(addContinentList);
						System.out.println("Successfully Entered...");
						return true;
					}
				}else {
					boolean switchIPFlag = true;
					while(switchIPFlag) {
						System.out.println("The Entered continent ID is already present, kindly enter a new ID"
								+ "\nPress\n1. Enter new ID\n2. Return back");
						int switchIP = ob.nextInt();
						switch(switchIP)
						{
						case 1:
							switchIPFlag=false;
							continue;
						case 2:
//							switchIPFlag=false;
//							addContinentFlag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}
			}
		}catch(InputMismatchException e) {
			System.out.println("Ïnput must be an integer");
		}
		return false;
	}
	public boolean removeContinent() {
		try {
			boolean removeContinentFlag=true;
			while(removeContinentFlag) {
				System.out.print("\nEnter the continent ID need to be removed");
				int continentID = ob.nextInt();
				if(!validateOb.validateContinentID(continentID)) {
					boolean flag=true;
					while(flag) {
						System.out.println("\nPress 1.Confirm\n2.Return back");
						int switchInput=ob.nextInt();
						switch(switchInput) {
						case 1:
							List<Continent> continentList = new ContinentMapReader().readContinentFile();
							for(Continent continentObject:continentList) {
								if(continentObject.getContinentId().equalsIgnoreCase(continentID+"")) {
									List<Country> countryList = new CountryMapreader().readCountryMap();
									for(Country countryObject: countryList) {
										if(countryObject.getContinentId().equalsIgnoreCase(continentID+"")) {
											removeAllCountry(Integer.parseInt(countryObject.getCountryId()));
										}
									}
									continentList.remove(continentList.indexOf(continentObject));
									writeContinentFile(continentList);
									System.out.println("Successfully removed the Continent ID - "+continentID);
									return true;
								}
							}
							flag=false;
							break;
						case 2:
							flag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}else {
					boolean switchIPFlag = true;
					while(switchIPFlag) {
						System.out.println("The Entered continent ID is not present, kindly enter an existing ID"
								+ "\nPress\n1. Enter existing ID\n2. Return back");
						int switchIP = ob.nextInt();
						switch(switchIP)
						{
						case 1:
							switchIPFlag=false;
							continue;
						case 2:
//							switchIPFlag=false;
//							addContinentFlag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}ob.hasNextLine();
			}
		}catch(InputMismatchException e) {
			System.out.println("Ïnput must be an integer");
		}
		return false;
	}
	public boolean removeAllCountry(int countryID) {
		List<Country> countryList = new CountryMapreader().readCountryMap();
		List<Borders> bordersList = new CountryBorderReader().mapCountryBorderReader();
		for(Borders borderObject:bordersList) {
			if(borderObject.getCountryId().equalsIgnoreCase(countryID+"")) {
				bordersList.remove(bordersList.indexOf(borderObject));
			}
			
			for(String s: borderObject.getAdjacentCountries()) {
				if(s.equalsIgnoreCase(countryID+"")) {
					borderObject.getAdjacentCountries().remove(s);
				}
			}
		}

		writeBordersFile(bordersList);
		System.out.println("Borders File Successfully updated");
		
		for(Country countryObject:countryList) {
			if(countryObject.getCountryId().equalsIgnoreCase(countryID+"")) {
				countryList.remove(countryList.indexOf(countryObject));
				writeCountryFile(countryList);
				System.out.println("Country File is Successfully updated");
				return true;
			}
		}
		return true;
	}
	public boolean removeCountry() {
		try {
			boolean removeCountryFlag=true;
			while(removeCountryFlag) {
				System.out.print("\nEnter the country ID need to be removed");
				int countryID = ob.nextInt();
				if(!validateOb.validateCountryID(countryID)) {
					boolean flag=true;
					while(flag) {
						System.out.println("\nPress 1.Confirm\n2.Return back");
						int switchInput=ob.nextInt();
						switch(switchInput) {
						case 1:
							removeAllCountry(countryID);
							return true;
						case 2:
							flag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}else {
					boolean switchIPFlag = true;
					while(switchIPFlag) {
						System.out.println("The Entered country ID is not present, kindly enter an existing ID"
								+ "\nPress\n1. Enter existing ID\n2. Return back");
						int switchIP = ob.nextInt();
						switch(switchIP)
						{
						case 1:
							switchIPFlag=false;
							continue;
						case 2:
//							switchIPFlag=false;
//							addContinentFlag=false;
							return false;
						default:
							System.out.println("Your selected option is not valid... Try again");
						}
					}
				}ob.hasNextLine();
			}
		}catch(InputMismatchException e) {
			System.out.println("Ïnput must be an integer");
		}
		return false;
	}
	
}
