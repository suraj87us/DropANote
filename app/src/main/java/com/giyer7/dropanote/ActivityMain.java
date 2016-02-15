package com.giyer7.dropanote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giyer7.dropanote.adapters.Divider;
import com.giyer7.dropanote.adapters.NoteAdapter;
import com.giyer7.dropanote.adapters.SimpleTouchCallback;
import com.giyer7.dropanote.beans.Note;
import com.giyer7.dropanote.widgets.NoteRecyclerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    ImageView iv_logo,iv_background;
    Toolbar mToolbar;
    Button mAddButton;
    NoteRecyclerView mRecyclerView;
    Realm mRealm;
    NoteAdapter mAdapter;
    RealmResults<Note> realmResult;
    View mEmptyView;

    ShowDialogListener mListener = new ShowDialogListener() {
        @Override
        public void add() {
            showDialogAdd();
        }
    };

    private RealmChangeListener mRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.updateData(realmResult);
        }
    };

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClicked(final int position) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityMain.this)
                    .setMessage("Mark Complete?")
                    .setIcon(R.drawable.ic_check)
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.markComplete(position);
                            Snackbar.make(mRecyclerView, "Whoa! You finished your goal.", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setCancelable(true)
                    .show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();
        realmResult = mRealm.where(Note.class).equalTo("isComplete", false).findAllAsync();
        iv_background = (ImageView) findViewById(R.id.img_background);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEmptyView = findViewById(R.id.emptyset);
        mRecyclerView = (NoteRecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager((this));
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new NoteAdapter(this, mRealm, realmResult, mListener, itemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.hideIfEmpty(mToolbar);
        mRecyclerView.showIfEmpty(mEmptyView);
        mRecyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        mAddButton = (Button) findViewById(R.id.btn_dropanote);
        mAddButton.setOnClickListener(this);
        SimpleTouchCallback callback = new SimpleTouchCallback(mAdapter, mRecyclerView);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
        initBackgroundImage();
        setSupportActionBar(mToolbar);
    }

    private void initBackgroundImage() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(iv_background);
    }

    @Override
    public void onClick(View v) {
        showDialogAdd();
    }

    private void showDialogAdd() {
        FragmentDialogAdd fragmentDialogAdd = new FragmentDialogAdd();
        fragmentDialogAdd.show(getSupportFragmentManager(),"FragmentDialogAdd");
    }

    @Override
    protected void onStart() {
        super.onStart();
        realmResult.addChangeListener(mRealmChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        realmResult.addChangeListener(mRealmChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_duedate:
                break;
            case R.id.action_sort_creationdate:
                break;
            case R.id.action_completed:
                break;
            case R.id.action_sort_alphabetically:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
