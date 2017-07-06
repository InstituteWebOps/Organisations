package io.rohithram.podda;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ImageUtil {

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this)

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.image_not_found)
                .showImageOnLoading(R.drawable.loading_icon).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .defaultDisplayImageOptions(options)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

    }

    /**
     * Returns an ImageLoader instance assuming initImageLoader has already been called.
     * @return ImageLoader instance
     */
    public static ImageLoader getImageLoader(){
        return ImageLoader.getInstance();
    }

    /**
     * Initializes the config of ImageLoader and returns an instance.
     * @param context Context used in config init.
     * @return
     */
    public static ImageLoader getImageLoader(Context context){
        initImageLoader(context);
        return ImageLoader.getInstance();
    }
}
