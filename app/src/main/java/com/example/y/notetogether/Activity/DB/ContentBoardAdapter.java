package com.example.y.notetogether.Activity.DB;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.y.notetogether.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by y on 2016-03-22.
 */
public class ContentBoardAdapter extends RecyclerView.Adapter<ContentBoardAdapter.ViewHolder> {
    private Context context;
    private Dao dao;
    public static ArrayList<Contents> contentList;
    private ArrayList<Contents> arraylist;//검색기능을 위한 리스트공간 생성
    private int edit_btn_state=1;
    private ViewHolder preholder=null;
    int item_layout;
    private int fullHeight;
    public ContentBoardAdapter(Context context, ArrayList<Contents> contentList, int item_layout) {
        this.contentList = contentList;
        this.context = context;
        this.item_layout = item_layout;
        //검색기능때문에 아래 2줄 추가
        this.arraylist = new ArrayList<Contents>();
        this.arraylist.addAll(contentList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contentboard_item, null);
        return new ViewHolder(v);
    }
    //메세지 펼치기
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Contents item = contentList.get(position);
        //메모 목록 시각화
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        holder.time.setText(item.getTime());
        //처음에 숨길 내용 설정
        holder.content.setVisibility(View.INVISIBLE);
        //메세지 색깔 넣기
        if(item.getColor()==1) {
            holder.cardview.setCardBackgroundColor(Color.parseColor("#FFCC80"));
        }
        else if(item.getColor()==2){
            holder.cardview.setCardBackgroundColor(Color.parseColor("#FFE0B2"));
        }
        else if(item.getColor()==3){
            holder.cardview.setCardBackgroundColor(Color.parseColor("#FFF3E0"));
        }
        else if(item.getColor()==4){
            holder.cardview.setCardBackgroundColor(Color.parseColor("#FFFAF3"));
        }
        else
            holder.cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

        //시각화
        setPreDraw(holder);
        //스크롤 보이게
        holder.scrollView.setVerticalScrollBarEnabled(true);
        //메모 클릭했을 때 메모 내용 보이게
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn_edit.setTextColor(Color.parseColor("#FFFFFF"));
                holder.btn_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                holder.btn_finish.setTextColor(Color.parseColor("#FFFFFF"));
                holder.btn_finish.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                if(holder.state==false)
                {
                    if(preholder!=null) {
                        if(preholder.state==true) {
                            toggleCardView(preholder);
                            preholder.content.setVisibility(View.INVISIBLE);
                            preholder.btn_edit.setVisibility(View.INVISIBLE);
                            preholder.btn_finish.setVisibility(View.INVISIBLE);
                            preholder.content.setEnabled(false);
                            preholder.state=false;
                            preholder=null;
                        }
                    }
                    holder.btn_edit.setText("수정");
                    holder.content.setVisibility(View.VISIBLE);
                    holder.btn_edit.setVisibility(View.VISIBLE);
                    holder.content.setEnabled(false);
                    toggleCardView(holder);
                    holder.state=true;
                    preholder=holder;
                }
                else
                {
                    toggleCardView(holder);
                    holder.state = false;
                    holder.content.setVisibility(View.INVISIBLE);
                    holder.btn_edit.setVisibility(View.INVISIBLE);
                    holder.btn_finish.setVisibility(View.INVISIBLE);
                    preholder=null;
                }
                //수정 버튼
                holder.btn_edit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        if(edit_btn_state==1)
                        {
                            edit_btn_state=0;
                            holder.content.setEnabled(true);
                            holder.btn_edit.setText("취소");
                            holder.btn_finish.setVisibility(View.VISIBLE);

                        }

                        else{
                            holder.content.setEnabled(false);
                            holder.btn_finish.setVisibility(View.INVISIBLE);
                            holder.btn_edit.setText("수정");
                            edit_btn_state=1;
                        }
                    }
                });
                //완료버튼
                holder.btn_finish.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(final View v) {
                        //대화상자
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("제목 : " + item.getTitle());
                        builder.setMessage("메모를 수정하시겠습니까?");
                        builder.setCancelable(false);
                        //확인 버튼 설정
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toggleCardView(holder);
                                edit_btn_state=1;
                                holder.state = false;
                                holder.btn_edit.setText("수정");
                                holder.btn_edit.setVisibility(View.INVISIBLE);
                                holder.btn_finish.setVisibility(View.INVISIBLE);
                                preholder = holder;
                                dao = new Dao(context);
                                dao.update(item.getId(),holder.content.getText().toString());
                                Toast.makeText(context, "메세지가 수정되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //취소 버튼 설정
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();//알림창 객체 생성
                        dialog.show();//알림창 띄우기

                    }
                });
            }
        });


        //메세지 삭제 => DB접근
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //대화상자
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("제목 : "+item.getTitle());
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setCancelable(false);
                //확인 버튼 설정
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new Dao(context);
                        dao.delete(item.getId());
                        Toast.makeText(context, "메세지가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        //뷰에서 리스트 삭제 모션
                        contentList.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                //취소 버튼 설정
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();//알림창 객체 생성
                dialog.show();//알림창 띄우기

                return false;
            }
        });
    }

    private void setPreDraw(final ViewHolder holder) {
        holder.cardview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                holder.cardview.getViewTreeObserver().removeOnPreDrawListener(this);
                fullHeight = 1000;

                ViewGroup.LayoutParams layoutParams = holder.cardview.getLayoutParams();
                layoutParams.height = 250;
                holder.cardview.setLayoutParams(layoutParams);

                return true;
            }
        });
    }

    private void toggleCardView(final ViewHolder holder) {
        int minHeight = 250;
        if (holder.cardview.getHeight() == minHeight) {
            ValueAnimator anim = ValueAnimator.ofInt(holder.cardview.getMeasuredHeightAndState(), fullHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = holder.cardview.getLayoutParams();
                    layoutParams.height = val;
                    holder.cardview.setLayoutParams(layoutParams);
                }
            });
            anim.start();
        } else {
            ValueAnimator anim = ValueAnimator.ofInt(holder.cardview.getMeasuredHeightAndState(), minHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = holder.cardview.getLayoutParams();
                    layoutParams.height = val;
                    holder.cardview.setLayoutParams(layoutParams);
                }
            });
            anim.start();
        }

    }
    @Override
    public int getItemCount() {
        return this.contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        EditText content;
        CardView cardview;
        Button btn_edit;
        Button btn_finish;
        ScrollView scrollView;
        Boolean state = false;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView_contentBoard_title);
            time = (TextView) itemView.findViewById(R.id.textView_contentBoard_time);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            content = (EditText) itemView.findViewById(R.id.textView_contentBoard_content);
            btn_edit = (Button)itemView.findViewById(R.id.btn_contentBoard_edit);
            btn_finish = (Button) itemView.findViewById(R.id.btn_contentBoard_finish);
            scrollView = (ScrollView)itemView.findViewById(R.id.scrollView_contentBoard_scroll);
        }
    }
    //검색 기능
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        contentList.clear();
        if(charText.length()==0){
            contentList.addAll(arraylist);
        }
        else {
            for(Contents wp : arraylist){
                if(wp.getContent().toLowerCase(Locale.getDefault()).contains(charText)){
                    contentList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
