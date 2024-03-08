package com.adria.dbencryption.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WSUserDetails {

    private String id;

    private String emailId;

    private String mobileNumber;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

}
