//package com.example.chattingapp.adapter
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Filterable
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.chattingapp.R
//import com.example.chattingapp.dto.ChatRoom
//import com.example.chattingapp.dto.Message
//import com.example.chattingapp.dto.User
//import com.example.chattingapp.view.MessageChatActivity
//import java.lang.IllegalArgumentException
//import java.util.*
//import java.util.logging.Logger
//import kotlin.collections.ArrayList
//
//// Main Chatlist type Adapter
//class RoomlistAdapter(val context: Context,  val user : User, val activity: Activity) :
//    RecyclerView.Adapter<RoomlistAdapter.Holder>(), Filterable {
//    private val logger = Logger.getLogger(RoomlistAdapter::class.java.name)
//
//    private val roomPositionTable = HashMap<Int,Int>()
//    private var roomDataset: ArrayList<ChatRoom> = ArrayList()
//    private lateinit var showDataset : ArrayList<ChatRoom>
//
//    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
//        var roomId = -1
//        val roomnameText = itemView?.findViewById<TextView>(R.id.roomname)
//        val curmessageText = itemView?.findViewById<TextView>(R.id.curmessage)
//        val recenttimeText = itemView?.findViewById<TextView>(R.id.recenttime)
//
//        fun bind(chatroom: ChatRoom, context: Context) {
//            roomId = chatroom.roomId
//            roomnameText?.text = chatroom.roomName
//            curmessageText?.text = chatroom.curMessage
//            recenttimeText?.text = chatroom.recentTime
//        }
//
//        fun bind(curMessage:String, time:String){
//            curmessageText?.text = curMessage
//            recenttimeText?.text = time
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = LayoutInflater.from(context).inflate(R.layout.widget_roomlist_item, parent, false)
//        return Holder(view)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder?.bind(showDataset?.get(position)!!, context)
//        holder?.itemView.setOnClickListener{
//            val intent = Intent(activity, MessageChatActivity::class.java)
//            intent.putExtra("user", user)
//            intent.putExtra("room", showDataset?.get(position)!!)
//
//            context.startActivity(intent)
//        }
//    }
//
//    // this used for data set changed
//    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
//        if(payloads.isEmpty()){
//            onBindViewHolder(holder,position)
//            return;
//        }
//
//        val message = payloads.get(0) as Message
//        holder?.bind(message.content, message.writtenAt)
//    }
//
//    override fun getItemCount(): Int {
//        return showDataset?.size!!
//    }
//
//    fun notifyItemChangedBy(roomId:Int, message:Message){
//        if(!roomPositionTable.containsKey(roomId))
//            throw IllegalArgumentException("adapter does not have roomId")
//        notifyItemChanged(roomPositionTable.get(roomId)!!, message)
//    }
//
//    fun setRooms(newrooms : List<ChatRoom>){
//        this.roomDataset?.clear()
//        this.roomDataset = newrooms as ArrayList<ChatRoom>
//        for(i in 0 until roomDataset?.size!!){
//            roomPositionTable[roomDataset?.get(i)!!.roomId] = i
//        }
//        notifyDataSetChanged()
//    }
//
//    override fun getFilter(): android.widget.Filter {
//        return object : android.widget.Filter() {
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                val charString = constraint.toString()
//                var filteredList : ArrayList<ChatRoom>
//                if (charString.isEmpty()) {
//                    filteredList = roomDataset
//                } else {
//                    filteredList = ArrayList()
//                    for (room in roomDataset) {
//                        Log.e("room!!", room.toString())
//                        if(room.roomName.toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(room);
//                        }
//
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//                Log.e("filterResults", filterResults.values.toString())
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence, results: FilterResults) {
////                roomDataset  = results.values as ArrayList<ChatRoom>
////                notifyDataSetChanged()
//            }
//        }
//    }
//}

package com.example.chattingapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingapp.R
import com.example.chattingapp.dto.ChatRoom
import com.example.chattingapp.dto.Message
import com.example.chattingapp.dto.User
import com.example.chattingapp.view.MessageChatActivity
import java.lang.IllegalArgumentException
import java.util.*
import java.util.logging.Logger

// Main Chatlist type Adapter
class RoomlistAdapter(val context: Context,  val user : User, val activity: Activity) : RecyclerView.Adapter<RoomlistAdapter.Holder>() {
    private val logger = Logger.getLogger(RoomlistAdapter::class.java.name)

    private val roomPositionTable = HashMap<Int,Int>()
    private var roomList = ArrayList<ChatRoom>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var roomId = -1
        val roomnameText = itemView?.findViewById<TextView>(R.id.roomname)
        val curmessageText = itemView?.findViewById<TextView>(R.id.curmessage)
        val recenttimeText = itemView?.findViewById<TextView>(R.id.recenttime)

        fun bind(chatroom: ChatRoom, context: Context) {
            roomId = chatroom.roomId
            roomnameText?.text = chatroom.roomName
            curmessageText?.text = chatroom.curMessage
            recenttimeText?.text = chatroom.recentTime
        }

        fun bind(curMessage:String, time:String){
            curmessageText?.text = curMessage
            recenttimeText?.text = time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.widget_roomlist_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(roomList[position], context)
        holder?.itemView.setOnClickListener{
            val intent = Intent(activity, MessageChatActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("room", roomList[position])

            context.startActivity(intent)
        }
    }

    // this used for data set changed
    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position)
            return;
        }

        val message = payloads.get(0) as Message
        holder?.bind(message.content, message.writtenAt)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    fun notifyItemChangedBy(roomId:Int, message:Message){
        if(!roomPositionTable.containsKey(roomId))
            throw IllegalArgumentException("adapter does not have roomId")
        notifyItemChanged(roomPositionTable.get(roomId)!!, message)
    }

    fun setRooms(rooms : List<ChatRoom>){
        this.roomList.clear()
        this.roomList = rooms as ArrayList<ChatRoom>
        for(i in 0 until roomList.size){
            roomPositionTable[roomList[i].roomId] = i
        }
        notifyDataSetChanged()
    }
}