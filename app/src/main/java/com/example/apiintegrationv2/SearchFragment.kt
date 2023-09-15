package com.example.apiintegrationv2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url =
            "https://api.currentsapi.services/v1/search?apiKey=uUsfvL9NAP16WDMCiEtHyvUK9UKY2-Jh2y5FjCGCPKtT5n9I&language=" + MainActivity.language + "&keywords=" + MenuFragment.searchTag
        val rq: RequestQueue = Volley.newRequestQueue(context)
        Log.d("robert", url)


        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { res ->

                val news = res.getJSONArray("news")
                if (news.length() < 1) {
                    val frag = NewsFragment()


                    val bundle = Bundle()
                    bundle.putString("headline", "Couldn't find any articles")
                    bundle.putString("article", "")
                    bundle.putString("url", "")
                    frag.arguments = bundle
                    val fm: FragmentManager = (activity as MainActivity).supportFragmentManager
                    fm.beginTransaction().add(R.id.searchFragmentContainer, frag).commit()
                }
                for (i in 0 until news.length()) {
                    val frag = NewsFragment()


                    val bundle = Bundle()
                    bundle.putString("headline", news.getJSONObject(i).getString("title"))
                    bundle.putString("article", news.getJSONObject(i).getString("description"))
                    bundle.putString("url", news.getJSONObject(i).getString("url"))
                    frag.arguments = bundle
                    val fm: FragmentManager = (activity as MainActivity).supportFragmentManager
                    fm.beginTransaction().add(R.id.searchFragmentContainer, frag).commit()
                }


            },
            { err ->
                Log.e("Robert", err.toString())
                val frag = NewsFragment()


                val bundle = Bundle()
                bundle.putString("headline", "Couldn't find any articles")
                bundle.putString("article", "")
                bundle.putString("url", "")
                frag.arguments = bundle
                val fm: FragmentManager = (activity as MainActivity).supportFragmentManager
                fm.beginTransaction().add(R.id.searchFragmentContainer, frag).commit()

            }
        ).setRetryPolicy(DefaultRetryPolicy(3000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))

        rq.add(jsonRequest)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}