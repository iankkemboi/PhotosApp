package com.dkb.photosapp.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.dkb.photosapp.R
import com.dkb.photosapp.data.models.PhotosListResponse


class PhotosListAdapter(
    private val context: Context,
    private val onItemClick: (photo: PhotosListResponse) -> Unit,
    private val photoList: List<PhotosListResponse>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        val viewItem: View = inflater.inflate(R.layout.photo_item, parent, false)
        viewHolder = photoViewHolder(viewItem)


        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = photoList!![position]
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick.invoke(photo)
            }
        }
        val photoViewHolder = holder as photoViewHolder
        photoViewHolder.photoTitle.text = photo.title

        val url = GlideUrl(
            photo.thumbnailUrl, LazyHeaders.Builder()
                .addHeader("User-Agent", "5")
                .build()
        )

        Glide.with(context).load(url)
            .apply(RequestOptions.centerCropTransform())
            .into(photoViewHolder.photoImage)


    }


    override fun getItemCount(): Int {
        return if (photoList == null) 0 else photoList!!.size
    }


    class photoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoTitle: TextView
        val photoImage: ImageView

        init {
            photoTitle = itemView.findViewById(R.id.photo_title)
            photoImage = itemView.findViewById(R.id.photo_poster) as ImageView
        }
    }


    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }


}