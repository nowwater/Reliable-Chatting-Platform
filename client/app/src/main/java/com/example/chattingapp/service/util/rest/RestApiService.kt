package com.example.chattingapp.service.util.rest

import com.example.chattingapp.dto.ChatRoom
import com.example.chattingapp.dto.Friend
import com.example.chattingapp.dto.Message
import com.example.chattingapp.dto.User
import com.example.chattingapp.dto.request.LoginRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface RestApiService {
    // User Api
    @GET("/api/user/all") fun getUsers() : Call<List<User>>
    @GET("/api/user/checkSession") fun checkSession() : Call<Int>
    @POST("/api/user/signup") fun signUp(@Body user:User) : Call<String>
    @POST("/api/user/login") fun signIn(@Body loginRequest: LoginRequest) : Call<User>
  
    @POST("/api/user/updateImage") fun updateImage(@Body base64Image : String) : Call<String>
    @POST("/api/user/updateStatus") fun updateStatus(@Query("statusMessage") statusMessage: String) : Call<User>
    @POST("/api/user/updateNickName") fun updateNickName(@Query("NickName")  nickName: String) : Call<User>
    @Multipart @POST("/api/user/uploadProfileImage") fun uploadProfileImage(@Part file : MultipartBody.Part) : Call<User>


    // Friend Api
    @GET("/api/friend/all") fun getFriends() : Call<List<Friend>>
    @POST("/api/friend/add") fun addFriend(@Query("to") toId : Int) : Call<String>
    @POST("/api/friend/delete/") fun deleteFriend(@Query("to") toId : Int) : Call<String>


    // Room Api
    @GET("/api/room/getRooms") fun getRooms() : Call<List<ChatRoom>>
    @GET("/api/room/{roomId}") fun getRoom(@Path("roomId") roomId : Int) : Call<ChatRoom>
    @POST("/api/room/create") fun createRoom(@Query("roomName") roomName : String) : Call<Int>
    @POST("/api/room/invite") fun inviteRoom(@Query("roomId") roomId : Int, @Query("toId") toId: Int) : Call<String>
    @POST("/api/room/out") fun outRoom(@Query("roomId") roomId: Int) : Call<String>

    @POST("/api/room/enter/{roomId}/{userId}") fun enterRoom(@Path("roomId") roomId: Int, @Path("userId") userId: Int) : Call<Int>
    @POST("/api/room/exit/{roomId}/{userId}") fun exitRoom(@Path("roomId") roomId: Int, @Path("userId") userId: Int) : Call<Int>

    // Message Api
    @GET("/api/room/message/{roomId}") fun getMessages(@Path("roomId") roomId : Int) : Call<List<Message>>

    companion object {
        val instance = RestApiServiceGenerator.createService(RestApiService::class.java)
    }
}