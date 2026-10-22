package se331.bokipassport.util;

public final class ExamAccess {
    private ExamAccess() {
    }

    public static boolean canAccessExam(Long examLevelId, Long userLevelId, boolean isAdmin) {
        if (isAdmin) {
            return true;
        }
        if (examLevelId == null || userLevelId == null) {
            return false;
        }
        return examLevelId <= userLevelId;
    }
}
