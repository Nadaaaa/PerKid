package com.example.xdev.perkid.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.activities.MainActivity;
import com.example.xdev.perkid.adapters.SocialMediaAdapter;
import com.example.xdev.perkid.models.History;
import com.example.xdev.perkid.models.SocialMedia;
import com.example.xdev.perkid.models.SocialMediaAdapterModel;
import com.example.xdev.perkid.models.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class KidsFragment extends Fragment implements SocialMediaAdapter.ListItemClickListener {


    FloatingActionButton fab_call;
    FloatingActionButton fab_callParent;
    FloatingActionButton fab_call_911;

    TextView textView_callParent;
    TextView textView_call_911;

    RecyclerView rv_socialMedia;
    List<SocialMediaAdapterModel> socialMediaList;
    SocialMediaAdapter socialMediaAdapter;
    Realm realm;
    String currentUserName;
    String currentLocation;
    String parentMobileNumber;

    public KidsFragment() {
        // Required empty public constructor
    }

    public static KidsFragment newInstance(String username) {
        KidsFragment fragment = new KidsFragment();
        Bundle kidsBundle = new Bundle();
        kidsBundle.putString("KIDS_USER_NAME", username);
        fragment.setArguments(kidsBundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kids, container, false);

        realm = Realm.getDefaultInstance();

        currentUserName = getArguments().getString("KIDS_USER_NAME");

        fab_call = rootView.findViewById(R.id.fab_call);
        fab_callParent = rootView.findViewById(R.id.fab_callParent);
        fab_call_911 = rootView.findViewById(R.id.fab_call_911);

        textView_callParent = rootView.findViewById(R.id.text_callParent);
        textView_call_911 = rootView.findViewById(R.id.text_call911);

        rv_socialMedia = rootView.findViewById(R.id.rv_socialMedia);
        socialMediaList = new RealmList<>();

        getSocialMediaView();
        //List get when user name;
        socialMediaAdapter = new SocialMediaAdapter(getContext(), socialMediaList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rv_socialMedia.setLayoutManager(gridLayoutManager);
        rv_socialMedia.setHasFixedSize(false);
        rv_socialMedia.setAdapter(socialMediaAdapter);

        onClickCall();
        onClickCall911();
        onClickCallParent();

        getCurrentParentMobileNumber();

        ((MainActivity) getActivity()).isPermissionGranted();

        getKidCurrentLocation();

        return rootView;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        if (((MainActivity) getActivity()).isPermissionGranted()) {

            getKidCurrentLocation();

            sumUpNumberOFClicks(socialMediaList.get(clickedItemIndex).getName());

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
            String datetime = dateFormat.format(c.getTime());

            addToHistory(socialMediaList.get(clickedItemIndex).getName(), datetime);
        }
    }

    void sumUpNumberOFClicks(final String socialMediaName) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                SocialMedia socialMedia = realm.where(SocialMedia.class)
                        .equalTo("username", currentUserName)
                        .equalTo("name", socialMediaName)
                        .findFirst();

                int visitTimes = socialMedia.getVisitTimes();
                visitTimes++;
                socialMedia.setVisitTimes(visitTimes);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                openBrowser(socialMediaName);
                getSocialMediaView();
                // Transaction was a success.
                // Toast.makeText(MainActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                //Toast.makeText(MainActivity.this, "EditError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getSocialMediaView() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                socialMediaList.clear();
                RealmResults<SocialMedia> realmResults = realm.where(SocialMedia.class)
                        .equalTo("username", currentUserName)
                        .findAll();

                for (int i = 0; i < realmResults.size(); i++) {
                    SocialMediaAdapterModel socialMediaAdapterModel =
                            new SocialMediaAdapterModel(realmResults.get(i).getUsername(),
                                    realmResults.get(i).getVisitTimes(),
                                    realmResults.get(i).getName());
                    socialMediaList.add(socialMediaAdapterModel);
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                socialMediaAdapter.notifyDataSetChanged();
                // Transaction was a success.
                // Toast.makeText(MainActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                //Toast.makeText(MainActivity.this, "EditError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addToHistory(final String socialMediaName, final String socialMediaTimeAndDate) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                History history = realm.createObject(History.class);
                history.setKidUsername(currentUserName);
                history.setSocialMediaName(socialMediaName);
                history.setSocialMediaTimeAndData(socialMediaTimeAndDate);
                history.setCurrentLocation(currentLocation);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

    }

    void onClickCall() {
        fab_call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (fab_call_911.getVisibility() == View.GONE) {
                    fab_call_911.setVisibility(View.VISIBLE);
                    textView_call_911.setVisibility(View.VISIBLE);
                    textView_callParent.setVisibility(View.VISIBLE);
                    fab_callParent.setVisibility(View.VISIBLE);
                } else {
                    fab_call_911.setVisibility(View.GONE);
                    textView_call_911.setVisibility(View.GONE);
                    textView_callParent.setVisibility(View.GONE);
                    fab_callParent.setVisibility(View.GONE);
                }
            }
        });
    }

    void onClickCall911() {
        fab_call_911.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).isPermissionGranted()) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "911"));
                    startActivity(intent);
                }
            }
        });
    }

    void onClickCallParent() {
        fab_callParent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).isPermissionGranted()) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + parentMobileNumber));
                    startActivity(intent);
                }
            }
        });
    }

    void openBrowser(String socialMediaName) {
        String URL = "";
        if (socialMediaName.equals("youtube"))
            URL = "https://www.youtube.com/";

        if (socialMediaName.equals("facebook"))
            URL = "https://www.facebook.com/";

        if (socialMediaName.equals("instagram"))
            URL = "https://www.instagram.com/";

        if (socialMediaName.equals("pinterest"))
            URL = "https://www.pinterest.com/";

        if (socialMediaName.equals("snapchat"))
            URL = "https://www.snapchat.com/";

        if (socialMediaName.equals("twitter"))
            URL = "https://twitter.com/";

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
    }

    void getKidCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            Task lastLocation = fusedLocationProviderClient.getLastLocation();
            lastLocation.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d("getKidCurrentLocation", "onComplete: Found Location");
                        Location location = (Location) task.getResult();
                        if (location != null) {
                            getAddress(location.getLatitude(), location.getLongitude());
                            Log.d("getKidCurrentLocation", String.valueOf(location.getLatitude()));
                        } else {
                            Toast.makeText(getContext(), "Enable Location Information", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "nill", Toast.LENGTH_SHORT).show();
                        Log.d("getKidCurrentLocation", "onComplete: Current Location Is Null");
                        Toast.makeText(getActivity(), "Unable To Get Current Location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e("getKidCurrentLocation", "getDeviceLocation: SecurityException" + e.getMessage());
        }
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            currentLocation = obj.getAddressLine(0);
            // add = add + "\n" + obj.getLocality();
            /*add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getSubThoroughfare();*/

            Log.v("IGA", "Address" + currentLocation);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void getCurrentParentMobileNumber() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                User user = realm.where(User.class)
                        .equalTo("isLogged", true)
                        .findFirst();
                parentMobileNumber = user.getParentMobileNumber();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                // Toast.makeText(MainActivity.this, "EditSuccess", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                //Toast.makeText(MainActivity.this, "EditError", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


