package fr.pedocactus.lequipetest.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fr.pedocactus.lequipetest.R
import fr.pedocactus.lequipetest.ui.presentation.VideoPresentationModel


class VideoAdapter(val context: Context?) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {


    private val TAG = "VideoAdapter"
    private var videoPresentationModels: ArrayList<VideoPresentationModel> = arrayListOf()
    var imageWidth = 0
    var imageHeight = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoPresentationModels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presentationModel = videoPresentationModels[position]
        holder.title.text = presentationModel.title
        holder.sport.text = presentationModel.sport
        if (context != null) {
            if (imageWidth == 0) {
                if (holder.imageView.width == 0) {
                    holder.imageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                        override fun onGlobalLayout() {
                            holder.imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            Log.d(TAG, "view dimensions retreived from view rendering listener")
                            imageWidth = holder.imageView.width
                            imageHeight = holder.imageView.height

                            val url = presentationModel.imageUrl.replace("{width}", "" + imageWidth).replace("{height}", "" + imageHeight)
                            Glide.with(context).load(url).apply(RequestOptions().placeholder(ColorDrawable(Color.GRAY))).into(holder?.imageView)
                        }

                    })
                } else {
                    Log.d(TAG, "view dimensions retreived from already rendered view")

                    imageWidth = holder.imageView.width
                    imageHeight = holder.imageView?.height

                    val url = presentationModel.imageUrl.replace("{width}", "" + imageWidth).replace("{height}", "" + imageHeight)
                    Glide.with(context).load(url).apply(RequestOptions().placeholder(ColorDrawable(Color.GRAY))).into(holder?.imageView)

                }
            } else {
                Log.d(TAG, "view dimensions retreived from already calculated dimensions")
                val url = presentationModel.imageUrl.replace("{width}", "" + imageWidth).replace("{height}", "" + imageHeight)
                Glide.with(context).load(url).apply(RequestOptions().placeholder(ColorDrawable(Color.GRAY))).into(holder?.imageView)
            }

        }

    }

    fun populateData(presentationModels: List<VideoPresentationModel>) {
        videoPresentationModels.clear()
        videoPresentationModels.addAll(presentationModels)
        notifyDataSetChanged()

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val sport: TextView = view.findViewById(R.id.sportTitle)
        val imageView: ImageView = view.findViewById(R.id.imageView)


    }

//    inline fun ViewTreeObserver.waitForLayout(crossinline f: () -> Unit) = {
//        this.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//
//            override fun onGlobalLayout() {
//                removeOnGlobalLayoutListener(this)
//                f()
//            }
//
//        })
//
//    }

}