package com.bogdan.codeforceswatcher.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.bogdan.codeforceswatcher.CwApp
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.contests.models.Contest
import io.xorum.codeforceswatcher.util.Settings

class Prefs(private val context: Context) : Settings {

    override fun writeUserAccount(userAccount: UserAccount?) {
        val editor = getDefaultPrefs().edit()

        userAccount?.let {
            editor.putString(KEY_USER_ACCOUNT, it.toJson())
        } ?: editor.remove(KEY_USER_ACCOUNT)

        editor.apply()
    }

    override fun readUserAccount(): UserAccount? {
        return getDefaultPrefs().getString(KEY_USER_ACCOUNT, null)?.let {
            UserAccount.fromJson(it)
        }
    }

    override fun writeLastPinnedPostLink(pinnedPostLink: String) {
        val editor = getDefaultPrefs().edit()
        editor.putString(KEY_PINNED_POST, pinnedPostLink)
        editor.apply()
    }

    override fun readLastPinnedPostLink(): String {
        val defaultPrefs = getDefaultPrefs()
        return defaultPrefs.getString(KEY_PINNED_POST, "").orEmpty()
    }

    override fun readSpinnerSortPosition(): Int {
        val defaultPrefs = getDefaultPrefs()
        return (defaultPrefs.getString(KEY_SPINNER_SORT_POSITION, "0") ?: "0").toInt()
    }

    override fun writeSpinnerSortPosition(spinnerSortPosition: Int) {
        val editor = getDefaultPrefs().edit()
        editor.putString(KEY_SPINNER_SORT_POSITION, spinnerSortPosition.toString())
        editor.apply()
    }

    override fun readProblemsIsFavourite(): Boolean {
        val defaultPrefs = getDefaultPrefs()
        return defaultPrefs.getBoolean(KEY_PROBLEMS_IS_FAVOURITE, false)
    }

    override fun writeProblemsIsFavourite(isFavourite: Boolean) {
        val editor = getDefaultPrefs().edit()
        editor.putBoolean(KEY_PROBLEMS_IS_FAVOURITE, isFavourite)
        editor.apply()
    }

    override fun readContestsFilters(): Set<String> {
        val defaultPrefs = getDefaultPrefs()
        return defaultPrefs.getStringSet(KEY_CONTESTS_FILTERS, Contest.Platform.defaultFilterValueToSave).orEmpty()
    }

    override fun writeContestsFilters(filters: Set<String>) {
        val editor = getDefaultPrefs().edit()
        editor.putStringSet(KEY_CONTESTS_FILTERS, filters)
        editor.apply()
    }

    private fun getDefaultPrefs(): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    override fun readProblemsTags(): List<String> {
        val defaultPrefs = getDefaultPrefs()
        return defaultPrefs.getStringSet(KEY_PROBLEMS_TAGS, setOf()).orEmpty().toList()
    }

    override fun writeProblemsTags(tags: List<String>) {
        val editor = getDefaultPrefs().edit()
        editor.putStringSet(KEY_PROBLEMS_TAGS, tags.toSet())
        editor.apply()
    }

    override fun readProblemsSelectedTags(): Set<String> {
        val defaultPrefs = getDefaultPrefs()
        return defaultPrefs.getStringSet(KEY_PROBLEMS_SELECTED_TAGS, setOf()).orEmpty()
    }

    override fun writeProblemsSelectedTags(tags: Set<String>) {
        val editor = getDefaultPrefs().edit()
        editor.putStringSet(KEY_PROBLEMS_SELECTED_TAGS, tags)
        editor.apply()
    }

    companion object {

        private const val KEY_SPINNER_SORT_POSITION = "key_counter"
        private const val KEY_PROBLEMS_IS_FAVOURITE = "key_problems_is_favourite"
        private const val KEY_CONTESTS_FILTERS = "key_contests_filters"
        private const val KEY_PINNED_POST = "key_pinned_post"
        private const val KEY_USER_ACCOUNT = "key_user_account"
        private const val KEY_PROBLEMS_TAGS = "key_problems_tags"
        private const val KEY_PROBLEMS_SELECTED_TAGS = "key_problems_selected_tags"

        @SuppressLint("StaticFieldLeak")
        private val prefs: Prefs = Prefs(CwApp.app)

        fun get() = prefs
    }
}
