package com.onwhichtvchannel.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.onwhichtvchannel.Model.DataModel;
import com.onwhichtvchannel.Model.ItemModel;
import com.onwhichtvchannel.R;
import com.onwhichtvchannel.Utils.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;

        new GetDataFromHtml().execute(new String[]{Constants.URL});
    }


    private class GetDataFromHtml extends AsyncTask<String, Void, ArrayList<DataModel>> {

        ProgressDialog prog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Start Progress Dialog
            prog = new ProgressDialog(mContext);
            prog.setMessage(mContext.getResources().getString(R.string.loading));
            prog.setIndeterminate(false);
            prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prog.setCancelable(false);
            prog.show();
        }

        @Override
        protected ArrayList<DataModel> doInBackground(String... strings) {
            ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
            Document doc = null;
            try {
                Log.d("JSwa", "Connecting to [" + strings[0] + "]");
                doc = Jsoup.connect(strings[0]).maxBodySize(10000000).get();
                Log.d("JSwa", "Connected to [" + strings[0] + "]");

                // Get document (HTML page) title
                String title = doc.title();
                Log.d("JSwA", "Title [" + title + "]");

                // Get [tab-content mb-30] div data
                Elements tabDiv = doc.select("div.tab-content.ft-tab-content.mb-30");
                for (Element tabs : tabDiv) {

                    // Get [id = fixtures class = swiper-slide] div data
                    Elements swipeSlidesDiv = tabs.select("div#fixtures.swiper-slide");
                    for (Element swipeSlide : swipeSlidesDiv) {

                        //Get [widget kopa-match-list-widget] div data
                        Elements widgetDiv = swipeSlide.select("div.widget.kopa-match-list-widget");
                        for (Element widge : widgetDiv) {

                            // Get [match-item last-item style1]div data
                            Elements mainDiv = widge.select("div.match-item.last-item.style1");
                            for (Element div : mainDiv) {

                                DataModel model = new DataModel();

                                //Get header data [date header]
                                Element header = div.getElementsByTag("header").first();

                                //set header in data model
                                model.setHeader(header.text());

                                ArrayList<ItemModel> itemModelArrayList = new ArrayList<>();

                                //Get [r-item] div data
                                Elements rItemDiv = mainDiv.select("div.r-item");
                                for (Element rItem : rItemDiv) {

                                    //Get [arkenus-event-cell] div data
                                    Elements arkenusDiv = rItem.select("div.arkenus-event-cell");
                                    for (Element arkenus : arkenusDiv) {

                                        //Get [col-md-2 col-sm-2 col-xs-2] div data  -- left div
                                        Element leftDiv = arkenus.select("div.col-md-2.col-sm-2.col-xs-2").first();
                                        Element leftImage = leftDiv.select("img").first();

                                        //Get [col-md-8 col-sm-8 col-xs-6] div data  -- middle div
                                        Element dataDiv = arkenus.select("div.col-md-8.col-sm-8.col-xs-6").first();
                                        Element notesSpan = dataDiv.select("span[class=notes_label]").first();
                                        Element link = dataDiv.select("a").first();
                                        Element nameDiv = link.select("div[itemprop=broadcastOfEvent]").first();
                                        Element notesName = nameDiv.select("span[itemprop=name]").first();
                                        Element startDate = nameDiv.select("meta[itemprop=startDate]").first();

                                        //Get [col-md-2 col-sm-2 col-xs-4 channel_icon] div data  -- right div
                                        Element rightDiv = arkenus.select("div.col-md-2.col-sm-2.col-xs-4.channel_icon").first();
                                        Element rightImage = rightDiv.select("img").first();

                                        String time, category, category_icon, name = "", startDateTxt, channel_icon;
                                        time = leftDiv.text().trim();
                                        category_icon = leftImage.attr("src");
                                        category = notesSpan.text().trim();

                                        name = nameDiv.text().trim().substring(notesName.text().trim().length() + 1, nameDiv.text().trim().length());
                                        startDateTxt = startDate.text();
                                        channel_icon = rightImage.attr("src");

                                        //set data in item model
                                        ItemModel itemModel = new ItemModel();
                                        itemModel.setTime(time);
                                        itemModel.setCategory_icon(category_icon);
                                        itemModel.setCategory(category);
                                        itemModel.setName(name);
                                        itemModel.setStartaDate(startDateTxt);
                                        itemModel.setChannel_icon(channel_icon);

                                        itemModelArrayList.add(itemModel);
                                        model.setItemModelArrayList(itemModelArrayList);
                                    }
                                    dataModelArrayList.add(model);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return dataModelArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<DataModel> dataModelArrayList) {
            try {
                //Close Progress Dialog
                if (prog != null && prog.isShowing()) {
                    prog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
