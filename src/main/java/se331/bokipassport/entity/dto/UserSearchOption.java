package se331.bokipassport.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchOption {
    String name;
    String province;
    Long level;
}
