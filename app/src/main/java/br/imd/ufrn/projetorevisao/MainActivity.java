package br.imd.ufrn.projetorevisao;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private Toolbar toolbar;
    private ListView list;
    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.itemsList);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemAnterior = selectedItem;
                selectedItem = adapter.getItem(position);

                if (selectedItemAnterior != null && selectedItemAnterior.equals(selectedItem)) {
                    selectedItem = null;
                    showAddButton();
                } else {
                    view.setSelected(true);
                    hideAddButton();
                }
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
    }

    private void showAddButton()
    {
        toolbar.getMenu().findItem(R.id.actionsAdd).setVisible(true);
        toolbar.getMenu().findItem(R.id.actionsEdit).setVisible(false);
        toolbar.getMenu().findItem(R.id.actionsRemove).setVisible(false);
    }

    private void hideAddButton()
    {
        toolbar.getMenu().findItem(R.id.actionsAdd).setVisible(false);
        toolbar.getMenu().findItem(R.id.actionsEdit).setVisible(true);
        toolbar.getMenu().findItem(R.id.actionsRemove).setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionsAdd:
                AddItemDialog.show(getSupportFragmentManager(), new AddItemDialog.OnTextListener() {
                    @Override
                    public void onSetTExt(String text) {
                        setText(text);
                    }
                });
                showAddButton();
                return true;
            case R.id.actionsEdit:
                AddItemDialog.show(getSupportFragmentManager(), new AddItemDialog.OnTextListener() {
                    @Override
                    public void onSetTExt(String text) {
                        setText(text);
                    }
                });
                return true;
            case R.id.actionsRemove:
                adapter.remove(selectedItem);
                showAddButton();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setText(String content)
    {
        int itemPosition = adapter.getPosition(selectedItem);
        if (itemPosition >= 0)
        {
            adapter.insert(content, itemPosition);
            adapter.remove(selectedItem);
        }
        else
        {
            adapter.add(content);
        }
    }
}