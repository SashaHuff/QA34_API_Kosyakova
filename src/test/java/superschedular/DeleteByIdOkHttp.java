package superschedular;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import dto.ResponseDeleteById;
import dtosuper.DateDto;
import dtosuper.RecordDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteByIdOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imh1ZmZAZ21haWwuY29tIn0.lFBnlLOhjstTaUUZBiA_I0-bC1x8ifQg4u-fwjNLg5Q";
    int id;

    @BeforeMethod
    public void preCondition() throws IOException {
        RecordDto recordDto = RecordDto.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder().dayOfMonth(24).dayOfWeek("We").month(8).year(2022).build())
                .hours(5)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Title")
                .type("Type")
                .totalSalary(5450)
                .wage(50)
                .build();

        RequestBody body = RequestBody.create(gson.toJson(recordDto), JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        RecordDto record = gson.fromJson(response.body().string(), RecordDto.class);
        id = record.getId();
    }



    @Test
    public void deleteByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record/" +id)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        ResponseDeleteById responseDeleteById = gson.fromJson(response.body().string(), ResponseDeleteById.class);
        Assert.assertEquals(responseDeleteById.getStatus(), "Record with id: "+id+" was deleted");

    }
    @Test
    public void deleteByIdRecordDoesntExist() throws IOException {
        int idNotExist =(int) (System.currentTimeMillis()/1000)%3600;
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record/"+idNotExist)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(response.code(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Record with id "+idNotExist+" doesn't exist!");

    }
}
