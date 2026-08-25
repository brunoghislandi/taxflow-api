package br.com.test.taxflow_api.mapper;

import br.com.test.taxflow_api.domain.Tributo;
import br.com.test.taxflow_api.dto.TributoPatchDTO;
import br.com.test.taxflow_api.dto.TributoRequestDTO;
import br.com.test.taxflow_api.dto.TributoResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TributoMapper {

    Tributo toEntity(TributoRequestDTO dto);

    TributoResponseDTO toResponseDTO(Tributo tributo);

    void atualizar(TributoRequestDTO dto, @MappingTarget Tributo entidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarParcial(TributoPatchDTO dto, @MappingTarget Tributo entidade);
}