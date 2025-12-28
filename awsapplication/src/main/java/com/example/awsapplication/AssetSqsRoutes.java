package com.example.awsapplication;

//
//import com.example.AssetMessage;
//import com.example.entity.Asset;
//import com.example.repo.coverpage.CoverpageAssetRepository;
//import com.example.repo.ebook.EbookAssetRepository;
//import com.example.repo.hardback.HardbackAssetRepository;
//import com.example.repo.paperback.PaperbackAssetRepository;
import com.example.awsapplication.entity.Asset;
import com.example.awsapplication.repo.coverpage.CoverpageAssetRepository;
import com.example.awsapplication.repo.ebook.EbookAssetRepository;
import com.example.awsapplication.repo.hardback.HardbackAssetRepository;
import com.example.awsapplication.repo.paperback.PaperbackAssetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetSqsRoutes extends RouteBuilder {

    private final CoverpageAssetRepository coverpageRepo;
    private final EbookAssetRepository ebookRepo;
    private final HardbackAssetRepository hardbackRepo;
    private final PaperbackAssetRepository paperbackRepo;

    @Override
    public void configure() {

        from("aws2-sqs:coverpageq")
                .unmarshal().json(JsonLibrary.Jackson, AssetMessage.class)
                .process(e -> {
                    AssetMessage msg = e.getMessage().getBody(AssetMessage.class);
                    coverpageRepo.save(new Asset(msg.getAssetId(), msg.getTitle()));
                });

        from("aws2-sqs:ebookq")
                .unmarshal().json(JsonLibrary.Jackson, AssetMessage.class)
                .process(e -> {
                    AssetMessage msg = e.getMessage().getBody(AssetMessage.class);
                    ebookRepo.save(new Asset(msg.getAssetId(), msg.getTitle()));
                });

        from("aws2-sqs:hardbackq")
                .unmarshal().json(JsonLibrary.Jackson, AssetMessage.class)
                .process(e -> {
                    AssetMessage msg = e.getMessage().getBody(AssetMessage.class);
                    hardbackRepo.save(new Asset(msg.getAssetId(), msg.getTitle()));
                });

        from("aws2-sqs:paperbackq")
                .unmarshal().json(JsonLibrary.Jackson, AssetMessage.class)
                .process(e -> {
                    AssetMessage msg = e.getMessage().getBody(AssetMessage.class);
                    paperbackRepo.save(new Asset(msg.getAssetId(), msg.getTitle()));
                });
    }
}

