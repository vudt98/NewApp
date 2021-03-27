package com.example.newapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.newapp.bookmarks.BookmarksFragment
import com.example.newapp.breakingnew.BreakingNewsFragment
import com.example.newapp.databinding.ActivityMainBinding
import com.example.newapp.searchnew.SearchNewsFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var breakingNewsFragment: BreakingNewsFragment
    private lateinit var searchNewsFragment: SearchNewsFragment
    private lateinit var bookmarksFragment: BookmarksFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(
            breakingNewsFragment,
            searchNewsFragment,
            bookmarksFragment
        )

    private var selectedIndex = 0

    private val selectedFragment get() = fragments[selectedIndex]

    private fun selectFragment(selectedFragment: Fragment) {

        var transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (selectedFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit()

        title = when (selectedFragment) {
            is BreakingNewsFragment -> getString(R.string.breaking_new)
            is SearchNewsFragment -> getString(R.string.search_new)
            is BookmarksFragment -> getString(R.string.book_marks)
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            breakingNewsFragment = BreakingNewsFragment()
            searchNewsFragment = SearchNewsFragment()
            bookmarksFragment = BookmarksFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, breakingNewsFragment, TAG_BREAKING_NEWS_FRAGMENT)
                .add(R.id.fragment_container, searchNewsFragment, TAG_SEARCH_NEWS_FRAGMENT)
                .add(R.id.fragment_container, bookmarksFragment, TAG_BOOK_MARKS_FRAGMENT)
                .commit()
        } else {
            breakingNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_BREAKING_NEWS_FRAGMENT) as BreakingNewsFragment
            searchNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_SEARCH_NEWS_FRAGMENT) as SearchNewsFragment
            bookmarksFragment =
                supportFragmentManager.findFragmentByTag(TAG_BOOK_MARKS_FRAGMENT) as BookmarksFragment

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)
        }
        selectFragment(selectedFragment)

        binding.navBottom.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_breaking -> breakingNewsFragment
                R.id.nav_search -> searchNewsFragment
                R.id.nav_bookmarks -> bookmarksFragment
                else -> throw IllegalArgumentException("Unexepted item")
            }
            selectFragment(fragment)
            true
        }

    }

    override fun onBackPressed() {
        if (selectedIndex != 0) {
            binding.navBottom.selectedItemId = R.id.nav_breaking
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_INDEX,selectedIndex)
    }


}

private const val TAG_BREAKING_NEWS_FRAGMENT = "TAG_BREAKING_NEWS_FRAGMENT"
private const val TAG_SEARCH_NEWS_FRAGMENT = "TAG_SEARCH_NEWS_FRAGMENT"
private const val TAG_BOOK_MARKS_FRAGMENT = "TAG_BOOK_MARKS_FRAGMENT"
private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"


