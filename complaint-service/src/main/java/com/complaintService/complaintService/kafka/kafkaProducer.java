package com.complaintService.complaintService.kafka;

import com.complaintService.complaintService.dto.priorityRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class kafkaProducer {
    private final KafkaTemplate<String, priorityRequestDto> kafkaTemplate;

    public void sendForPrioritization(priorityRequestDto requestDto) {
        kafkaTemplate.send("complaint-priority-request", requestDto);
    }
}
