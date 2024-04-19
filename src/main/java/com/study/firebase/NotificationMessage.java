package com.study.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {

    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String,String> data;
}
