package com.example.awsapplication.repo.paperback;

import com.example.awsapplication.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperbackAssetRepository extends JpaRepository<Asset, Integer> {
}
