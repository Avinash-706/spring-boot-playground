package com.tyss;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentEligibilityService service;

    @BeforeAll
    static void beforeAllTests() {
        System.out.println("-- Testing Started --");
    }

    @BeforeEach
    void setup() {
        service = new StudentEligibilityService();
    }

    @Test
    void testAssertTrue() {
        assertTrue(service.isEligible(18));
    }

    @Test
    void testAssertFalse() {
        assertFalse(service.isEligible(17));
    }

    @Test
    void testAssertEquals() {
        assertEquals(true, service.isEligible(18));
    }

    @Test
    void testAssertNotEquals() {
        assertNotEquals(false, service.isEligible(25));
    }

    @DisplayName("Test Assert Not Null")
    @Test
    void testAssertNotNull() {
        assertNotNull(service);
    }

    @Test
    void testAssertSame() {
        StudentEligibilityService ref1 = service;
        StudentEligibilityService ref2 = service;
        assertSame(ref1, ref2);
    }

    @Test
    void testAssertNotSame() {
        StudentEligibilityService ref1 = new StudentEligibilityService();
        StudentEligibilityService ref2 = new StudentEligibilityService();
        assertNotSame(ref1, ref2);
    }

    @Test
    void testAssertAll() {
        assertAll(
                () -> assertTrue(service.isEligible(18)),
                () -> assertFalse(service.isEligible(16)),
                () -> assertEquals(true, service.isEligible(30))
        );
    }

    @Test
    void testAssertThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.isEligible(-5);
        });
    }

    @Test
    void testFailExample() {
        if (service.isEligible(18) != true) {
            fail("Eligibility logic failed");
        }
    }

    @AfterEach
    void tearDown() {
        service = null;
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("-- Testing Completed --");
    }
}
