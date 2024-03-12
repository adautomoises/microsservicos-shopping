package com.example.demo.service;

import com.example.demo.model.item.ItemDTO;
import com.example.demo.model.shop.Shop;
import com.example.demo.model.shop.ShopDTO;
import com.example.demo.model.shop.ShopReportDTO;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final ReportRepository reportRepository;

    public List<ShopDTO> getAll(){
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier){
        List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO){
        List<Shop> shops = shopRepository.findAllByDateGreaterThanEqual(shopDTO.getDate());

        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }
    public ShopDTO findById(Long productId){
        Optional<Shop> shop = shopRepository.findById(productId);
        return shop.map(ShopDTO::convert).orElse(null);
    }

    public ShopDTO save(ShopDTO shopDTO){
        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum)
        );
        Shop shop = Shop.convert(shopDTO);
        shop.setDate(LocalDateTime.now());

        return ShopDTO.convert(shopRepository.save(shop));
    }

    public List<ShopDTO> getShopsByFilter(
            LocalDate dataInicio,
            LocalDate dataFim,
            Float valorMinimo) {
        List<Shop> shops = reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim){
        return reportRepository.getShopByDate(dataInicio, dataFim);
    }
}
