package reaa;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.File;

public class OrganizationAccount {
	HashMap<String, Double> dailyCosts = new HashMap<String, Double>();//to be read from csv in constructor
	HashMap<String, Double> dailyIncome = new HashMap<String, Double>();//to be read from csv in constructor
	
	double expenses = 0.0; //default val
	double income = 0.0; //default val
	char timeframe = 'M'; //set to M for Monthly (only valid to D,W,M,Y,C) // (day, week, month, year, custom)
	double profit = 0.0; //default val
	
	private BufferedReader openCsv(String fileName) throws IOException {
		// 1) try getResourceAsStream from this class (leading slash = classpath root)
		InputStream is = getClass().getResourceAsStream("/" + fileName);
		if (is != null) {
			return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		}
		// 2) try context ClassLoader (no leading slash)
		InputStream is2 = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (is2 != null) {
			return new BufferedReader(new InputStreamReader(is2, StandardCharsets.UTF_8));
		}
		// 3) try package path (e.g. src/main/dailyCosts.csv -> classpath resource 'main/dailyCosts.csv')
		InputStream is3 = Thread.currentThread().getContextClassLoader().getResourceAsStream("main/" + fileName);
		if (is3 != null) {
			return new BufferedReader(new InputStreamReader(is3, StandardCharsets.UTF_8));
		}
		// 4) try working directory
		File f = new File(fileName);
		if (f.exists()) {
			return new BufferedReader(new FileReader(f));
		}
		// 5) try src/ folder
		File f2 = new File("src" + File.separator + fileName);
		if (f2.exists()) {
			return new BufferedReader(new FileReader(f2));
		}
		// not found
		return null;
	}
	
	public void processCashflow(){
		// CSV file names (no path) — we'll try classpath first, then working dir, then src/
		String costCsv = "dailyCosts.csv";
		String incomeCsv = "dailyIncome.csv";
		
		Map<String, Double> costMap = new HashMap<>();
		Map<String, Double> incomeMap = new HashMap<>();
		
		// Read cost CSV
		try (BufferedReader br = openCsv(costCsv)){
			if (br == null) {
				// resource not found; silently ignore here (or handle as needed)
			} else {
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						String key = parts[0].trim();
						try {
							Double value = Double.parseDouble(parts[1].trim());
							costMap.put(key, value);
						} catch (NumberFormatException e) {
							// skip invalid numeric values
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Read profit CSV
		try (BufferedReader br = openCsv(incomeCsv)){
			if (br == null) {
				// resource not found; silently ignore here (or handle as needed)
			} else {
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						String key = parts[0].trim();
						try {
							Double value = Double.parseDouble(parts[1].trim());
							incomeMap.put(key, value);
						} catch (NumberFormatException e) {
							// skip invalid numeric values
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		// store into fields if you need them later
		this.dailyCosts.putAll(costMap);
		this.dailyIncome.putAll(incomeMap);
		
		this.getTotals();
	}

	public void getTotals() {
		// Calculate total expenses
		double totalCosts = 0.0;
		for (double cost : dailyCosts.values()) {
			totalCosts += cost;
		}
		this.expenses = totalCosts;
		
		// Calculate total income
		double totalIncome = 0.0;
		for (double inc : dailyIncome.values()) {
			totalIncome += inc;
		}
		this.income = totalIncome;
		
		// Calculate profit
		this.profit = this.income - this.expenses;
	}
	
	
	public double getIncome() {
		return this.income;
	}
	public double getExpenses() {
		return this.expenses;
	}
	public double getProfit() {
		return this.profit;
	}	
	
}