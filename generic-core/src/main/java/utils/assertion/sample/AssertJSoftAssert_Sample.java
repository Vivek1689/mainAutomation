package utils.assertion.sample;

import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

public class AssertJSoftAssert_Sample {

	@Test
	void string_test() {
		String str = "Hello World";

		SoftAssertions softAssert = new SoftAssertions();
		softAssert.assertThat(str)
		.isNotNull()
		.as("String is Empty").isNotEmpty() // passing custom msg to be printed for each assertions
		.as("String is Blank").isNotBlank()
		.isEqualTo("HelloWorld")
		.contains("llo W");

		System.out.println("Continue execution ....");

		softAssert.assertAll();

	}

	@Test
	void string_testbdd() {
		String str = "Hello World";

		// Similar to above BDD style assertion can be used
		BDDSoftAssertions bddSoftAssert = new BDDSoftAssertions();

		bddSoftAssert.then(str)
		.isNotNull()
		.as("String is Empty").isNotEmpty() // passing custom msg to be printed for each assertions
		.as("String is not Blank").isBlank()
		.isEqualTo("Hello World");

		bddSoftAssert.assertAll();

	}

}




















