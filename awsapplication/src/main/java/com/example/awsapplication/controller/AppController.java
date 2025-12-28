package com.example.awsapplication.controller;
import com.example.awsapplication.service.SqsProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sqs")
@RequiredArgsConstructor
public class AppController {

    private final SqsProducerService sqsProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        sqsProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to SQS");
    }
}

