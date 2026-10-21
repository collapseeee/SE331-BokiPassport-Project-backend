package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.bokipassport.entity.Benefit;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LevelDTO {
    Long id;
    String name;
    String description;
    Double passCriteria;

    List<BenefitDTO> benefits;
}
