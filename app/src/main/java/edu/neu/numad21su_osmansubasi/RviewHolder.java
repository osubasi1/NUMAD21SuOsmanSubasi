package edu.neu.numad21su_osmansubasi;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RviewHolder  extends RecyclerView.ViewHolder {

    public TextView displayedText;
    public TextView url;


    public RviewHolder(View itemView, final LinkClickListener link) {
        super(itemView);
        displayedText = itemView.findViewById(R.id.link_collector_item);
        url = itemView.findViewById(R.id.urlInput);

        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (link != null){
                    int position = getLayoutPosition();
                    if(position != RecyclerView.NO_POSITION){
                        link.onItemClick(url.getText().toString());
                    }
                }
            }
        });

    }

}
