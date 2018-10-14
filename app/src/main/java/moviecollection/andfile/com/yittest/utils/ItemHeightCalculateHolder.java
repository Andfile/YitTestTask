package moviecollection.andfile.com.yittest.utils;

/**
 * Created by Andrey Lebedev on 10/14/2018.
 */
public class ItemHeightCalculateHolder {

    private static ItemHeightCalculateHolder instance;
    private int height;

    private ItemHeightCalculateHolder() {
    }

    public static ItemHeightCalculateHolder getInstance(){

        if(instance == null){

            instance = new ItemHeightCalculateHolder();
        }
        return instance;
    }

    public int getStoredItemHeight(){

        return height;
    }

    public void calculateItemHeight(int recyclerViewHeight, boolean isPortrait){

        height =  recyclerViewHeight /  (isPortrait ? Const.SHOWN_ROWS_IN_LIST : Const.SHOWN_ROWS_IN_LIST/2);
    }
}
