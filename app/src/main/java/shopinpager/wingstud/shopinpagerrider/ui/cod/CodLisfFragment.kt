package shopinpager.wingstud.shopinpagerrider.ui.cod

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import shopinpager.wingstud.shopinpagerrider.R
import shopinpager.wingstud.shopinpagerrider.databinding.FragmentCodLisfBinding
import shopinpager.wingstud.shopinpagerrider.response.CODResponse
import shopinpager.wingstud.shopinpagerrider.rest.network.RestClient
import shopinpager.wingstud.shopinpagerrider.util.FixedGridLayoutManager
import kotlinx.android.synthetic.main.fragment_cod_lisf.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shopinpager.wingstud.shopinpagerrider.account.AccountManager

/**
 * A simple [Fragment] subclass.
 */
class CodLisfFragment : Fragment() {
    internal var scrollX = 0
    lateinit var codAdapter: CODAdapter
    val session by lazy { AccountManager.getUserAccount()!! }

    lateinit var binding: FragmentCodLisfBinding;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cod_lisf, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Cod List"
        val manager = FixedGridLayoutManager()
        binding.codRv.layoutManager = manager
        binding.codRv.addItemDecoration(
                DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                )
        )

        binding.codRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scrollX += dx
                headerScroll.scrollTo(scrollX, 0)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        codList()

        return binding.getRoot()
    }

    private fun codList() {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["user_id"] = session.deliverBoyId
        Log.i("user_id",hashMap.toString())

        RestClient.getInstance().apiInterface.codList(hashMap).enqueue(object : Callback<CodResponse> {
            override fun onFailure(call: Call<CodResponse>, t: Throwable) {
//                progress_bar.visibility = View.GONE
                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<CodResponse>, response: Response<CodResponse>) {
//                progress_bar.visibility = View.GONE
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body!!.status_code == 1) {
                        relEmptyWL.visibility = View.GONE
                        binding.codRv.visibility = View.VISIBLE
                        codAdapter = CODAdapter(context, body.cod_list)
                        binding.codRv.adapter = codAdapter
                    } else {
                        relEmptyWL.visibility = View.VISIBLE
                        binding.codRv.visibility = View.GONE
//                        Snackbar.make(root, body.message ?: "Opps! Something went wrong.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        })
    }


}
