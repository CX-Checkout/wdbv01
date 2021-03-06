package befaster.solutions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SKU {
	public final char key;
	public final int singlePrice;
	public final List<Discount> discounts;
	public final Character freebieProduct;
	public final int freebieQuantity;

	public SKU(char key, int singlePrice, List<Discount> discounts, int freebieQuantity, Character freebieProduct) {
		this.key = key;
		this.singlePrice = singlePrice;
		if(discounts != null) {
			this.discounts = discounts;
		} else {
			this.discounts = Collections.EMPTY_LIST;
		}
		this.freebieQuantity = freebieQuantity;
		this.freebieProduct = freebieProduct;
	}
	
	public int calculatePriceOfBasket(int quantity) {
		
		int total = 0;
		
		for(Discount discount: discounts) {
			while(quantity >= discount.quantity) {
				total += discount.price;
				quantity -= discount.quantity;
			}
		}
		
		// once all discounts have been applied, charge full price for any items that remain
		total += quantity * singlePrice;
		
//		if(quantity < discountQuantity || discountQuantity == -1) {
//			return quantity * singlePrice;
//		} else {
//			return discountPrice + calculatePriceOfBasket(quantity - discountQuantity);
//		}
		
		return total;
	}
	
	public Optional<Character> getFreebie() {
		return Optional.ofNullable(freebieProduct);
	}
}