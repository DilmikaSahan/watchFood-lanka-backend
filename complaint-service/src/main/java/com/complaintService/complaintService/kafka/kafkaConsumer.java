package com.complaintService.complaintService.kafka;

import com.complaintService.complaintService.dto.priorityResponseDto;
import com.complaintService.complaintService.service.complaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class kafkaConsumer {
    private  final complaintService ComplaintService;
    @KafkaListener(topics = "complaint-priority-result")
    public void consume(priorityResponseDto responseDto){
        ComplaintService.updateComplaintPriority(responseDto);
    }

}
