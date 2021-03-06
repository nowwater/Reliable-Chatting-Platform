package com.Gongdae9.joinroom.domain;


import com.Gongdae9.bookmark.domain.Bookmark;
import com.Gongdae9.room.domain.Room;
import com.Gongdae9.user.domain.User;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JoinRoom {

    @Id
    @GeneratedValue
    @Column(name="joinRoom_id")
    private Long joinRoomId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;

    @OneToMany(mappedBy = "joinRoom", cascade = CascadeType.ALL) // persist 전파
    private List<Bookmark> bookmarks;

    public JoinRoom(User user,Room room){
        setUser(user);
        setRoom(room);
    }

    // 연관관계 편의 메소드
    public void setUser(User user){
        if(this.user!=null){
            this.user.getJoinRooms().remove(this);
        }
        this.user=user;
        user.getJoinRooms().add(this);
    }

    public void setRoom(Room room){
        if(this.room!=null){
            this.room.getJoinRooms().remove(this);
        }
        this.room=room;
        room.getJoinRooms().add(this);
    }
}
