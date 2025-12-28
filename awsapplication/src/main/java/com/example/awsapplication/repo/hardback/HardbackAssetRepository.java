package com.example.awsapplication.repo.hardback;

import com.example.awsapplication.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardbackAssetRepository extends JpaRepository<Asset, String> {
}
