package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
/*
"monthFrom": 0,
  "monthTo": 0,
  "yearFrom": 0,
  "yearTo": 0
 */
public class PeriodDto {
    private int monthFrom;
    private int monthTo;
    private int yearFrom;
    private int yearTo;
}
