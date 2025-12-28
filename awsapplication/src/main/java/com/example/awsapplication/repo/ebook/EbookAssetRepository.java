package com.example.awsapplication.repo.ebook;

import com.example.awsapplication.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EbookAssetRepository extends JpaRepository<Asset, String> {
}
