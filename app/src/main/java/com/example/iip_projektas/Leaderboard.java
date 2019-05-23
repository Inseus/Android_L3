package com.example.iip_projektas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class LeaderAdapter extends ArrayAdapter<Leader>
{
    public LeaderAdapter(Context context, List<Leader> objects) {
        super(context, R.layout.adapter_view_layout, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if(v==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_view_layout,null);
        }

        TextView id = v.findViewById(R.id.idView);
        TextView username = v.findViewById(R.id.usernameView);
        TextView score = v.findViewById(R.id.scoreView);

        Leader leader = getItem(position);

        id.setText(leader.getId());
        username.setText(leader.getUsername());
        score.setText(leader.getScore());

        return v;
    }
}
class Leader {
    private String id;
    private String username;
    private String score;

    public Leader() {
    }

    public Leader(String id, String username, String score) {
        this.id = id;
        this.username = username;
        this.score=score;
    }
    public String getId()
    {
        return  id;
    }
    public void setId()
    {
        this.id=id;
    }
    public String getUsername()
    {
        return  username;
    }
    public void setUsername()
    {
        this.username=username;
    }
    public String getScore()
    {
        return  score;
    }
    public void setScore()
    {
        this.score=score;
    }
}

public class Leaderboard extends Activity {

    public LeaderAdapter adapter;
    public ListView mListView;
    LeaderboardDatabaseHandler dbhandler;
    List<Leader> leaderList;
    private int scores_count = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        leaderList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        dbhandler = new LeaderboardDatabaseHandler(this);
        mListView = findViewById(R.id.leader_list);

        viewAll();
    }


    public void viewAll()
    {
        int counter=1;
        Cursor res = dbhandler.getEntries();

        while(res.moveToNext()&& counter<=scores_count)
        {
            Leader leader = new Leader(Integer.toString(counter),res.getString(1),res.getString(2));
            leaderList.add(leader);
            counter++;
        }
        adapter = new LeaderAdapter(this, leaderList);
        mListView.setAdapter(adapter);
    }

}
