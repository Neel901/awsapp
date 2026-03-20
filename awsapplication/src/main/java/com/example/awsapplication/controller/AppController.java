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
    public ResponseEntity<Asset> getCoverpageById(@PathVariable int id) {
        Optional<Asset> asset = coverpageRepo.findById(id);

        if (asset.isPresent()) {
            return ResponseEntity.ok(asset.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ebook/{id}")
    public ResponseEntity<Asset> getEbookById(@PathVariable int id) {
        Optional<Asset> asset = ebookRepo.findById(id);

        if (asset.isPresent()) {
            return ResponseEntity.ok(asset.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hardback/{id}")
    public ResponseEntity<Asset> getHardbackById(@PathVariable int id) {
        Optional<Asset> asset = hardbackRepo.findById(id);

        if (asset.isPresent()) {
            return ResponseEntity.ok(asset.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paperback/{id}")
    public ResponseEntity<Asset> getPaperbackById(@PathVariable int id) {
        Optional<Asset> asset = paperbackRepo.findById(id);

        if (asset.isPresent()) {
            return ResponseEntity.ok(asset.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

