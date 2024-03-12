package com.example.demo.model.shop;

import com.example.demo.model.item.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
    @NotBlank
    private String userIdentifier;
    @NotNull
    private Float total;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private List<ItemDTO> items;

    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        shopDTO.setDate(shop.getDate());
        return shopDTO;
    }
}
