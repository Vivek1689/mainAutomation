package utils.assertion.sample;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssertJ_Sample {

    @Test
    void string_test() {
        String str = "Hello World";

        Assertions.assertThat(str)
                .isNotNull()
                .as("String is Empty").isNotEmpty() // passing custom msg to be printed for each assertions
                .as("String is Blank").isNotBlank()
                .isEqualTo("Hello World")
                .contains("llo W")
                .doesNotContain("Not")
                .containsWhitespaces()
                .matches("\\w.* World")
                .doesNotContainPattern("\\d.*")
                .hasSize(11)
                .hasSizeGreaterThan(10)
                .hasSizeLessThan(20)
                .hasSizeBetween(1, 40)
                .endsWith("ld")

                .as("CVriable is not 'String' type").isInstanceOf(String.class);

        // Similar to above BDD style assertion can be used
        BDDAssertions.then(str)
                .isNotNull()
                .as("String is Empty").isNotEmpty() // passing custom msg to be printed for each assertions
                .as("String is Blank").isNotBlank()
                .isEqualTo("Hello World")
                .contains("llo W")
                .isMixedCase();

    }

    @Test
    void int_test() {
        int a = 10;

        Assertions.assertThat(a)
                .isEqualTo(10)
                .isCloseTo(14, Offset.offset(5)) // Offset 5 means in range of 5-15
                .isInstanceOf(Integer.class)
                .isBetween(3, 12)
                .isCloseTo(12, Percentage.withPercentage(30)) // 7 - 13
                .isNotCloseTo(15, Percentage.withPercentage(30))
                .isPositive()
                .isEven()
                .isGreaterThanOrEqualTo(5)
                .isLessThanOrEqualTo(20);
    }

    @Test
    void list_test() {
        List<String> l1 = Arrays.asList("Testing", "Java", "Selenium");
        List<String> l2 = Arrays.asList("Testing", "Java");
        List<String> l3 = Arrays.asList("Youtube", "Tutorial");

        Assertions.assertThat(l1)
                .as("sfsdfsdf").hasSize(5)
                .hasSizeBetween(1, 10)
                .hasAtLeastOneElementOfType(String.class)
                .isNotEmpty()
                .contains("Testing")
                .doesNotContain("@@")
                .startsWith("Testing")
                .containsExactly("Testing", "Java", "Selenium")
                .containsExactlyInAnyOrder("Selenium", "Testing", "Java")
                .withFailMessage(() -> "String in list not having size less than 3").allMatch(s -> s.length() < 3)
                .withFailMessage(() -> "String in list not having size less than 3").matches(s -> s.contains("MUS"))
                .containsAll(l2)
                .doesNotContainAnyElementsOf(l3);
    }

    @Test
    void map_test() {
        Map<String, String> m1 = new HashMap<>();

        m1.put("name", "FW");
        m1.put("site", "YT");
        m1.put("lang", "Java");

        Assertions.assertThat(m1)
                .containsEntry("name", "FW")
                .hasSize(3)
                .isNotEmpty()
                .isNotNull()
                .doesNotContainEntry("tool", "Se")
                .doesNotContainKey("key")
                .containsValues("FW", "Java")
                .containsValues("FW", "Java", "YT");

    }


    @SuppressWarnings("unchecked")
    @Test
    public void customClassTest() {

        Employee emp1 = new Employee(25, "sachin", 100); //o1 -->age1, name1, salary1
        Employee emp2 = new Employee(25, "sachin", 100); //o2

        Assertions.assertThat(emp1)
                .isEqualToComparingFieldByField(emp2)
                .isNotNull()
                .isInstanceOf(Employee.class)
                .hasFieldOrProperty("age")
                .hasFieldOrPropertyWithValue("name", "sachin")
                .extracting(e -> e.age).isNotEqualTo(24);

    }
}




















