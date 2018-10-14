package moviecollection.andfile.com.yittest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import moviecollection.andfile.com.yittest.model.HitsItem;
import moviecollection.andfile.com.yittest.search.PixRecyclerAdapter;
import moviecollection.andfile.com.yittest.search.RecyclerLoadNextPage;
import moviecollection.andfile.com.yittest.search.SearchViewModel;
import moviecollection.andfile.com.yittest.utils.Const;
import moviecollection.andfile.com.yittest.utils.ItemHeightCalculateHolder;

public class MainActivity extends AppCompatActivity {

    private SearchViewModel<List<HitsItem>> searchVewModel;
    private LiveData<List<HitsItem>> searchData;
    private RecyclerView recyclerView;
    private ImageView buttonSearch;
    private ImageView clearButton;
    private EditText searchBar;
    private FlexboxLayoutManager layoutManager;
    private PixRecyclerAdapter adapter;
    private int position = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        searchBar.addTextChangedListener(initTextwatcher());
        setUpRecycler();
        setUpClickListeners();

        if(savedInstanceState != null){
            position = savedInstanceState.getInt(Const.ADATPER_POSITION);
        }

        searchVewModel = obtainSearchViewModel();
        searchData = searchVewModel.getData();
        searchData.observe(this, new Observer<List<HitsItem>>() {
            @Override
            public void onChanged(List<HitsItem> hitsItems) {
                adapter.addData(hitsItems);
            }
        });

        ViewTreeObserver vto = recyclerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(savedInstanceState == null){
                    ItemHeightCalculateHolder.getInstance().calculateItemHeight(recyclerView.getHeight(),isPortaitOrientation());
                }
            }
        });

        scrollToPossitionIfNeeded();
    }

    @Override
    protected void onStop() {
        searchVewModel.setAllData();
        super.onStop();
    }


    public SearchViewModel<List<HitsItem>> obtainSearchViewModel() {

        return ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    private RecyclerLoadNextPage getNextPageLoadCallback() {

        return new RecyclerLoadNextPage() {
            @Override
            public void loadNextpage() {
                searchVewModel.getNextPage();
            }
        };
    }

    private TextWatcher initTextwatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    showClearButton(true);
                } else {
                    showClearButton(false);
                }
            }
        };
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(adapter.getItemCount() > 0){
            outState.putInt(Const.ADATPER_POSITION, adapter.getPosition());
        }
        super.onSaveInstanceState(outState);
    }

    private void scrollToPossitionIfNeeded() {
        if(position>0){
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(position);
                    position = 0;
                }
            },500);
        }
    }

    private void setUpClickListeners() {
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clearData();
                String query = searchBar.getText().toString();
                searchVewModel.search(query);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setText("");
                showClearButton(false);
                adapter.clearData();
            }
        });
    }

    private void setUpRecycler() {
        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PixRecyclerAdapter(getNextPageLoadCallback());
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        clearButton = findViewById(R.id.icon_clear_search);
        buttonSearch = findViewById(R.id.search_button);
        searchBar = findViewById(R.id.search_bar);
        showClearButton(false);
    }

    private void showClearButton(boolean isVisible) {
        clearButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private boolean isPortaitOrientation(){
        int value = getResources().getConfiguration().orientation;
        return value == Configuration.ORIENTATION_PORTRAIT;
    }
}
