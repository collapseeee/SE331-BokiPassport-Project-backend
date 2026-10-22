package se331.bokipassport.service;

import se331.bokipassport.entity.dto.ExamSubmitRequest;
import se331.bokipassport.entity.dto.ExamSubmitResponse;

public interface ExamAttemptService {
    ExamSubmitResponse submit(Long examId, ExamSubmitRequest request);
}
