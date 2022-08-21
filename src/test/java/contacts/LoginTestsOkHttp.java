package contacts;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IlRlc3QxMjM0QGdtYWlsLmNvbSJ9._JPdcwrf7JM2le_lU8v5b2fx6lTao2NdqOzg-ZrNzS0
public class LoginTestsOkHttp {

    Gson gson = new Gson();
    public  static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("Test1234@gmail.com").password("Ttest1234$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        AuthResponseDto responseDto = gson.fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }
    @Test
    public void loginUnSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("Ttest1234@gmail.com").password("Ttest1234").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(),400);
        Assert.assertTrue(errorDto.getMessage().contains("Password"));
    }
    @Test
    public void loginUnSuccess1() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("Test123gmail.com").password("Ttest123$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(),400);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format! Example: name@mail.com"));
    }
    @Test
    public void loginUnRegisteredUser() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("qwerty@gmail.com").password("Qqwerty1234$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Wrong email or password!");
        Assert.assertEquals(errorDto.getCode(),401);
    }


}
