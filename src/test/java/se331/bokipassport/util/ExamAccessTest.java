package se331.bokipassport.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExamAccessTest {

    @Test
    void aUserBelowTheExamLevelIsLockedOut() {
        assertFalse(ExamAccess.canAccessExam(3L, 1L, false));
    }

    @Test
    void aUserOnTheSameLevelMayTakeTheExam() {
        assertTrue(ExamAccess.canAccessExam(3L, 3L, false));
    }

    @Test
    void aUserAboveTheExamLevelMayTakeTheExam() {
        assertTrue(ExamAccess.canAccessExam(3L, 4L, false));
    }

    @Test
    void anAdminBypassesTheLevelCheck() {
        assertTrue(ExamAccess.canAccessExam(3L, 1L, true));
    }

    @Test
    void anUnknownCallerLevelIsLockedOut() {
        assertFalse(ExamAccess.canAccessExam(3L, null, false));
        assertFalse(ExamAccess.canAccessExam(null, 3L, false));
    }
}
