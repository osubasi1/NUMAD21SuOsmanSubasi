package edu.neu.numad21su_osmansubasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<LinkCard> linkList = new ArrayList<>();
    ;

    private RecyclerView recyclerView;
    private RviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addFab;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        init(savedInstanceState);

        addFab = findViewById(R.id.addButton);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("length of the list is:" + linkList.size());
                int position = 0;
                addLink(position);
            }
        });
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    Toast.makeText(LinkCollectorActivity.this, "Deleted a link", Toast.LENGTH_SHORT).show();
                    int position = viewHolder.getLayoutPosition();
                    linkList.remove(position);
                    rviewAdapter.notifyItemRemoved(position);
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerView);



    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = linkList == null ? 0 : linkList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);


        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", linkList.get(i).getLinkName());
            outState.putString(KEY_OF_INSTANCE + i + "1", linkList.get(i).getLinkUrl());
        }
        super.onSaveInstanceState(outState);

    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (linkList == null || linkList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                for (int i = 0; i < size; i++) {
                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");

                    LinkCard itemCard = new LinkCard(linkName, url);

                    linkList.add(itemCard);
                }
            }
        }
        else {
            // empty list as required in tha assignment
        }
    }


    private void createRecyclerView(){
        rLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new RviewAdapter(linkList);
        LinkClickListener linkClickListener = new LinkClickListener() {
            @Override
            public void onItemClick(String url) {
                System.out.println("httpsCheck returns" + httpsCheck(url));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(httpsCheck(url)));
                startActivity(webIntent);
            }
        };
        rviewAdapter.setOnItemClickListener(linkClickListener);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    private String httpsCheck( String url){
        if (!url.startsWith("http://") || !url.startsWith("https://"))
            url = "https://" + url;
        return url;
    }
    private Boolean tldCheck (String url) {
        String[] tld = {"com", "edu", "org", "gov", "net", "biz", "info"};
        String urlTld = "";
        if (url.length() < 3) {
            return false;
        } else {
            urlTld = url.substring(url.length() - 3);
        }
        for (String each : tld) {
            if (each.equals(urlTld)) {
                return true;
            }
        }
        return false;
    }

    private void addLink (int position){
        AlertDialog.Builder newLinkBuilder = new AlertDialog.Builder(this);
        newLinkBuilder.setTitle("Create a new Link");

        View newLinkView = LayoutInflater.from(this).inflate(R.layout.link_collector_user_input, findViewById(R.id.content), false);

        final EditText linkName = (EditText) newLinkView.findViewById(R.id.urlName);
        final EditText url = (EditText) newLinkView.findViewById(R.id.urlInput);
        newLinkBuilder.setView(newLinkView);

        newLinkBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();;
                String name = linkName.getText().toString();
                String urlString = url.getText().toString();
                if (tldCheck(urlString)){
                    linkList.add(position, new LinkCard(name, urlString));
                    Toast.makeText(LinkCollectorActivity.this, "New link added to List", Toast.LENGTH_LONG).show();
                    rviewAdapter.notifyItemInserted(position);
                }else {
                    Toast.makeText(LinkCollectorActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
                }
            }

        });

    newLinkBuilder.show();
    }


}