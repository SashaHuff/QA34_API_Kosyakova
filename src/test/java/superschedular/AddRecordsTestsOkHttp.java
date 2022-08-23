package superschedular;

import com.google.gson.Gson;
import dtosuper.DateDto;
import dtosuper.RecordDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

//
public class AddRecordsTestsOkHttp {
    Gson gson = new Gson();
    public  static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imh1ZmZAZ21haWwuY29tIn0.lFBnlLOhjstTaUUZBiA_I0-bC1x8ifQg4u-fwjNLg5Q";
    @Test
    public void recordSuccess() throws IOException {
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

        RequestBody body = RequestBody.create(gson.toJson(recordDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        RecordDto record = gson.fromJson(response.body().string(),RecordDto.class);
        System.out.println(record.getId());
    }
    @Test
    public void recordUnSuccess() throws IOException {
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

        RequestBody body = RequestBody.create(gson.toJson(recordDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization","pampam")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

        RecordDto record = gson.fromJson(response.body().string(),RecordDto.class);
        System.out.println(record.getId());
    }

}
