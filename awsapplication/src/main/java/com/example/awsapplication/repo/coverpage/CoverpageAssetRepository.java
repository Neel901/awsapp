package com.example.awsapplication.repo.coverpage;
import com.example.awsapplication.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverpageAssetRepository extends JpaRepository<Asset, Integer> {
}
