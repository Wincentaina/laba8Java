import java.util.*;

// Интерфейс для тестовых раннеров
interface ITestRunner {
    boolean executeTest(String input, String expected);
}

// Простой тестовый раннер
class SimpleTestRunner implements ITestRunner {
    @Override
    public boolean executeTest(String input, String expected) {
        return input.equals(expected);
    }
}

// Расширенный тестовый раннер
class AdvancedTestRunner implements ITestRunner {
    private int complexityLevel;

    public AdvancedTestRunner(int complexityLevel) {
        this.complexityLevel = complexityLevel;
    }

    @Override
    public boolean executeTest(String input, String expected) {
        System.out.println("Executing with complexity level: " + complexityLevel);
        return input.equals(expected) && complexityLevel > 2;
    }
}

// Класс для представления теста
class TestCase {
    private final String input;
    private final String expected;
    private final ITestRunner testRunner;  // Поле для тестового раннера

    public TestCase(String input, String expected, ITestRunner testRunner) {
        this.input = input;
        this.expected = expected;
        this.testRunner = testRunner;
    }

    public String getInput() {
        return this.input;
    }

    public String getExpected() {
        return this.expected;
    }

    public ITestRunner getTestRunner() {
        return this.testRunner;
    }

    // Метод для выполнения теста с использованием конкретного раннера
    public boolean executeTest() {
        return testRunner.executeTest(input, expected);
    }
}

// Базовый класс для тестов с описанием
class DescriptiveTestCase extends TestCase {
    private final String description;

    public DescriptiveTestCase(String input, String expected, String description, ITestRunner testRunner) {
        super(input, expected, testRunner);
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}

// Класс для сортировки тестов по полю input
class TestCaseInputComparator implements Comparator<TestCase> {
    @Override
    public int compare(TestCase t1, TestCase t2) {
        return t1.getInput().compareTo(t2.getInput());
    }
}

public class Main {

    // Алгоритм поиска теста по значению expected
    public static TestCase findTestCaseByExpected(List<TestCase> testCases, String expected) {
        for (TestCase testCase : testCases) {
            if (testCase.getExpected().equals(expected)) {
                return testCase;
            }
        }
        return null; // Если тест не найден
    }

    public static void main(String[] args) {
        // Создаем контейнеры для тестов
        List<TestCase> testCases = new ArrayList<>();
        List<DescriptiveTestCase> descriptiveTestCases = new ArrayList<>();

        // Добавляем тесты
        testCases.add(new TestCase("input1", "expected1", new SimpleTestRunner()));
        testCases.add(new TestCase("input2", "input2", new AdvancedTestRunner(3)));

        descriptiveTestCases.add(new DescriptiveTestCase("input3", "expected3", "Test A", new SimpleTestRunner()));
        descriptiveTestCases.add(new DescriptiveTestCase("input4", "expected4", "Test C", new AdvancedTestRunner(5)));
        descriptiveTestCases.add(new DescriptiveTestCase("input5", "expected5", "Test B", new SimpleTestRunner()));

        // Алгоритм сортировки тестов по полю input
        testCases.sort(new TestCaseInputComparator());
        System.out.println("Sorted test cases by input:");
        for (TestCase testCase : testCases) {
            System.out.println(testCase.getInput());
        }

        // Поиск теста по значению expected
        String searchExpected = "expected3";
        TestCase foundTest = findTestCaseByExpected(testCases, searchExpected);

        if (foundTest != null) {
            System.out.println("Found test with expected: " + foundTest.getExpected());
        } else {
            System.out.println("Test with expected '" + searchExpected + "' not found.");
        }

        // Выполняем тесты и выводим результаты
        for (TestCase testCase : testCases) {
            boolean result = testCase.executeTest();
            System.out.println("Test with input: " + testCase.getInput() + " passed: " + result);
        }
    }
}
