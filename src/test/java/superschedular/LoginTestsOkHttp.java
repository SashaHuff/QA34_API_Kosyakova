package superschedular;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkHttp {

        Gson gson = new Gson();
        public  static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        @Test
        public void loginSuccess() throws IOException {
            AuthRequestDto auth = AuthRequestDto.builder().email("huff@gmail.com").password("Hhuff1234$").build();

            RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

            Request request = new Request.Builder()
                    .url("https://super-scheduler-app.herokuapp.com/api/login")
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            Assert.assertTrue(response.isSuccessful());
            Assert.assertEquals(response.code(),200);
            AuthResponseDto responseDto = gson.fromJson(response.body().string(),AuthResponseDto.class);
            System.out.println(responseDto.getToken());
        }
    @Test
    public void loginWrongEmail() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("H@gmailom").password("Hhuff1234$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(response.code(),401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email or password"));// bug, user can register with wrong Email expected [401] but found [200]
    }
    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("huff@gmail.com").password("1flo").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(response.code(),401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email or password"));
    }
}
