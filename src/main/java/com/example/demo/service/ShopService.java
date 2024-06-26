package com.example.demo.service;

import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ShopDTO;
import com.example.demo.model.DTOConverter;
import com.example.demo.model.shop.Shop;
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
    private final UserService userService;
    private final ProductService productService;

    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository.findAllByDateGreaterThanEqual(shopDTO.getDate());

        return shops
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public ShopDTO findById(Long productId) {
        Optional<Shop> shop = shopRepository.findById(productId);
        return shop.map(DTOConverter::convert).orElse(null);
    }

    public ShopDTO save(ShopDTO shopDTO, String key) {
        userService.getUserByCpfAndKey(shopDTO.getUserIdentifier(), key);
        validateProducts(shopDTO.getItems());

        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum)
        );
        Shop shop = DTOConverter.convert(shopDTO);
        shop.setDate(LocalDateTime.now());

        return DTOConverter.convert(shopRepository.save(shop));
    }

    private boolean validateProducts(List<ItemDTO> items) {
        for (ItemDTO item : items) {
            ProductDTO productDTO = productService
                    .getProductByIdentifier(
                            item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }

    public List<ShopDTO> getShopsByFilter(
            LocalDate dataInicio,
            LocalDate dataFim,
            Float valorMinimo) {
        List<Shop> shops = reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
        return reportRepository.getShopByDate(dataInicio, dataFim);
    }
}
