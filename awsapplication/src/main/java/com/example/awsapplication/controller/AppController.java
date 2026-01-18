package com.example.awsapplication.controller;
import com.example.awsapplication.entity.Asset;
import com.example.awsapplication.repo.coverpage.CoverpageAssetRepository;
import com.example.awsapplication.repo.ebook.EbookAssetRepository;
import com.example.awsapplication.repo.hardback.HardbackAssetRepository;
import com.example.awsapplication.repo.paperback.PaperbackAssetRepository;
import com.example.awsapplication.service.SqsProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppController {
    private final CoverpageAssetRepository coverpageRepo;
    private final EbookAssetRepository ebookRepo;
    private final HardbackAssetRepository hardbackRepo;
    private final PaperbackAssetRepository paperbackRepo;
    private final SqsProducerService sqsProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        sqsProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to SQS");
    }

    @GetMapping("/coverpage")
    public ResponseEntity<List<Asset>> getCoverpageAssets() {
        return ResponseEntity.ok(coverpageRepo.findAll());
    }

    @GetMapping("/ebook")
    public ResponseEntity<List<Asset>> getEbookAssets() {
        return ResponseEntity.ok(ebookRepo.findAll());
    }

    @GetMapping("/hardback")
    public ResponseEntity<List<Asset>> getHardbackAssets() {
        return ResponseEntity.ok(hardbackRepo.findAll());
    }

    @GetMapping("/paperback")
    public ResponseEntity<List<Asset>> getPaperbackAssets() {
        return ResponseEntity.ok(paperbackRepo.findAll());
    }

    @GetMapping("/coverpage/{id}")
    public Optional<Asset> getCoverpageById(@PathVariable int id) {
        return coverpageRepo.findById(id);
    }

    @GetMapping("/ebook/{id}")
    public Optional<Asset> getEbookById(@PathVariable int id) {
        return ebookRepo.findById(id);
    }

    @GetMapping("/hardback/{id}")
    public Optional<Asset> getHardbackById(@PathVariable int id) {
        return hardbackRepo.findById(id);
    }

    @GetMapping("/paperback/{id}")
    public Optional<Asset> getPaperbackById(@PathVariable int id) {
        return paperbackRepo.findById(id);
    }
}

