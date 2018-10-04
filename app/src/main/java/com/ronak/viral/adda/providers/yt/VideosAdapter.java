package com.ronak.viral.adda.providers.yt;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronak.viral.adda.R;
import com.ronak.viral.adda.providers.yt.api.object.Video;
import com.squareup.picasso.Picasso;

/**
 * Setting our custom listview rows with the retrieved filtered_videos
 */
public class VideosAdapter extends ArrayAdapter<Video> implements Filterable {


    private List<Video> original_videos = null;
    List<Video> filtered_videos = null;


    private LayoutInflater mInflater;
    private Context mContext;
    private String TAG_TOP = "TOP";

    private ItemFilter mFilter = new ItemFilter();

    public VideosAdapter(Context context, List<Video> filtered_videos) {
        super(context, 0, filtered_videos);
        this.mContext = context;
        this.filtered_videos = filtered_videos;
        this.original_videos = filtered_videos;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setOriginal_videos(List<Video> videos) {
        this.filtered_videos = new ArrayList<>();
        this.original_videos = new ArrayList<>();
        this.filtered_videos = videos;
        this.original_videos = videos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filtered_videos != null ? filtered_videos.size() : 0;
    }

    @Override
    public Video getItem(int position) {
        return filtered_videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Video video = filtered_videos.get(position);

        //if it is the first item, give a special treatment.
        if (position == 0 && null != video) {
            convertView = mInflater.inflate(R.layout.listview_highlight, null);
            Picasso.with(mContext).load(video.getImage()).into((ImageView) convertView.findViewById(R.id.imageViewHighlight));

            ((TextView) convertView.findViewById(R.id.textViewHighlight)).setText(video.getTitle());
            convertView.setTag(TAG_TOP);
            return convertView;
        }

        ViewHolder holder;

        if (convertView == null || convertView.getTag().equals(TAG_TOP)) {
            convertView = mInflater.inflate(R.layout.fragment_youtube_row, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.userVideoTitleTextView);
            holder.date = (TextView) convertView.findViewById(R.id.userVideoDateTextView);
            holder.thumb = (ImageView) convertView.findViewById(R.id.userVideoThumbImageView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.thumb.setImageDrawable(null);

        if (video != null) {

            // Set the title for the video
            holder.title.setText(video.getTitle());
            // Set the date for the video
            holder.date.setText(video.getUpdated());

            //setting the image
            Picasso.with(mContext).load(video.getThumbUrl()).into(holder.thumb);

        }
        return convertView;

    }

    static class ViewHolder {
        TextView title;
        TextView date;
        ImageView thumb;
        int position;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Video> list = original_videos;

            int count = list.size();
            final ArrayList<Video> nlist = new ArrayList<Video>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getTitle().toLowerCase();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_videos = (ArrayList<Video>) results.values;
            notifyDataSetChanged();
        }

    }

}