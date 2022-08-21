package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
"address": "string",
  "description": "string",
  "email": "string",
  "id": 0,
  "lastName": "string",
  "name": "string",
  "phone": "string"
 */
@Setter
@Getter
@ToString
@Builder

public class ContactDto {
    private String address;
    private String description;
    private String email;
    private String lastName;
    private String name;
    private String phone;
    private int id;
}
