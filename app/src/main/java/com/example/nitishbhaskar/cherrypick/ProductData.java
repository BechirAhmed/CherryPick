package com.example.nitishbhaskar.cherrypick;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nitish on 4/18/2016.
 */
public class ProductData {
    Firebase mref;
    MyFirebaseRecyclerAdapter myFirebaseRecyclerAdapter;
    Context mContext;

    List<Map<String,?>> productList;
    List<Map<String,?>> filteredProductList;

    public List<Map<String, ?>> getProductList() {
        return productList;
    }

    public int getSize(){
        return productList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < productList.size()){
            return (HashMap) productList.get(i);
        } else return null;
    }

    public ProductData(){
        String description;
        mref = new Firebase("https://cherrypick.firebaseio.com/Productdata");
        String price;
        String datePostedOn;
        String productId;
        String location;
        String productName;
        productList = new ArrayList<Map<String,?>>();
        mContext = null;
        myFirebaseRecyclerAdapter = null;
    }

    public void setAdapter(MyFirebaseRecyclerAdapter adapter){
        myFirebaseRecyclerAdapter = adapter;
    }

    public void setContext(Context context){
        mContext = context;
    }

    public void initializeDataFromCloud(){
        productList.clear();

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Child Added", dataSnapshot.toString());
                HashMap<String, ?> product = (HashMap<String, ?>) dataSnapshot.getValue();
                onItemAddedToCloud(product);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("Child Changed", dataSnapshot.toString());
                HashMap<String, String> product = (HashMap<String, String>) dataSnapshot.getValue();
                onItemUpdatedInCloud(product);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("Child Removed", dataSnapshot.toString());
                HashMap<String, String> product = (HashMap<String, String>) dataSnapshot.getValue();
                onItemRemovedFromCloud(product);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void removeItemFromServer(Map<String, ?> product){
        if(product!=null){
            String id = (String) product.get("productId");
            mref.child(id).removeValue();
        }
    }

    public void addItemToServer(Map<String, ?> product){
        if(product!=null){
            String id = (String) product.get("productId");
            mref.child(id).setValue(product);
        }
    }

    private void onItemAddedToCloud(HashMap item){
        productList.add(item);
        //Toast.makeText(mContext, "Item Added:" + item.get("productName"), Toast.LENGTH_SHORT).show();
    }

    private void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id = (String) item.get("productId");
        for(int i=0;i< productList.size();i++){
            HashMap movie = (HashMap) productList.get(i);
            String mid = (String) movie.get("productId");
            if(mid.equals(id)){
                position = i;
                break;
            }
        }
        if(position !=-1) {
            productList.remove(position);
            Log.d("NotifyRemoved", id);
            //Toast.makeText(mContext, "Item Removed:" + item.get("productName"), Toast.LENGTH_SHORT).show();
        }

    }

    private void onItemUpdatedInCloud(HashMap item){
        String id = (String) item.get("productId");
        for(int i=0;i< productList.size();i++){
            HashMap movie = (HashMap) productList.get(i);
            String mid = (String) movie.get("productId");
            if(mid.equals(id)){
                productList.remove(i);
                productList.add(i, item);
                Log.d("NotifyChanged", id);
                if(myFirebaseRecyclerAdapter!=null)
                    myFirebaseRecyclerAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    //code to use filter option
    public void flushFilter(){
        filteredProductList=new ArrayList<Map<String,?>>();
        filteredProductList.addAll(getProductList());
        myFirebaseRecyclerAdapter.notifyDataSetChanged();
    }

    public void setFilter(String queryText) {

        filteredProductList=new ArrayList<Map<String,?>>();
        for (Map<String,?> item: getProductList()) {
            if (item.get("productName").toString().contains(queryText.toLowerCase()))
                filteredProductList.add(item);
        }
        myFirebaseRecyclerAdapter.notifyDataSetChanged();
    }//end of filter option code

    public int findFirst(String query){

        for(int i=0;i<getSize();i++){
            HashMap hm = (HashMap)getProductList().get(i);
            if(((String)hm.get("productName")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }


}
