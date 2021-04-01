package com.Gongdae9.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Room {

    @Id
    @GeneratedValue
    @Column(name="room_id")
    private Long roomId;

    private String roomName;
    private String state;

    @OneToMany(mappedBy="room")
    private List<JoinRoom> joinRooms = new ArrayList<JoinRoom>();





}