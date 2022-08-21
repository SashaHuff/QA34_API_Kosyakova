package dtosuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 "breaks": 0,
  "currency": "string",
  "date": {
    "dayOfMonth": 0,
    "dayOfWeek": "string",
    "month": 0,
    "year": 0
  },
  "hours": 0,
  "id": 0,
  "timeFrom": "string",
  "timeTo": "string",
  "title": "string",
  "totalSalary": 0,
  "type": "string",
  "wage": 0
 */
@Setter
@Getter
@ToString
@Builder
public class RecordDto {
    private int breaks;
    private String currency;
    private DateDto date;
    private int hours;
    private int id;
    private String timeFrom;
    private String timeTo;
    private String title;
    private int totalSalary;
    private String type;
    private int wage;
}
