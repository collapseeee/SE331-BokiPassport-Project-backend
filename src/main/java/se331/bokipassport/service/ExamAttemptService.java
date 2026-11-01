package se331.bokipassport.service;

import se331.bokipassport.entity.dto.ExamSubmitRequest;
import se331.bokipassport.entity.dto.ExamSubmitResponse;
import se331.bokipassport.security.user.UserAccount;

public interface ExamAttemptService {
    ExamSubmitResponse submit(Long examId, ExamSubmitRequest request, UserAccount account);
}
