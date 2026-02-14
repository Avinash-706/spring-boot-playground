package com.tyss;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    private LoanService loanService;

    @BeforeAll
    public static void startService(){
        System.out.println("-- Starting the Testing Service --");
    }

    @BeforeEach
    public void setUp() {
        loanService = new LoanService();
    }

    @Test
    public void testValidEligibility() {
        assertTrue(loanService.isEligible(25, 50000));
    }

    @Test
    public void testInvalidAge_TooYoung() {
        assertFalse(loanService.isEligible(20, 30000));
    }


    @Test
    public void testInvalidAge_TooOld() {
        assertFalse(loanService.isEligible(61, 30000));
    }

    @Test
    public void testInvalidSalary() {
        assertFalse(loanService.isEligible(25, 20000));
    }

    @Test
    public void testBoundaryAge_Lower() {
        assertTrue(loanService.isEligible(21, 25000));
    }

    @Test
    public void testBoundaryAge_Upper() {
        assertTrue(loanService.isEligible(60, 25000));
    }

    @Test
    public void testBoundarySalary() {
        assertTrue(loanService.isEligible(30, 25000));
    }


    @Test
    public void testValidEMICalculation() {
        double emi = loanService.calculateEMI(120000, 5);
        assertEquals(2000.0, emi, 0.01);
    }

    @Test
    public void testExceptionForInvalidLoanAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.calculateEMI(0, 5);
        });
    }

    @Test
    public void testExceptionForNegativeLoanAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.calculateEMI(-10000, 5);
        });
    }

    @Test
    public void testExceptionForInvalidTenure() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.calculateEMI(100000, 0);
        });
    }

    @Test
    public void testExceptionForNegativeTenure() {
        assertThrows(IllegalArgumentException.class,
                () -> {
            loanService.calculateEMI(100000, -2);
        }
        );
    }

    @Test
    public void testCreditScorePremium() {
        assertEquals("Premium", loanService.getLoanCategory(750));
        assertEquals("Premium", loanService.getLoanCategory(800));
    }


    @Test
    public void testCreditScoreStandard() {
        assertEquals("Standard", loanService.getLoanCategory(600));
        assertEquals("Standard", loanService.getLoanCategory(700));
        assertEquals("Standard", loanService.getLoanCategory(749));
    }

    @Test
    public void testCreditScoreHighRisk() {
        assertEquals("High Risk", loanService.getLoanCategory(599));
        assertEquals("High Risk", loanService.getLoanCategory(400));
    }

    @Test
    public void testLoanServiceNotNull() {
        assertNotNull(loanService);
    }

    @Test
    public void testGetLoanCategoryNotNull() {
        String category = loanService.getLoanCategory(700);
        assertNotNull(category);
    }


    @Test
    public void testGroupedAssertions() {
        assertAll("Eligibility Checks",
            () -> assertTrue(loanService.isEligible(25, 30000)),
            () -> assertFalse(loanService.isEligible(18, 30000)),
            () -> assertFalse(loanService.isEligible(25, 20000))
        );
    }

    @Test
    public void testGroupedEMIAssertions() {
        assertAll("EMI Calculations",
            () -> assertEquals(1000.0, loanService.calculateEMI(60000, 5), 0.01),
            () -> assertEquals(500.0, loanService.calculateEMI(60000, 10), 0.01),
            () -> assertThrows(IllegalArgumentException.class, () -> loanService.calculateEMI(-1000, 5))
        );
    }

    @Test
    public void testGroupedCreditScoreAssertions() {
        assertAll("Credit Score Categories",
                () -> assertEquals("Premium", loanService.getLoanCategory(750)),
                () -> assertEquals("Standard", loanService.getLoanCategory(650)),
                () -> assertEquals("High Risk", loanService.getLoanCategory(500))
        );
    }

    @AfterAll
    public  static void testEnded(){
        System.out.println("-- All Test Completed --");
    }
}
