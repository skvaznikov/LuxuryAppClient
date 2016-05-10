package de.akvilonsoft.luxuryapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * An activity representing a list of Coupons. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CouponDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CouponListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    List<String> names = new ArrayList<String>();
  //  List<Coupon> coupons = new ArrayList<>();
    private String name;
    public String town = new String();
    JSONArray jArray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.coupon_list);
        assert recyclerView != null;
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            town = extras.getString("city").toString();
        }

        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.coupon_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


  //      saveData();

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(getObjects()));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Coupon> mVal;

        public SimpleItemRecyclerViewAdapter(List<Coupon> items) {
            mVal = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.coupon_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final String  name = mVal.get(position).getName();
            final String  bez_lang = mVal.get(position).getBeschreibung_lang();
              holder.mItem = mVal.get(position);
             // holder.mIdView.setText(mVal.get(position).getId().toString());
          //  mVal.get(position).getName() + "  " +
              holder.mContentView.setText( mVal.get(position).getBeschreibung() + "  " + mVal.get(position).getName());
            holder.mContentView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString(CouponDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
                        CouponDetailFragment fragment = new CouponDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.coupon_detail_container, fragment)
                                .commit();
                    } else {

                        Context context = v.getContext();
                        Intent intent = new Intent(context, CouponDetailActivity.class);
                        intent.putExtra(CouponDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
                        intent.putExtra(CouponDetailFragment.NAME, name);
                        intent.putExtra(CouponDetailFragment.BESCHREIBUNG_LANG, bez_lang);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mVal.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
           public Coupon mItem;
            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
    private void saveData() {
        DatabaseHandler db = new DatabaseHandler(this);

        db.addCoupon(new Coupon("Rollex Schweiz", "Uhren von feinsten"));
        db.addCoupon(new Coupon("Luxury3", "Handtaschen"));
        db.addCoupon(new Coupon("Luxury 2", "Sonnenbrillen"));
        db.addCoupon(new Coupon("Luxury 3", "Schuhe"));
    }

   // private List<Coupon> loadData() {

        //   DatabaseHandler db = new DatabaseHandler(this);
        //   Coupon coupon = db.getCoupon(2);
        //   coupons = db.getAllCoupons();
        //   ListIterator<Coupon> iter = coupons.listIterator();
        //   while (iter.hasNext()) {
        //       names.add(iter.next().getBeschreibung());
        //   }
      //  new RetrieveFeedTask().execute();
      //  int i;
      //  for(i=0; i < jArray.length(); i++) {

      //      JSONObject jObject = jArray.getJSONObject(i);

       //     String name = jObject.getString("name");
       //     String desc = jObject.getString("bezeichnung");
            // String add = jObject.getString("additional");
            // String loc = jObject.getString("location");
       //     coupons.add(new Coupon(i+1, name, desc));

       // }
       // return coupons;
    //}
    private void searchLocation() {

        //   DatabaseHandler db = new DatabaseHandler(this);
        //   Coupon coupon = db.getCoupon(2);
        //   coupons = db.getAllCoupons();
        //   ListIterator<Coupon> iter = coupons.listIterator();
        //   while (iter.hasNext()) {
        //       names.add(iter.next().getBeschreibung());
        //   }
      //  new RetrieveFeedTask().execute();
    }
    protected List<Coupon> getObjects() {
        List<Coupon> coupons = new ArrayList<>();
        RetrieveFeedTask rft = new RetrieveFeedTask();

        try {
            if (town.equals("") || town.toString()=="" ){
                jArray = rft.execute(new URL("http://10.0.2.2:8080/LuxuryApp/rest/demo/allCoupons")).get(5, TimeUnit.SECONDS);
            } else {
                jArray = rft.execute(new URL("http://10.0.2.2:8080/LuxuryApp/rest/demo/localeCoupons?city=" + town)).get(5, TimeUnit.SECONDS);
            }

            //jArray = rft.getjArray();
            // new RetrieveFeedTask(new RetrieveFeedTask.AsyncResponse() {
            //     @Override
            //     public void processFinish(JSONArray output) {
            //         jArray = output;
            //     }
            // }).execute();
            int i;
            for (i = 0; i < jArray.length(); i++) {

                JSONObject jObject = null;

                jObject = jArray.getJSONObject(i);


                String name = jObject.getString("name");
                System.out.println(name);
                String desc = jObject.getString("bezeichnung");
                String desc_long = jObject.getString("bezeichnung_lang");
                // String add = jObject.getString("additional");
                // String loc = jObject.getString("location");
                coupons.add(new Coupon(i + 1, name, desc, desc_long));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return coupons;
    }


}
