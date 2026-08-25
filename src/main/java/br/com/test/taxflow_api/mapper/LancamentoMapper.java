package br.com.test.taxflow_api.mapper;

import br.com.test.taxflow_api.domain.Lancamento;
import br.com.test.taxflow_api.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LancamentoMapper {
    @Mapping(target = "tributo", ignore = true)
    Lancamento toEntity(LancamentoRequestDTO dto);

    @Mapping(target = "tributoId", source = "tributo.id")
    LancamentoResponseDTO toResponseDTO(Lancamento lancamento);

    void atualizar(LancamentoRequestDTO dto, @MappingTarget Lancamento entidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarParcial(LancamentoPatchDTO dto, @MappingTarget Lancamento entidade);
}