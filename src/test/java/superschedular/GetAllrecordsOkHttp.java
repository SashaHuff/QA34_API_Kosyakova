package superschedular;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import dto.GetAllContactsDto;
import dtosuper.GetAllRecordsDto;
import dtosuper.PeriodDto;
import dtosuper.RecordDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllrecordsOkHttp {
    Gson gson = new Gson();
    public  static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imh1ZmZAZ21haWwuY29tIn0.lFBnlLOhjstTaUUZBiA_I0-bC1x8ifQg4u-fwjNLg5Q";
    @Test
    public void getAllRecordsSuccess() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(8)
                .monthTo(9)
                .yearFrom(2021)
                .yearTo(2022)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);

        GetAllRecordsDto records = gson.fromJson(response.body().string(), GetAllRecordsDto.class);
        List<RecordDto> all = records.getRecords();
        for (RecordDto dto : all) {
            System.out.println(dto.toString());
            System.out.println("**************");
        }
    }
    @Test
    public void getAllRecordsWrongMonthPeriod() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(8)
                .monthTo(13)
                .yearFrom(2021)
                .yearTo(2022)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(response.code(), 400);
        Assert.assertEquals(errorDto.getMessage(),"Wrong month period! Month from and to need be in range 1-12");

        }
    @Test
    public void getAllRecordsWrongYearPeriod() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(8)
                .monthTo(9)
                .yearFrom(2010)
                .yearTo(2025)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(response.code(), 400);
        Assert.assertEquals(errorDto.getMessage(),"Wrong year period!Year from and to needs be in range currentYear - 2 years and currentYear + 2 years");

    }
    @Test
    public void getAllRecordsWrongToken() throws IOException {
        PeriodDto periodDto = PeriodDto.builder()
                .monthFrom(8)
                .monthTo(9)
                .yearFrom(2021)
                .yearTo(2022)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", "pampam")
                .build();
        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(response.code(), 401);
        Assert.assertEquals(errorDto.getMessage(),"Wrong authorization format");

    }
    }

