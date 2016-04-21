package com.gettyimages.gettydemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.gettyimages.connectsdk.SdkException;
import com.gettyimages.connectsdk.search.IBlendedImagesSearch;
import com.gettyimages.connectsdk.search.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by namseok on 16. 4. 17..
 */
public class GettyCrawler {

    public List<ItemMutator> crawlItems(String url) {
        List<ItemMutator> items = new ArrayList<ItemMutator>();

        //ref : https://jsoup.org/cookbook/extracting-data/selector-syntax
        Elements elements = null;
        try {
            elements = Jsoup.connect(url).get().select("img[src$=.jpg]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = null;
        try {
            links= Jsoup.connect(url).get().select("a[href]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> imagesUrl = new ArrayList<String>();
        int index = 0;
        for(Element image : elements) {
            if(image.absUrl("src").contains("Thumbnails")) {
                imagesUrl.add(index, image.absUrl("src"));
                index++;
            }
        }

        int indexFirst = 0, indexSecond = 0;
        for(Element image : links) {
            if(image.attr("href").contains("Picture-Library/Image.aspx")) {
                if(indexFirst%2 !=0) {
                    ItemMutator item = new ItemMutator();
                    item.setUrl(imagesUrl.get(indexSecond));
                    item.setTitle(image.text());

                    items.add(item);
                    indexSecond++;
                }
                indexFirst++;
            }
        }
        return items;
    }

    public List<ItemMutator> crawlItems2(Search search, String searchTerm, String order, int page) {
        // Both page 0 and page 1 occured with same result
        List<ItemMutator> itemMutator = new ArrayList<ItemMutator>();
        String result = "";
        try {
            IBlendedImagesSearch imageSearch = null;
            if(searchTerm == null || searchTerm.length() == 0) {
                imageSearch = search.Images().WithPage(page).WithSortOrder(order);
            } else {
                imageSearch = search.Images().WithPhrase(searchTerm).WithPage(page).WithSortOrder(order);
            }
            result = imageSearch.ExecuteAsync();
        } catch (SdkException e) {
            result = "[SdkException] "+e.getMessage();
        } catch (Exception e) {
            result = "[Exception] "+e.toString();
        }
        Log.d("Getty", "Result : " + result);

        try {
            JSONObject json = (JSONObject) new JSONObject(result);

            int resultCount = Integer.parseInt(json.getString("result_count"));

            JSONArray images = json.getJSONArray("images");

            int imagesLen = (images != null)?images.length():0;
            Log.d(this.getClass().getSimpleName(), "result_count : " + resultCount + " , images_count : " + imagesLen);

            for(int i=0; i < images.length(); i++) {
                ItemMutator item = new ItemMutator();
                String url = images.getJSONObject(i).getJSONArray("display_sizes").getJSONObject(0).getString("uri");
                item.setUrl(url);
                item.setId(images.getJSONObject(i).getString("id"));
                item.setTitle(images.getJSONObject(i).getString("title"));
                item.setCaption(images.getJSONObject(i).getString("caption"));
                //item.setBitmap(getBitmapFromURL(url));
                itemMutator.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemMutator;
    }

    //TODO : delete
    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
