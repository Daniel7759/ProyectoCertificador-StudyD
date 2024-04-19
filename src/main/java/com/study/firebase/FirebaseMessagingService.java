package com.study.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotificationByToken(NotificationMessage notificationMessage){

        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage())
                .build();

        Message message = Message
                .builder()
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "Succes sending notifcation";
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
            return "Failed to send message";
        }
    }

    public String sendNotifyByTopic(NotificationMessage notificationMessage){
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage())
                .build();

        Message message = Message
                .builder()
                .setTopic("cursos")
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "Succes sending notifcation by topic";
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
            return "Failed to send message by topic";
        }
    }

    public String sendNotificationByTokens(NotificationMessage notificationMessage, Set<String> tokens){
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage())
                .build();

        String response = "";
        for(String token : tokens){
            Message message = Message
                    .builder()
                    .setNotification(notification)
                    .setToken(token)
                    .build();

            try {
                firebaseMessaging.send(message);
                response += "Succes sending notification to token: " + token + "\n";
            }catch (FirebaseMessagingException e){
                e.printStackTrace();
                response += "Failed to send message to token: " + token + "\n";
            }
        }
        return response;
    }
}
