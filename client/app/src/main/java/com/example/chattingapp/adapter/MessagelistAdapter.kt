package com.example.chattingapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chattingapp.R
import com.example.chattingapp.dto.Message
import com.example.chattingapp.view.AddBookmarkActivity
import kotlinx.android.synthetic.main.fragment_friendlist.*
import kotlinx.android.synthetic.main.widget_chat_company.view.*

class MessagelistAdapter(val context: Context, val userId: Int, val roomId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val MY_CHATTING = 0
        val OTHER_CHATTING = 1
        val ENTER = 2
    }

    private var messages = ArrayList<Message>()
    private val messageIdToIdx = HashMap<Int, Int>()

    inner class MyChatHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val messageView: TextView =
            itemView?.findViewById<TextView>(R.id.item_chat_company_content)!!
        val timeView: TextView = itemView?.findViewById<TextView>(R.id.item_chat_company_date)!!

        fun bind(message: Message, context: Context) {
            messageView.text = message.content
            timeView.text = message.writtenAt

            itemView.findViewById<TextView>(R.id.item_chat_company_content).setOnClickListener {
                if (itemView.findViewById<TextView>(R.id.my_bookmark_add_button).visibility == View.VISIBLE) {
                    itemView.findViewById<TextView>(R.id.my_bookmark_add_button).visibility =
                        View.INVISIBLE
                }
            }   // 한 번 더 짧게 클릭 시 북마크 추가 버튼 없어짐

            itemView.findViewById<TextView>(R.id.item_chat_company_content).setOnLongClickListener(
                object : View.OnLongClickListener {
                    override fun onLongClick(v: View?): Boolean {
                        itemView.findViewById<TextView>(R.id.my_bookmark_add_button).visibility =
                            View.VISIBLE

                        itemView.findViewById<TextView>(R.id.my_bookmark_add_button).setOnClickListener {

                            val intent = Intent(v?.context, AddBookmarkActivity::class.java)
                            intent.putExtra("msgContent", itemView.findViewById<TextView>(R.id.item_chat_company_content).text.toString())
                            intent.putExtra("roomId", roomId)

                            v?.context?.startActivity(intent)

                            itemView.findViewById<TextView>(R.id.my_bookmark_add_button).visibility =
                                View.INVISIBLE
                        }   //bookmark 추가 버튼 클릭 시 액티비티 전환

                        return true;
                    }
                }
            ) // each message long click listener

        }
    }

    inner class OtherChatHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val messageView: TextView =
            itemView?.findViewById<TextView>(R.id.item_chat_customer_content)!!
        val timeView: TextView = itemView?.findViewById<TextView>(R.id.item_chat_customer_date)!!
        val nicknameView: TextView = itemView?.findViewById(R.id.item_chat_customer_nickname)!!
        val profileImageView : ImageView = itemView?.findViewById<ImageView>(R.id.item_chat_customer_image)!!

        fun bind(message: Message, context: Context) {
            messageView.text = message.content
            timeView.text = message.writtenAt
            nicknameView.text = message.writtenBy

            itemView.findViewById<TextView>(R.id.item_chat_customer_content).setOnClickListener {
                if (itemView.findViewById<TextView>(R.id.customer_bookmark_add_button).visibility == View.VISIBLE) {
                    itemView.findViewById<TextView>(R.id.customer_bookmark_add_button).visibility =
                        View.INVISIBLE
                }
            }   // 한 번 더 짧게 클릭 시 북마크 추가 버튼 없어짐

            itemView.findViewById<TextView>(R.id.item_chat_customer_content).setOnLongClickListener(
                object : View.OnLongClickListener {
                    override fun onLongClick(v: View?): Boolean {
                        itemView.findViewById<TextView>(R.id.customer_bookmark_add_button).visibility =
                            View.VISIBLE

                        itemView.findViewById<TextView>(R.id.customer_bookmark_add_button).setOnClickListener {

                            val intent = Intent(v?.context, AddBookmarkActivity::class.java)
                            intent.putExtra("msgContent", itemView.findViewById<TextView>(R.id.item_chat_customer_content).text.toString())
                            intent.putExtra("roomId", roomId)

                            v?.context?.startActivity(intent)

                            itemView.findViewById<TextView>(R.id.customer_bookmark_add_button).visibility =
                                View.INVISIBLE
                        }   //bookmark 추가 버튼 클릭 시 액티비티 전환

                        return true;
                    }
                }
            ) // each message long click listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MY_CHATTING) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.widget_chat_company, parent, false)
            return MyChatHolder(view)
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.widget_chat_customer, parent, false)
        return OtherChatHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyChatHolder) {
            holder.bind(this.messages[position], context)
        } else if (holder is OtherChatHolder) {
            holder.bind(this.messages[position], context)

            messages[position].profileImage?. let {Log.e(" ", messages[position].profileImage!!)
                Glide.with(holder.itemView.context).load(messages[position].profileImage).override(10,10).centerCrop().into(holder.profileImageView)
            }
        }
    }


    override fun getItemCount(): Int {
        return this.messages.size;
    }

    override fun getItemViewType(position: Int): Int {
        if (userId == this.messages[position].userId) {
            return MY_CHATTING
        }
        return OTHER_CHATTING
    }
//
//    private fun addItem(message : Message){
//        this.messages.add(message)
//        notifyItemInserted(this.messages.size-1);
//    }

    private fun addItems(messages: List<Message>) {
        this.messages.addAll(messages)
        for (i in 0 until messages.size)
            messageIdToIdx[messages[i].messageId] = i
    }

    private fun clearData() {
        this.messages.clear()
        this.messageIdToIdx.clear()
    }

    fun getIdx(messageId: Int): Int? {
        return messageIdToIdx[messageId]
    }

    fun setMessages(messages: List<Message>) {
        clearData()
        addItems(messages)
        notifyDataSetChanged()
    }
}