package befaster.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class SupermarketApp {
	private static Map<Character, SKU> skus = new HashMap<Character, SKU>() {{
		put('A', new SKU('A', 50, asList(new Discount(5, 200), new Discount(3, 130)), -1, null));
		put('B', new SKU('B', 30, asList(new Discount(2, 45)) , -1, null));
		put('C', new SKU('C', 20, null, 						-1, null));
		put('D', new SKU('D', 15, null,							-1, null));
		put('E', new SKU('E', 40, null,							 2, 'B'));
	}};
	
	public static int checkout(String s) {
		
		int total = 0;
		
		Multiset<Character> quantities = HashMultiset.create();
		
		for(int i = 0; i < s.length(); i++) {
			
			char c = s.charAt(i);
			
			if(!skus.containsKey(c)) {
				return -1;
			} else {
				quantities.add(c);
			}
		}
		
		Multiset<Character> freebies = HashMultiset.create();
		
		// Find freebies
		for(char c: quantities.elementSet()) {
			int quantity = quantities.count(c);
			final SKU sku = skus.get(c);
			Optional<Character> freebie = sku.getFreebie();
			freebie.ifPresent(f -> freebies.add(f, quantity / sku.freebieQuantity));
		}
		
		// Apply freebies
		for(char c: freebies.elementSet()) {
			quantities.remove(c, freebies.count(c));
		}
		
		// Calculate price based on totals
		for(char c: quantities.elementSet()) {
			total += skus.get(c).calculatePriceOfBasket(quantities.count(c));
		}
		
		return total;
	}
}
