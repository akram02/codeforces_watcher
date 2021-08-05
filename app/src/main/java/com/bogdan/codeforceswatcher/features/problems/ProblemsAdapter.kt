package com.bogdan.codeforceswatcher.features.problems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.codeforceswatcher.R
import io.xorum.codeforceswatcher.features.problems.models.Problem
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsRequests
import io.xorum.codeforceswatcher.redux.store
import kotlinx.android.synthetic.main.view_problem_item.view.*

class ProblemsAdapter(
        private val context: Context,
        private val itemClickListener: (Problem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<Problem> = mutableListOf()
    private var isFavouriteStatus = false

    override fun getItemCount() = items.size + if (items.isEmpty()) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {
                STUB_ALL_PROBLEMS_VIEW_TYPE -> {
                    val layout = LayoutInflater.from(context).inflate(R.layout.view_all_problems_stub, parent, false)
                    StubViewHolder(layout)
                }
                STUB_FAVOURITE_PROBLEMS_VIEW_TYPE -> {
                    val layout = LayoutInflater.from(context).inflate(R.layout.view_favourite_problems_stub, parent, false)
                    StubViewHolder(layout)
                }
                else -> {
                    val layout = LayoutInflater.from(context).inflate(R.layout.view_problem_item, parent, false)
                    ProblemViewHolder(layout)
                }
            }

    override fun getItemViewType(position: Int): Int {
        return when {
            items.isEmpty() && store.state.problems.isFavourite -> STUB_FAVOURITE_PROBLEMS_VIEW_TYPE
            items.isEmpty() -> STUB_ALL_PROBLEMS_VIEW_TYPE
            else -> PROBLEM_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (items.isEmpty()) return

        val problemViewHolder = viewHolder as ProblemViewHolder
        with(problemViewHolder) {
            with(items[adapterPosition]) {
                tvProblemName.text = title
                tvContestName.text = subtitle
                ivFavourite.setColorFilter(ContextCompat.getColor(
                        context, if (isFavourite) R.color.colorAccent else R.color.dark_gray)
                )

                onClickListener = { itemClickListener(this) }
                onFavouriteClickListener = {
                    store.dispatch(ProblemsRequests.ChangeStatusFavourite(this.copy()))
                    when (isFavouriteStatus) {
                        false -> notifyItemChanged(adapterPosition).also { changeProblem(this) }
                        true -> notifyItemRemoved(adapterPosition).also { removeProblem(this) }
                    }
                }
            }
        }
    }

    private fun changeProblem(problem: Problem) {
        problem.isFavourite = problem.isFavourite.not()
    }

    private fun removeProblem(problem: Problem) {
        items.remove(problem)
    }

    fun setItems(problemsList: List<Problem>, isFavourite: Boolean) {
        this.isFavouriteStatus = isFavourite
        items = problemsList.toMutableList()
        notifyDataSetChanged()
    }

    class ProblemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvProblemName: TextView = view.tvProblemName
        val tvContestName: TextView = view.tvContestName
        val ivFavourite: ImageView = view.ivFavourite

        var onClickListener: (() -> Unit)? = null
        var onFavouriteClickListener: (() -> Unit)? = null

        init {
            view.setOnClickListener { onClickListener?.invoke() }
            view.ivFavourite.setOnClickListener { onFavouriteClickListener?.invoke() }
        }
    }

    data class StubViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    companion object {
        const val STUB_ALL_PROBLEMS_VIEW_TYPE = 0
        const val STUB_FAVOURITE_PROBLEMS_VIEW_TYPE = 2
        const val PROBLEM_VIEW_TYPE = 1
    }
}
