package com.Gongdae9.joinroom.api;


import com.Gongdae9.joinroom.service.JoinRoomService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinRoomApiController {

    private final JoinRoomService joinRoomService;

    @PostMapping("/api/room/create")
    public Long createRoom(String roomName, HttpServletRequest req){
        long userId = (Long)req.getSession().getAttribute("userId");
        return joinRoomService.createRoom(userId,roomName);
    }



    @PostMapping("/api/room/invite")
    public boolean inviteRoom(HttpServletRequest req,Long toId,Long roomId){
        long fromId= (Long)req.getSession().getAttribute("userId");
        return joinRoomService.inviteRoom(fromId,toId,roomId);
    }

    @PostMapping("/api/room/out")
    public boolean outRoom(HttpServletRequest req,Long roomId){
        long fromId= (Long)req.getSession().getAttribute("userId");
        return joinRoomService.outRoom(fromId,roomId);
    }
}
