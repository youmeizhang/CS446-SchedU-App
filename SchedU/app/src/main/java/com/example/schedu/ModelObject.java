package com.example.schedu;

public enum ModelObject {
    FIRST(R.string.first, R.layout.blank),
    SECOND(R.string.second, R.layout.second_schedule),
    THIRD(R.string.third, R.layout.third_schedule);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
