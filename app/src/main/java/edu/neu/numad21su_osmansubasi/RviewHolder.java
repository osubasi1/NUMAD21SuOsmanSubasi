package edu.neu.numad21su_osmansubasi;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RviewHolder  extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView url;


    public RviewHolder(View itemView, final LinkClickListener listener) {
        super(itemView);
        name = itemView.findViewById(R.id.link_name);
        url = itemView.findViewById(R.id.link_url);
        System.out.println("url is: " + url);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null){
                    int position = getLayoutPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(url.getText().toString());
                    }
                }
            }
        });

    }

}
