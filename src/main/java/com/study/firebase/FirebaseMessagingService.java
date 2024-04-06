package com.study.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    /*public String sendNotificationByToken(NotificationMessage notificationMessage){

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
    }*/

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
}
