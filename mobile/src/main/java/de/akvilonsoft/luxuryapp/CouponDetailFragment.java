package de.akvilonsoft.luxuryapp;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * A fragment representing a single Coupon detail screen.
 * This fragment is either contained in a {@link CouponListActivity}
 * in two-pane mode (on tablets) or a {@link CouponDetailActivity}
 * on handsets.
 */
public class CouponDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String NAME = "item_name";
    public static final String BESCHREIBUNG_LANG = "item_beschreibung_lang";
    private String beschr = new String();

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CouponDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            DummyContent dc = new DummyContent();
            mItem = dc.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            beschr=getArguments().getString(BESCHREIBUNG_LANG);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.coupon_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.coupon_detail)).setText(mItem.details);
        }

        return rootView;
    }



    public class DummyContent {

        /**
         * An array of sample (dummy) items.
         */
        public  final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

        /**
         * A map of sample (dummy) items, by ID.
         */
        public  final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

        private static final int COUNT = 4;

         {
            // Add some sample items.
            for (int i = 1; i <= COUNT; i++) {
                addItem(createDummyItem(i));
            }
        }

        private  void addItem(DummyItem item) {
            ITEMS.add(item);
            ITEM_MAP.put(item.id, item);
        }

        private  DummyItem createDummyItem(int position) {
            return new DummyItem(String.valueOf(position), getArguments().getString(NAME), getArguments().getString(BESCHREIBUNG_LANG));
        }

        private  String makeDetails(int position) {
            /**       StringBuilder builder = new StringBuilder();
             builder.append("Details about Item: ").append(position);
             for (int i = 0; i < position; i++) {
             builder.append("\nMore details information here.");
             }
             return builder.toString();
             */
            return beschr;
        }

        /**
         * A dummy item representing a piece of content.
         */
        public  class DummyItem {
            public final String id;
            public final String content;
            public final String details;

            public DummyItem(String id, String content, String details) {
                this.id = id;
                this.content = content;
                this.details = details;
            }

            @Override
            public String toString() {
                return content;
            }
        }


    }
}
