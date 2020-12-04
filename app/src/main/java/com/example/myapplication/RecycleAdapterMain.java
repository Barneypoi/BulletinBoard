package com.example.myapplication;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    public List<News> list;
    private OnItemClickListener mItemClickListener;

    //构造方法，传入数据
    public RecycleAdapterMain(Context context,List<News> list){
        this.context = context;
        this.list = list;
    }

    //点击事件接口

    public interface OnItemClickListener {
        void onItemClick(String s, int position);
    }

    //返回item类型
    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    //为不同类型创建不同的item视图
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fulltext, parent, false);
            TextViewHolder holder = new TextViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mItemClickListener.onItemClick(list.get((Integer) v.getTag()).id, (Integer) v.getTag());;
                }
            });
            return holder;
        } else if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leftpic, parent, false);
            LeftViewHolder holder = new LeftViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mItemClickListener.onItemClick(list.get((Integer) v.getTag()).id, (Integer) v.getTag());;
                }
            });
            return holder;
        } else if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rightpic, parent, false);
            RightViewHolder holder = new RightViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mItemClickListener.onItemClick(list.get((Integer) v.getTag()).id, (Integer) v.getTag());;
                }
            });
            return holder;
        } else if(viewType == 3){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_centerpic, parent, false);
            CenterViewHolder holder = new CenterViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mItemClickListener.onItemClick(list.get((Integer) v.getTag()).id, (Integer) v.getTag());;
                }
            });
            return holder;
        } else if(viewType == 4){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multpic, parent, false);
            MultViewHolder holder = new MultViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mItemClickListener.onItemClick(list.get((Integer) v.getTag()).id, (Integer) v.getTag());
                }
            });
            return holder;
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TextViewHolder){
            TextViewHolder viewHolder = (TextViewHolder)holder;
            viewHolder.textView1.setText(list.get(position).title);
            viewHolder.textView2.setText("作者："+list.get(position).author+" 时间："+list.get(position).publishTime);
            viewHolder.itemView.setTag(position);
        }
        else if(holder instanceof LeftViewHolder){
            LeftViewHolder viewHolder = (LeftViewHolder)holder;
            viewHolder.textView1.setText(list.get(position).title);
            viewHolder.textView2.setText("作者："+list.get(position).author+" 时间："+list.get(position).publishTime);
            viewHolder.itemView.setTag(position);
            String url = "file:///android_asset/" + list.get(position).cover;
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView1);
        }
        else if(holder instanceof RightViewHolder){
            RightViewHolder viewHolder = (RightViewHolder)holder;
            viewHolder.textView1.setText(list.get(position).title);
            viewHolder.textView2.setText("作者："+list.get(position).author+" 时间："+list.get(position).publishTime);
            viewHolder.itemView.setTag(position);
            String url = "file:///android_asset/" + list.get(position).cover;
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView1);
        }
        else if(holder instanceof CenterViewHolder){
            CenterViewHolder viewHolder = (CenterViewHolder)holder;
            viewHolder.textView1.setText(list.get(position).title);
            viewHolder.textView2.setText("作者："+list.get(position).author+" 时间："+list.get(position).publishTime);
            viewHolder.itemView.setTag(position);
            String url = "file:///android_asset/" + list.get(position).cover;
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView1);
        }
        else if(holder instanceof MultViewHolder){
            MultViewHolder viewHolder = (MultViewHolder)holder;
            viewHolder.textView1.setText(list.get(position).title);
            viewHolder.textView2.setText("作者："+list.get(position).author+" 时间："+list.get(position).publishTime);
            viewHolder.itemView.setTag(position);
            String url = "file:///android_asset/" + list.get(position).covers[0];
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView1);
            url = "file:///android_asset/" + list.get(position).covers[1];
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView2);
            url = "file:///android_asset/" + list.get(position).covers[2];
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView3);
            url = "file:///android_asset/" + list.get(position).covers[3];
            Glide.with(context)
                    .load(url)
                    .into(viewHolder.imageView4);
        }
        else{

        }
    }

    //返回列表大小
    @Override
    public int getItemCount() {
        if(list==null) return 0;
        return list.size();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    class TextViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.Title);
            textView2 = (TextView) itemView.findViewById(R.id.Subtitle);
        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        ImageView imageView1;
        public CenterViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.TitleCenter);
            textView2 = (TextView) itemView.findViewById(R.id.SubtitleCenter);
            imageView1 = (ImageView) itemView.findViewById(R.id.pictureCenter);
        }
    }

    class LeftViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        ImageView imageView1;
        public LeftViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.TitleLeft);
            textView2 = (TextView) itemView.findViewById(R.id.SubtitleLeft);
            imageView1 = (ImageView) itemView.findViewById(R.id.pictureLeft);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        ImageView imageView1;
        public RightViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.TitleRight);
            textView2 = (TextView) itemView.findViewById(R.id.SubtitleRight);
            imageView1 = (ImageView) itemView.findViewById(R.id.pictureRight);
        }
    }

    class MultViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        ImageView imageView1, imageView2, imageView3, imageView4;
        public MultViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.TitleMult);
            textView2 = (TextView) itemView.findViewById(R.id.SubtitleMult);
            imageView1 = (ImageView) itemView.findViewById(R.id.picture1);
            imageView2 = (ImageView) itemView.findViewById(R.id.picture2);
            imageView3 = (ImageView) itemView.findViewById(R.id.picture3);
            imageView4 = (ImageView) itemView.findViewById(R.id.picture4);
        }
    }
}
