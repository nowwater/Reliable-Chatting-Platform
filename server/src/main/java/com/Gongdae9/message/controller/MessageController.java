package com.Gongdae9.message.controller;


import com.Gongdae9.fcm.service.FCMService;
import com.Gongdae9.message.domain.EventSubDto;
import com.Gongdae9.message.domain.Message;
import com.Gongdae9.message.domain.MessageDto;
import com.Gongdae9.message.service.MessageService;
import com.Gongdae9.room.service.RoomSessionService;
import com.Gongdae9.room.dto.ChattingUserDto;
import com.Gongdae9.room.service.RoomService;
import com.Gongdae9.user.domain.User;
import com.Gongdae9.user.repository.UserRepository;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final RoomService roomService;
    private final UserRepository userRepository;
    private final FCMService fcmService;

    private final RoomSessionService roomSessionService;


    @MessageMapping("/chat/message/{userId}/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public MessageDto greeting(@DestinationVariable("userId") Long userId,@DestinationVariable("roomId") Long roomId, @Payload String content) throws Exception {
        Thread.sleep(100); // delay

        User sender = userRepository.findById(userId);
        String ncontent=content.substring(0,content.length()-2);
        Message message = messageService.createMessage(userId, roomId, ncontent);
        message = messageService.save(message);

        List<ChattingUserDto> chattingUsers = roomService.getChattingUser(roomId);
        List<Long> userIdList = chattingUsers.stream()
            .filter(o -> !o.getUserId().equals(userId))
            .map(o->o.getUserId())
            .collect(Collectors.toList());

        List<Long> currentJoin = userIdList.stream().filter(id -> !roomSessionService.isJoin(roomId, id)).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for(long i : currentJoin){
            sb.append(i);
            sb.append("  ");
        }
        log.info("current not enter list : " + sb);
        List<String> fcmToken = userRepository.findFCMToken(currentJoin);


        fcmToken.forEach(o-> {
            try {
                fcmService.send(o,sender.getNickName(),ncontent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });


        MessageDto messageDto = new MessageDto(message);
        return messageDto;
    }

    @MessageMapping("/event/sub/{fromId}/{toId}")
    @SendTo("/sub/{toId}")
    public EventSubDto greeting(@DestinationVariable("fromId") Long fromId,@DestinationVariable("toId") Long toId,@Payload EventSubDto event) throws Exception {
        Thread.sleep(100); // delay
        return event;
    }


    /*
    1. 유저가 웹소켓 연결을 진행할때 roomID를 restApi를 통해서 roomID들을 다 받은 후에 구독을 진행한다
    2. 방을 생성할 때, 유저는 roomID를 restApi를 통해서 받고 roomID을 구독을 진행한다.
    3. 친구를 추가할 때, 친구를 추가하는 유저는 event/sub을 서버에게 보내고 서버는 userId에게 어떠한 메세지를 보낸다.
    ( 상대방 클라이언트가 웹소켓에 연결이 되어있으면 이 메세지를 받으면서 자동으로 구독을 진행할 것 이다.
    다만, 상대방 클라이언트가 웹소켓에 연결이 되어있지않으면 어떻게 처리할것인가? => 상대방은 웹소켓을 연결하러 들어올때 어차피 1번에서 roomID들을 다 받으면서 구독을 진행할 것이다 ? )

    4. 클라이언트쪽에서 채팅을 보냈을때 상대방이 웹소켓에 연결이되어있다면 그냥 채팅리스트에 업데이트가 될 것이다.
    다만 상대방 클라이언트가 웹소켓에 연결이 되어있지 않으면?? => 서버에서 상대방이 웹소켓에 연결이 될때 자동으로 메세지를 뿌려주어야하나? 이것을 어떻게 개발할것인가?
     */
}