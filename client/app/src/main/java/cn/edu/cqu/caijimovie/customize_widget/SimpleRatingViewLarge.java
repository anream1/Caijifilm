package cn.edu.cqu.caijimovie.customize_widget;

import android.content.Context;
import android.widget.ImageView;

import com.example.xlhratingbar_lib.IRatingView;

import cn.edu.cqu.caijimovie.R;

public class SimpleRatingViewLarge implements IRatingView {
    @Override
    public int getStateRes(int posi, int state) {
        int id = R.drawable.ic_star_grey_40dp;
        switch (state) {
            case STATE_NONE:
                id = R.drawable.ic_star_grey_40dp;
                break;
            case STATE_HALF:
                id = R.drawable.ic_star_grey_40dp;
                break;
            case STATE_FULL:
                id = R.drawable.ic_star_yellow_40dp;
                break;
            default:
                break;
        }
        return id;
    }

    @Override
    public int getCurrentState(float rating, int numStars, int position) {
        position++;
        float dis = position - rating;
        if (dis <= 0.1) {
            return STATE_FULL;
        }
        //if (dis == 0.5) {
        if (dis <= 0.6 && dis > 0.1) {
            return STATE_HALF;
        }
        if (dis > 0.6) {
            return STATE_NONE;
        }
        return 0;
    }

    //返回RatingView
    @Override
    public ImageView getRatingView(Context context, int numStars, int posi) {
        return new ImageView(context);
    }
}

