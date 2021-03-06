package befaster.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

public class SupermarketApp {
	private static Map<Character, SKU> skus = new HashMap<Character, SKU>() {{
		put('A', new SKU('A', 50, asList(new Discount(5, 200), new Discount(3, 130)), -1, null));
		put('B', new SKU('B', 30, asList(new Discount(2, 45)) , -1, null));
		put('C', new SKU('C', 20, null, 						-1, null));
		put('D', new SKU('D', 15, null,							-1, null));
		put('E', new SKU('E', 40, null,							 2, 'B'));
		// HACK - buying two Fs => get one free is the same as applying a 2xF discount on 3 Fs
		put('F', new SKU('F', 10, asList(new Discount(3, 20)),  -1, null));
		put('G', new SKU('G', 20, null,							-1, null));
		put('H', new SKU('H', 10, asList(new Discount(10, 80), new Discount(5, 45)), -1, null));
		put('I', new SKU('I', 35, null,							-1, null));
		put('J', new SKU('J', 60, null,							-1, null));
		put('K', new SKU('K', 70, asList(new Discount(2, 120)), -1, null));
		put('L', new SKU('L', 90, null,							-1, null));
		put('M', new SKU('M', 15, null,							-1, null));
		put('N', new SKU('N', 40, null,							 3, 'M'));
		put('O', new SKU('O', 10, null,							-1, null));
		put('P', new SKU('P', 50, asList(new Discount(5, 200)), -1, null));
		put('Q', new SKU('Q', 30, asList(new Discount(3, 80)),  -1, null));
		put('R', new SKU('R', 50, null, 						 3, 'Q'));
		put('S', new SKU('S', 20, null,							-1, null));
		put('T', new SKU('S', 20, null,							-1, null));
		// HACK - buying three Us => get one free is the same as applying a 3xU discount on 4 Us
		put('U', new SKU('U', 40, asList(new Discount(4, 120)),  -1, null));
		put('V', new SKU('V', 50, asList(new Discount(3, 130), new Discount(2, 90)), -1, null));
		put('W', new SKU('W', 20, null,							-1, null));
		put('X', new SKU('X', 17, null,							-1, null));
		put('Y', new SKU('Y', 20, null,							-1, null));
		put('Z', new SKU('Z', 21, null,							-1, null));
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
		
		// 3 of STXYZ should costs 45.
		// Hardcode this for now :-)
		while(remove3OutOf5(quantities)) {
			total += 45;
		}
		
		// Find freebies
		Multiset<Character> freebies = HashMultiset.create();
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
	
	private static boolean remove3OutOf5(Multiset<Character> quantities) {
		Multiset<Character> stxyz = LinkedHashMultiset.create();
		
		stxyz.add('Z', quantities.count('Z'));
		stxyz.add('S', quantities.count('S'));
		stxyz.add('T', quantities.count('T'));
		stxyz.add('Y', quantities.count('Y'));
		stxyz.add('X', quantities.count('X'));
		
		int numElements = stxyz.size();
		if(numElements >= 3) {
			int i = 3;
			for(char c: stxyz) {
				quantities.remove(c);
				if(--i <= 0) break;
			}
			return true;
		} else {
			return false;
		}
	}
}
