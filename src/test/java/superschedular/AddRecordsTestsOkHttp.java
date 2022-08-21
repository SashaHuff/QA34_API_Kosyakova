package superschedular;

import dtosuper.DateDto;
import dtosuper.RecordDto;
import org.testng.annotations.Test;

public class AddRecordsTestsOkHttp {
    @Test
    public void recordSuccess(){
        RecordDto recordDto = RecordDto.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder().dayOfMonth(24).dayOfWeek("We").month(8).year(2022).build())
                .hours(5)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Title")
                .type("Type")
                .totalSalary(500)
                .wage(50)
                .build();
    }
}
