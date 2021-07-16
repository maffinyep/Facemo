package com.example.facemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facemo.databinding.ActivityMainBinding;
import com.example.facemo.databinding.ItemFaccioBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding b;
    public ArrayList<ToDo> body;
    public MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);

        body = ToDo.rawToBody(getSharedPreferences("body", Context.MODE_PRIVATE).getString("raw","play macarena"));
        mainAdapter = new MainAdapter();

        b.rcyFaccioMain.setAdapter(mainAdapter);
        b.rcyFaccioMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void onResetClick(View v) { mainAdapter.reset(); }

    public void onNextClick(View v) { mainAdapter.next(); }

    public void onBodyClick(View v) { this.startActivity(new Intent(this, BodyActivity.class)); }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor spEditor= getSharedPreferences("body", Context.MODE_PRIVATE).edit();
        spEditor.putString("toDoes", ToDo.bodyToRaw(mainAdapter.getToDoes()));
        spEditor.putInt("nextToDoes", mainAdapter.getNextToDoes());
        spEditor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences = getSharedPreferences("body", Context.MODE_PRIVATE);
        int nextToDoes = sharedPreferences.getInt("nextToDoes", 0);
        ArrayList<ToDo> toDoes = ToDo.rawToBody(sharedPreferences.getString("toDoes", ""));

        if(sharedPreferences.contains("nextToDoes") && sharedPreferences.contains("toDoes"))
            mainAdapter.set(nextToDoes, toDoes);
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainVH>
    {
        public int getNextToDoes() { return nextToDoes; }
        public ArrayList<ToDo> getToDoes() { return toDoes; }

        private int nextToDoes;
        private ArrayList<ToDo> toDoes;


        MainAdapter(){
            reset();
        }

        public void set(int nextToDoes, ArrayList<ToDo> toDoes){
            this.nextToDoes = nextToDoes;
            this.toDoes = toDoes;
            notifyDataSetChanged();
        }

        public void reset(){
            set(0, new ArrayList<>());
            next();
        }

        public void next(){
            try {
                int position = toDoes.size();
                toDoes.add(body.get(nextToDoes));
                nextToDoes++;
                notifyItemChanged(position);
                notifyItemRangeInserted(position, 1);
            } catch (Exception e){}
        }

        @NonNull
        @Override
        public MainVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater lytInf = LayoutInflater.from(parent.getContext());
            ItemFaccioBinding i = ItemFaccioBinding.inflate(lytInf, parent, false);
            return new MainVH(i);
        }

        @Override
        public void onBindViewHolder(@NonNull MainVH holder, int position)
        {
            holder.bind(toDoes.get(position));
        }

        @Override
        public int getItemCount()
        {
            try { return toDoes.size(); }
            catch (NullPointerException e) { return 0; }
        }

        class MainVH extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            final ItemFaccioBinding i;

            MainVH(ItemFaccioBinding bind)
            {
                super(bind.getRoot());
                i = bind;
            }

            void bind(ToDo r) {
                i.setLine(r);
                i.btnTestoFaccio.setOnClickListener(this);
                i.executePendingBindings();
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                toDoes.remove(position);
                notifyItemChanged(position);
                notifyItemRangeRemoved(position, 1);
                next();
            }
        }

    }

}