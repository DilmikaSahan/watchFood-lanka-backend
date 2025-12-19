package com.aiService.aiService.kafka;

import com.aiService.aiService.dto.priorityRequestDto;
import com.aiService.aiService.dto.priorityResponseDto;
import com.aiService.aiService.service.priorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class kafkaConsumer {

    private  final priorityService PriorityService;

    private final KafkaTemplate<String, priorityResponseDto> kafkaTemplate;

    @KafkaListener(topics = "complaint-priority-request")
    public void consume(priorityRequestDto request) {
        priorityResponseDto response = PriorityService.predictPriority(request);

        kafkaTemplate.send("complaint-priority-result", response);
    }
}
