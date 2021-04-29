package com.example.chattingapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingapp.R
import com.example.chattingapp.adapter.FriendlistAdapter
import com.example.chattingapp.adapter.MessagelistAdapter
import com.example.chattingapp.adapter.RoomlistAdapter
import com.example.chattingapp.dto.ChatRoom
import com.example.chattingapp.dto.User
import com.example.chattingapp.service.RoomApiService
import com.example.chattingapp.service.UserApiService
import com.example.chattingapp.view.MessageChatActivity
import kotlinx.android.synthetic.main.fragment_roomlist.*

class RoomlistFragment(val user : User) : Fragment() {

    var roomlist = ArrayList<ChatRoom>()  //temporary array

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roomlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RoomlistAdapter(requireContext(), roomlist){ view:View, chatRoom:ChatRoom ->
            val intent = Intent(activity, MessageChatActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("room", chatRoom)

            startActivity(intent)
        }

        recyclerRoomlist.adapter = adapter
        recyclerRoomlist.layoutManager = LinearLayoutManager(requireContext())
        recyclerRoomlist.setHasFixedSize(true)

        RoomApiService.instance.getRooms(){
            adapter.addItem(it)
        }
    }
}
