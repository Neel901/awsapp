package com.example.awsapplication.data;

import lombok.Data;

@Data
public class DbSecret {
    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbInstanceIdentifier;
}

