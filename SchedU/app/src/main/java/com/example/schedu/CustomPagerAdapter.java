package com.example.schedu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;

    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public int getCount() {
        return ModelObject.values().length;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }
}
