package com.example.demo.repository;

import com.example.demo.model.shop.Shop;
import com.example.demo.model.shop.ShopReportDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository {
    List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);
    ShopReportDTO getShopByDate(LocalDate dataInicio, LocalDate dataFim);
}