package com.firstquad.stock.controller.mapper;

import com.firstquad.stock.controller.dto.StockDto;
import com.firstquad.stock.domain.StockCalculation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockDto toDto(StockCalculation subscription);

    List<StockDto> toDto(List<StockCalculation> subscription);

}
