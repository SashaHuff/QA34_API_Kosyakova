package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
"date": {
    "dayOfMonth": 0,
    "dayOfWeek": "string",
    "month": 0,
    "year": 0
  }
 */
@Setter
@Getter
@ToString
@Builder
public class DateDto {
    private int dayOfMonth;
    private String dayOfWeek;
    private int month;
    private int year;
}
