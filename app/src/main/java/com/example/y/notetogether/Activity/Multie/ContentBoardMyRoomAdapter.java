package com.example.y.notetogether.Activity.Multie;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.y.notetogether.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by kimwoohyeun on 2016-05-27.
 */
public class ContentBoardMyRoomAdapter extends RecyclerView.Adapter<ContentBoardMyRoomAdapter.ViewHolder> implements Checkable {
    private Context context;
    private int fullHeight;
    CardView precardview = null;
    public static ArrayList<RoomContents> contentList;
    private ArrayList<RoomContents> contentListsearch;//검색기능을 위한 리스트공간 생성
    int item_layout;
    private String title = null;
    public ContentBoardMyRoomAdapter(Context context, ArrayList<RoomContents> contentList, int item_layout) {
        this.contentList = contentList;
        this.context = context;
        this.item_layout = item_layout;
        //검색기능때문에 아래 2줄 추가
        this.contentListsearch = new ArrayList<RoomContents>();
        this.contentListsearch.addAll(contentList);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contentboard_room, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContentBoardMyRoomAdapter.ViewHolder holder, final int position) {
        final RoomContents item = contentList.get(position);
        //메모 목록 시각화
        holder.title.setText(item.getTitle());
        holder.count.setText(""+item.getCount());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(item.getColor()==false) {
                if(precardview==null) {
                    title = item.getTitle();
                    holder.cardview.setCardBackgroundColor(Color.parseColor("#FF9800"));
                    precardview = holder.cardview;
                }
                else{
                    precardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.cardview.setCardBackgroundColor(Color.parseColor("#FF9800"));
                    title = item.getTitle();
                    precardview = holder.cardview;
                }
            }
        });
        //시각화
        setPreDraw(holder);
    }



    @Override
    public int getItemCount() {
        return this.contentList.size();
    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
    //방 정보가져오기
    public String gettitle(){
        return this.title;
    }
    //검색 기능
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        contentList.clear();
        if(charText.length()==0){
            contentList.addAll(contentListsearch);
        }
        else {
            for(RoomContents wp : contentListsearch){
                if(wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)){
                    contentList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        TextView title;
        TextView count;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.room_cardview);
            title = (TextView) itemView.findViewById(R.id.textView_contentBoard_room_title);
            count = (TextView) itemView.findViewById(R.id.textView_contentBoard_room_count);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.Relative_room_color);
        }
    }
    private void setPreDraw(final ViewHolder holder) {
        holder.cardview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                holder.cardview.getViewTreeObserver().removeOnPreDrawListener(this);
                fullHeight = 1000;

                ViewGroup.LayoutParams layoutParams = holder.cardview.getLayoutParams();
                layoutParams.height = 250;
                layoutParams.width = 1070;
                holder.cardview.setLayoutParams(layoutParams);
                return true;
            }
        });
    }

}
