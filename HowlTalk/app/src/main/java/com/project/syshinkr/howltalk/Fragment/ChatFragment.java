package com.project.syshinkr.howltalk.Fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.syshinkr.howltalk.Chat.GroupMessageActivity;
import com.project.syshinkr.howltalk.Chat.MessageActivity;
import com.project.syshinkr.howltalk.Model.ChatModel;
import com.project.syshinkr.howltalk.Model.UserModel;
import com.project.syshinkr.howltalk.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class ChatFragment extends Fragment {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.chatFragment_recyclerview);
        recyclerView.setAdapter(new ChatRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        return view;
    }

    class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ChatModel> chatModels = new ArrayList<>();
        private List<String> keys = new ArrayList<>();//방에 대한 키 담기
        private String uid;
        private ArrayList<String> destinationUsers = new ArrayList<>();

        public ChatRecyclerViewAdapter() {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/" + uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    chatModels.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        chatModels.add(snapshot.getValue(ChatModel.class));
                        keys.add(snapshot.getKey());
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            final CustomViewHolder customViewHolder = ((CustomViewHolder) holder);
            String destinationUid = null;

            //챗방에 있는 유저 체크
            for (String user : chatModels.get(position).users.keySet()) {
                if (!user.equals(uid)) { //내가 아닌 사람
                    destinationUid = user;
                    destinationUsers.add(destinationUid);
                }
            }
            FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    Glide.with(customViewHolder.itemView.getContext())
                            .load(userModel.profileImageUrl)
                            .apply(new RequestOptions().circleCrop())
                            .into(customViewHolder.imageView);

                    customViewHolder.textview_title.setText(userModel.userName);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //메시지를 내림차순으로 정렬 후 마지막 메시지의 키값을 가져옴
            Map<String, ChatModel.Comment> commentMap = new TreeMap<>(Collections.<String>reverseOrder()); //내림차순
            commentMap.putAll(chatModels.get(position).comments);

            //단체채팅방을 만들었을 땐 메시지가 하나도 없음에도 불구하고 받으려하면 에러남
            if (commentMap.keySet().toArray().length > 0) {
                String lastMessageKey = (String) commentMap.keySet().toArray()[0];
                customViewHolder.textView_last_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);

                //timestamp
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                long unixTime = (long) chatModels.get(position).comments.get(lastMessageKey).timeStamp;
                Date date = new Date(unixTime);
                customViewHolder.textView_timeStamp.setText(simpleDateFormat.format(date));
            }
            customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if(chatModels.get(position).users.size() > 2) {
                        intent = new Intent(v.getContext(), GroupMessageActivity.class);
                        intent.putExtra("destinationRoom", keys.get(position));
                    } else {
                        intent = new Intent(v.getContext(), MessageActivity.class);
                        intent.putExtra("destinationUid", destinationUsers.get(position));
                    }

                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(), R.anim.from_right, R.anim.to_left);
                    startActivity(intent, activityOptions.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return chatModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textview_title;
            public TextView textView_last_message;
            public TextView textView_timeStamp;

            public CustomViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.chatItem_imageview);
                textview_title = view.findViewById(R.id.chatItem_textview_title);
                textView_last_message = view.findViewById(R.id.chatitem_textview_lastMessage);
                textView_timeStamp = view.findViewById(R.id.chatitem_textview_timestamp);
            }
        }
    }
}
