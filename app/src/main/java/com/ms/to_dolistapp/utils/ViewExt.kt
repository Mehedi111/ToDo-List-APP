package com.ms.to_dolistapp.utils

import androidx.appcompat.widget.SearchView

/**
 * @AUTHOR: Mehedi Hasan
 * @DATE: 2/4/2021, Thu
 */

/*
* We don't wanna have this code in fragment that's why we implemented here,
* In SOLID -> The interface-segregation principle (ISP) states that
* no client should be forced to depend on methods it does not use
*
* [inline] used for efficiency, because kotlin generates separates object each time for higherOrderFunc that creates
* runtimeOverHeads
* [inline] avoid call each time just copies values what we define in lambda argument
* [crossinline] can help us to avoid the "non-local returns"
* */
inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }

    })
}