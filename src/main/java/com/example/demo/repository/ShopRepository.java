package com.example.demo.repository;

import com.example.demo.model.shop.Shop;
import com.example.demo.model.shop.ShopReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByUserIdentifier(String userIdentifier);
    List<Shop> findAllByTotalGreaterThan(Float total);
    List<Shop> findAllByDateGreaterThanEqual(LocalDateTime date);
}
