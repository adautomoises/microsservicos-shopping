package com.example.demo.controller;

import com.example.demo.dto.ShopDTO;
import com.example.demo.model.shop.ShopReportDTO;
import com.example.demo.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping
    public List<ShopDTO> getShops() {
        return shopService.getAll();
    }

    @GetMapping("/shopByUser/{userIdentifier}")
    public List<ShopDTO> getShops(@PathVariable String userIdentifier) {
        return shopService.getByUser(userIdentifier);
    }

    @GetMapping("/shopByDate")
    public List<ShopDTO> getShops(@RequestBody @Valid ShopDTO shopDTO) {
        return shopService.getByDate(shopDTO);
    }

    @GetMapping("/{id}")
    public ShopDTO findById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDTO newShop(@RequestBody ShopDTO shopDTO) {
        return shopService.save(shopDTO);
    }

    @GetMapping("/search")
    public List<ShopDTO> getShopsByFilter( @RequestParam(name = "dataInicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam(name = "dataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim,
            @RequestParam(name = "valorMinimo", required = false) Float valorMinimo ) {
        return shopService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
    }

    @GetMapping("/report")
    public ShopReportDTO getReportByDate(@RequestParam(name = "dataInicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam(name = "dataFim") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim ) {
        return shopService.getReportByDate(dataInicio, dataFim);
    }
}
