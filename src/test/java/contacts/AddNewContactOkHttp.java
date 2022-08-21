package contacts;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IlRlc3QxMjM0QGdtYWlsLmNvbSJ9._JPdcwrf7JM2le_lU8v5b2fx6lTao2NdqOzg-ZrNzS0
public class AddNewContactOkHttp {

    Gson gson = new Gson();
    public  static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IlRlc3QxMjM0QGdtYWlsLmNvbSJ9._JPdcwrf7JM2le_lU8v5b2fx6lTao2NdqOzg-ZrNzS0";

    @Test
    public void addNewContactSuccess() throws IOException {
        int i = (int) (System.currentTimeMillis()/1000)%3600;
        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Snow")
                .email("snow"+i+"@gmail.com")
                .address("LA")
                .description("hw")
                .phone("658248215"+i)
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        ContactDto contact = gson.fromJson(response.body().string(),ContactDto.class);
        System.out.println(contact.getId());
        System.out.println(contact.getEmail());
        Assert.assertEquals(contactDto.getName(),contact.getName());
        Assert.assertEquals(contactDto.getEmail(),contact.getEmail());
        Assert.assertNotEquals(contactDto.getId(),contact.getId());

    }
    @Test
    public void addNewContactWrongName() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .lastName("Snow")
                .email("snow@gmail.com")
                .address("LA")
                .description("hw")
                .phone("658248215")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Wrong contact format! Name can't be empty!");

    }
    @Test
    public void addNewContactWrongAuth() throws IOException {

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Snow")
                .email("snow@gmail.com")
                .address("LA")
                .description("hw")
                .phone("658248215")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization","pampam")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());


        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Wrong token format!");
        Assert.assertEquals(response.code(),401); //recieve 500
    }
    //snow2127@gmail.com
    @Test
    public void addNewContactDuplicate() throws IOException {

        ContactDto contactDto = ContactDto.builder()
                .name("John")
                .lastName("Snow")
                .email("snow2127@gmail.com")
                .address("LA")
                .description("hw")
                .phone("658248215")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful()); // we can add contact, bug


        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Wrong token format!");
        Assert.assertEquals(response.code(),409);
    }
}
