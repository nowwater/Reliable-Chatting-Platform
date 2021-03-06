package com.example.chattingapp.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chattingapp.R
import com.example.chattingapp.dto.Friend
import com.example.chattingapp.view.MessageChatActivity
import com.example.chattingapp.view.ProfileActivity

// Main Userlist type Adapter
class FriendlistAdapter(val context: Context, var friendList: ArrayList<Friend>) :
    RecyclerView.Adapter<FriendlistAdapter.Holder>(), Filterable {
    private var friends: ArrayList<Friend>? = friendList

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val nameText = itemView?.findViewById<TextView>(R.id.nameText)
        val statusText = itemView?.findViewById<TextView>(R.id.statusText)
        val profileImage = itemView?.findViewById<ImageView>(R.id.friend_image)

        fun bind(friend: Friend, context: Context) {
            nameText?.text = friend.nickName
            statusText?.text = friend.statusMessage

            friend.profileImage?.let {
                if (profileImage != null) {
                    Glide.with(context).load(it).into(profileImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.widget_friendlist_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(friends?.get(position)!!, context)
        holder?.itemView.setOnClickListener{
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("friend", friends?.get(position))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return friends?.size!!
    }

    fun addItem(friend: Friend){
        friends?.add(friend)
        notifyItemInserted(friends?.size!!-1);
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                friends = if (charString.isEmpty()) {
                    friendList
                } else {
                    val filteredList = ArrayList<Friend>()
                    if (friendList != null) {
                        for (friend in friendList) {
                            if(friend.name.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(friend);
                            }
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = friends
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                friends  = results.values as ArrayList<Friend>
                notifyDataSetChanged()
            }
        }
    }
}