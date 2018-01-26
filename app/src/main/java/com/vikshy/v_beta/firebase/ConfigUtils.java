package com.vikshy.v_beta.firebase;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by laxman.muttineni on 26/01/18.
 */

public class ConfigUtils {

    public static String BANNER_TIME = "home_banner_time";
    public static String BANNER_KEY_FORMAT = "%s_%d";
    public static String BANNER_ID = "banner";

    public static int MAX_BANNERS = 8;
    private static final Set<String> DEFAULT_BANNERS = ImmutableSet.of(   //todo : change defaults
            "http://cdn3.nflximg.net/images/3093/2043093.jpg",
            "http://tvfiles.alphacoders.com/100/hdclearart-10.png",
            "http://a4.espncdn.com/combiner/i?img=%2Fphoto%2F2017%2F0424%2Fr202742_1296x729_16-9.jpg",
            "https://cdn-s3.si.com/s3fs-public/images/Maria-sharapova-464092644_master.jpg",
            "https://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2017/03/09/Pictures/madonna-sebastian_dd24fe10-049d-11e7-87c7-5947ba54d240.JPG",
            "http://www.apglitz.com/wp-content/uploads/2017/10/Anupama-Parameswaran-hot-in-White-Gown-Photos-1.jpg"
    );

    public static final Map<String, Object> FIRE_BASE_DEFAULTS = ImmutableMap.<String, Object>builder()
            .put(BANNER_TIME, 4000)
            .putAll(getDefaultBanners())
            .build();

    public static String getBannerkey(int id) {
        return String.format(BANNER_KEY_FORMAT, BANNER_ID, id);
    }

    private static Map<String, Object> getDefaultBanners() {
        Map<String, Object> defaultBannersMap = new HashMap<>();
        Iterator<String> iterator = DEFAULT_BANNERS.iterator();
        int i =1;
        while (iterator.hasNext()) {
            defaultBannersMap.put(getBannerkey(i), iterator.next());
            i++;
        }
        return defaultBannersMap;
    }

}
