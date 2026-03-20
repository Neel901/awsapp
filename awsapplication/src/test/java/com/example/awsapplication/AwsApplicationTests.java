package com.example.awsapplication;

import com.example.awsapplication.auth.JwtUtil;
import com.example.awsapplication.controller.AppController;
import com.example.awsapplication.controller.AuthController;
import com.example.awsapplication.entity.Asset;
import com.example.awsapplication.repo.coverpage.CoverpageAssetRepository;
import com.example.awsapplication.repo.ebook.EbookAssetRepository;
import com.example.awsapplication.repo.hardback.HardbackAssetRepository;
import com.example.awsapplication.repo.paperback.PaperbackAssetRepository;
import com.example.awsapplication.service.SqsProducerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AwsApplicationTests {

    private MockMvc mockMvc;
    private SqsProducerService sqsService;
    private CoverpageAssetRepository coverpageRepo;
    private EbookAssetRepository ebookAssetRepository;
    private HardbackAssetRepository hardbackAssetRepository;
    private PaperbackAssetRepository paperbackAssetRepository;

    @BeforeEach
    void setup() {

        coverpageRepo = mock(CoverpageAssetRepository.class);
        ebookAssetRepository = mock(EbookAssetRepository.class);
        hardbackAssetRepository = mock(HardbackAssetRepository.class);
        paperbackAssetRepository = mock(PaperbackAssetRepository.class);
        sqsService = mock(SqsProducerService.class);

        AppController appController = new AppController(
                coverpageRepo,
                ebookAssetRepository,
                hardbackAssetRepository,
                paperbackAssetRepository,
                sqsService
        );

        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
    }

    @Test
    void sendMessage_endpoint_callsSqs() throws Exception {
        String message = "Hello SQS";
        mockMvc.perform(post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent to SQS"));
        verify(sqsService, times(1)).sendMessage(message);
    }
    @Test
    void getCoverpageAssets() throws Exception {
        when(coverpageRepo.findAll())
                .thenReturn(List.of(new Asset(1, "Test")));

        mockMvc.perform(get("/coverpage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test"));
    }

    @Test
    void testGetCoverpageById_Found() throws Exception {
        Asset asset = new Asset();
        asset.setId(1);
        when(coverpageRepo.findById(1)).thenReturn(Optional.of(asset));
        mockMvc.perform(get("/coverpage/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetCoverpageById_NotFound() throws Exception {
        when(coverpageRepo.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/coverpage/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetEbookById_Found() throws Exception {
        Asset asset = new Asset();
        asset.setId(1);
        when(ebookAssetRepository.findById(1)).thenReturn(Optional.of(asset));
        mockMvc.perform(get("/ebook/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetEbookById_NotFound() throws Exception {
        when(ebookAssetRepository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/ebook/1"))
                .andExpect(status().isNotFound());
    }
    @Test
    void testGetHardbackById_Found() throws Exception {
        Asset asset = new Asset();
        asset.setId(1);
        when(hardbackAssetRepository.findById(1)).thenReturn(Optional.of(asset));
        mockMvc.perform(get("/hardback/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetHardbackById_NotFound() throws Exception {
        when(hardbackAssetRepository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/hardback/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPaperbackById_Found() throws Exception {
        Asset asset = new Asset();
        asset.setId(1);
        when(paperbackAssetRepository.findById(1)).thenReturn(Optional.of(asset));
        mockMvc.perform(get("/paperback/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetPaperbackById_NotFound() throws Exception {
        when(paperbackAssetRepository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/paperback/1"))
                .andExpect(status().isNotFound());
    }
}