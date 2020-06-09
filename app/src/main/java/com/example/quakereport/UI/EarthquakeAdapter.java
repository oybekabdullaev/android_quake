package com.example.quakereport.UI;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.quakereport.Model.Earthquake;
import com.example.quakereport.R;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        Earthquake currentEarthquake = getItem(position);

        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,
                    parent, false);
        }

        TextView magnitudeTextView = listItem.findViewById(R.id.earthquakeListItemMagnitudeTextView);
        TextView priamaryLocationTextView = listItem.findViewById(R.id.earthquakeListItemPrimaryLocationTextView);
        TextView locationOffsetTextView = listItem.findViewById(R.id.earthquakeListItemLocationOffsetTextView);
        TextView dateTextView = listItem.findViewById(R.id.earthquakeListItemDateTextView);
        TextView timeTextView = listItem.findViewById(R.id.earthquakeListItemTimeTextView);

        String originalLocation = currentEarthquake.getLocation();
        String locationOffset;
        String primaryLocation;

        String magnitudeString = formatMagnitude(currentEarthquake.getMagnitude());
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        if (originalLocation.contains(" of ")) {
            String[] parts = originalLocation.split(" of ");
            locationOffset = parts[0] + " of";
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        long timeInMs = currentEarthquake.getTimeInMilliseconds();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeInMs), ZoneId.systemDefault());
        String date = formatDate(dateTime);
        String time = formatTime(dateTime);

        magnitudeTextView.setText(magnitudeString);
        priamaryLocationTextView.setText(primaryLocation);
        locationOffsetTextView.setText(locationOffset);
        dateTextView.setText(date);
        timeTextView.setText(time);

        return listItem;
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL dd, yyyy");
        return dateTime.format(formatter);
    }

    private String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        return decimalFormatter.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
