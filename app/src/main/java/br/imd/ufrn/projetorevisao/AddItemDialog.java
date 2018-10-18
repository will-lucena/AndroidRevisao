package br.imd.ufrn.projetorevisao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddItemDialog extends DialogFragment {

    private EditText editText;
    private OnTextListener listener;

    public static void show(FragmentManager fm, OnTextListener listener){

        AddItemDialog dialog = new AddItemDialog();
        dialog.listener = listener;
        dialog.show(fm, "addItemDialog");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Item");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null){
                    String text = editText.getText().toString();
                    listener.onSetTExt(text);
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_item,null);
        editText = view.findViewById(R.id.itemName);
        builder.setView(view);
        return builder.create();
    }

    public interface OnTextListener{
        void onSetTExt(String text);
    }
}
