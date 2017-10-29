package befaster.solutions;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.Test;

public class SupermarketAppTest {

	@Test
	public void givenEmptyString_shouldReturnZero() {
		assertThat(SupermarketApp.checkout(""), is(0));
	}
	
	@Test
	public void givenSingleItem_shouldChargeSinglePrice() {
		assertThat(SupermarketApp.checkout("A"), is(50));
	}
	
	@Test
	public void givenOneMultipleLessThanDiscount_shouldMultiplySinglePrice() {
		assertThat(SupermarketApp.checkout("AA"), is(100));
	}
	
	@Test
	public void givenBasketWithMultiplesLessThanDiscount_shouldMultiplySinglePrice() {
		assertThat(SupermarketApp.checkout("AABCCC"), is(190));
	}
	
	@Test
	public void given5As_shouldGetDiscountFor5As() {
		assertThat(SupermarketApp.checkout("AAAAA"), is(200));
	}
	
	// Freebies
	@Test
	public void givenTwoEs_shouldGetAFeeB() {
		assertThat(SupermarketApp.checkout("EEB"), is(80));
	}
	
	@Test
	public void givenTwoEsAndTwoBs_shouldGetOneBFreeAndPayForTheOther() {
		assertThat(SupermarketApp.checkout("EEBB"), is(110));
	}
	
	@Test
	public void givenInvalidInput_shouldReturnMinus1() {
		assertThat(SupermarketApp.checkout("AAAABCD-"), is(-1));
	}
	
	@Test
	public void givenOneMultipleOfDiscountAmount_shouldReturnDiscountAmount() {
		assertThat(SupermarketApp.checkout("AAA"), is(130));
	}
	
	@Test
	public void givenComplexBasketWithDiscounts_shouldReturnRightAmount() {
		assertThat(SupermarketApp.checkout("AAABBBBCCCDD"), is(310));
	}
	
	@Test
	public void givenEEEEBB_shouldReturnCorrectTotalApplyingTwoFreebies() {
		assertThat(SupermarketApp.checkout("EEEEBB"), is(160));
	}
	
	@Test
	public void givenBEBEEE_shouldReturnCorrectTotalApplyingTwoFreebies() {
		assertThat(SupermarketApp.checkout("BEBEEE"), is(160));
	}
	
	// Fs
	@Test
	public void  given3Fs_shouldChargePriceOf2s() {
		assertThat(SupermarketApp.checkout("FFF"), is(20));
	}
}
