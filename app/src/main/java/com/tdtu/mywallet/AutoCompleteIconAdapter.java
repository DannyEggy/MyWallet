package com.tdtu.mywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tdtu.mywallet.model.Icon;

import java.util.List;

public class AutoCompleteIconAdapter extends ArrayAdapter<Icon> {
    private List<Icon> iconList;
    public AutoCompleteIconAdapter(@NonNull Context context, @NonNull List<Icon> iconList) {
        super(context, 0, iconList);

    }



    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Lấy View hiện tại hoặc tạo mới nếu chưa có
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_icon_selection, parent, false);
        }

        // Lấy đối tượng Icon tại vị trí position
        Icon icon = getItem(position);

        ImageView imageView = view.findViewById(R.id.icon_category_item);
        imageView.setBackgroundResource(icon.getIconResID());
        // Ánh xạ TextView trong layout
        TextView textView = view.findViewById(R.id.name_icon);

        // Thiết lập giá trị cho TextView
        if (icon != null) {
            textView.setText(icon.getIconName());
        }

        return view;
    }
}
