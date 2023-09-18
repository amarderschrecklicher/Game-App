package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CommentsAdapter (
    private var impression: List<UserImpression>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

       return if(viewType == 0) {
           val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.bar_review, parent, false)
                 BarViewHolder(view)
        }else {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.comment_review, parent, false)
            ReviewViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(impression[position]::class.simpleName.equals("UserRating"))0
        else if (impression[position]::class.simpleName.equals("UserReview"))1
        else 2
    }

    override fun getItemCount(): Int = impression.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position)==0){
            (holder as BarViewHolder)
            var bar : UserRating = impression[position] as UserRating
            holder.gameBar.rating = bar.rating.toFloat()
            holder.userName.text = bar.username
        }else{
            (holder as ReviewViewHolder)
            var review : UserReview = impression[position] as UserReview
            holder.gameReview.text = review.review
            holder.userName.text = review.username
        }
    }

    fun updateImpression(impression:List<UserImpression>) {
        this.impression = impression
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.username_textview)
        val gameReview : TextView = itemView.findViewById(R.id.review_textview)
    }
    inner class BarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.username_textview)
        val gameBar: RatingBar = itemView.findViewById(R.id.rating_bar)
    }
}