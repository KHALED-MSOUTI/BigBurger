package com.khmsouti.bigburger.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/*
 * THIS CLASS IS COPIED FROM GITHUB AND EDITED TO FIT TO MY USE
 *
 * MAIN SOURCE FOUND AT :
 *       https://github.com/xabaras/RecyclerViewSwipeDecorator
 */
public class SwipeDecorator {
    private Context context;
    private Canvas canvas;
    private RecyclerView.ViewHolder viewHolder;
    private float dX;
    private int actionState;

    private int swipeLeftBackgroundColor;
    private int swipeLeftActionIconId;

    private int swipeRightBackgroundColor;
    private int swipeRightActionIconId;

    private int iconHorizontalMargin;


    private SwipeDecorator() {
        swipeLeftBackgroundColor = 0;
        swipeRightBackgroundColor = 0;
        swipeLeftActionIconId = 0;
        swipeRightActionIconId = 0;
    }

    /**
     * Create a @RecyclerViewSwipeDecorator
     *
     * @param context     A valid Context object for the RecyclerView
     * @param canvas      The canvas which RecyclerView is drawing its children
     * @param viewHolder  The ViewHolder which is being interacted by the User or it was interacted and simply animating to its original position
     * @param dX          The amount of horizontal displacement caused by user's action
     * @param actionState The type of interaction on the View. Is either ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
     */
    private SwipeDecorator(Context context, Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, int actionState) {
        this();
        this.context = context;
        this.canvas = canvas;
        this.viewHolder = viewHolder;
        this.dX = dX;
        this.actionState = actionState;
        this.iconHorizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
    }


    /**
     * Set the background color for left swipe direction
     *
     * @param swipeLeftBackgroundColor The resource id of the background color to be set
     */
    private void setSwipeLeftBackgroundColor(int swipeLeftBackgroundColor) {
        this.swipeLeftBackgroundColor = swipeLeftBackgroundColor;
    }

    /**
     * Set the action icon for left swipe direction
     *
     * @param swipeLeftActionIconId The resource id of the icon to be set
     */
    private void setSwipeLeftActionIconId(int swipeLeftActionIconId) {
        this.swipeLeftActionIconId = swipeLeftActionIconId;
    }

    /**
     * Set the background color for right swipe direction
     *
     * @param swipeRightBackgroundColor The resource id of the background color to be set
     */
    private void setSwipeRightBackgroundColor(int swipeRightBackgroundColor) {
        this.swipeRightBackgroundColor = swipeRightBackgroundColor;
    }

    /**
     * Set the action icon for right swipe direction
     *
     * @param swipeRightActionIconId The resource id of the icon to be set
     */
    private void setSwipeRightActionIconId(int swipeRightActionIconId) {
        this.swipeRightActionIconId = swipeRightActionIconId;
    }


    /**
     * Decorate the RecyclerView item with the chosen backgrounds and icons
     */
    public void decorate() {
        try {
            if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) return;

            if (dX > 0) {
                // Swiping Right
                if (swipeRightBackgroundColor != 0) {
                    final ColorDrawable background = new ColorDrawable(swipeRightBackgroundColor);
                    background.setBounds(viewHolder.itemView.getLeft(), viewHolder.itemView.getTop(), viewHolder.itemView.getLeft() + (int) dX, viewHolder.itemView.getBottom());
                    background.draw(canvas);
                }

                int iconSize;
                if (swipeRightActionIconId != 0) {
                    Drawable icon = ContextCompat.getDrawable(context, swipeRightActionIconId);
                    assert icon != null;
                    iconSize = icon.getIntrinsicHeight();
                    int halfIcon = iconSize / 2;
                    int top = viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2 - halfIcon);
                    icon.setBounds(viewHolder.itemView.getLeft() + iconHorizontalMargin, top, viewHolder.itemView.getLeft() + iconHorizontalMargin + icon.getIntrinsicWidth(), top + icon.getIntrinsicHeight());
                    icon.draw(canvas);
                }


            } else if (dX < 0) {
                // Swiping Left
                if (swipeLeftBackgroundColor != 0) {
                    final ColorDrawable background = new ColorDrawable(swipeLeftBackgroundColor);
                    background.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop(), viewHolder.itemView.getRight(), viewHolder.itemView.getBottom());
                    background.draw(canvas);
                }

                int imgLeft;
                if (swipeLeftActionIconId != 0) {
                    Drawable icon = ContextCompat.getDrawable(context, swipeLeftActionIconId);
                    assert icon != null;
                    int halfIcon = icon.getIntrinsicHeight() / 2;
                    int top = viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2 - halfIcon);
                    imgLeft = viewHolder.itemView.getRight() - iconHorizontalMargin - halfIcon * 2;
                    icon.setBounds(imgLeft, top, viewHolder.itemView.getRight() - iconHorizontalMargin, top + icon.getIntrinsicHeight());
                    icon.draw(canvas);
                }

            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    /**
     * A Builder for the RecyclerViewSwipeDecorator class
     */
    public static class Builder {
        private SwipeDecorator mDecorator;

        /**
         * Create a builder for a RecyclerViewsSwipeDecorator
         *
         * @param context     A valid Context object for the RecyclerView
         * @param canvas      The canvas which RecyclerView is drawing its children
         * @param viewHolder  The ViewHolder which is being interacted by the User or it was interacted and simply animating to its original position
         * @param dX          The amount of horizontal displacement caused by user's action
         * @param actionState The type of interaction on the View. Is either ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
         */
        public Builder(Context context, Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, int actionState) {
            mDecorator = new SwipeDecorator(
                    context,
                    canvas,
                    viewHolder,
                    dX,
                    actionState
            );
        }


        /**
         * Add a background color while swiping right
         *
         * @param color A single color value in the form 0xAARRGGBB
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public SwipeDecorator.Builder addSwipeRightBackgroundColor(int color) {
            mDecorator.setSwipeRightBackgroundColor(color);
            return this;
        }

        /**
         * Add an action icon while swiping right
         *
         * @param drawableId The resource id of the icon to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public SwipeDecorator.Builder addSwipeRightActionIcon(int drawableId) {
            mDecorator.setSwipeRightActionIconId(drawableId);
            return this;
        }


        /**
         * Adds a background color while swiping left
         *
         * @param color A single color value in the form 0xAARRGGBB
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public SwipeDecorator.Builder addSwipeLeftBackgroundColor(int color) {
            mDecorator.setSwipeLeftBackgroundColor(color);
            return this;
        }

        /**
         * Add an action icon while swiping left
         *
         * @param drawableId The resource id of the icon to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public SwipeDecorator.Builder addSwipeLeftActionIcon(int drawableId) {
            mDecorator.setSwipeLeftActionIconId(drawableId);
            return this;
        }


        /**
         * Create a RecyclerViewSwipeDecorator
         *
         * @return The created @RecyclerViewSwipeDecorator
         */
        public SwipeDecorator create() {
            return mDecorator;
        }
    }
}
