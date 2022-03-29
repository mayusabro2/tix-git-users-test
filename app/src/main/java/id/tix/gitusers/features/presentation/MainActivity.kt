package id.tix.gitusers.features.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.DialogCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.tix.gitusers.R
import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.databinding.ActivityMainBinding
import id.tix.gitusers.databinding.DialogUserBinding
import id.tix.gitusers.features.presentation.users.UserViewModel
import id.tix.gitusers.features.presentation.users.adapters.LoadingStateHeaderAdapter
import id.tix.gitusers.features.presentation.users.adapters.UserAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : UserViewModel by viewModels()
    val pagingAdapter = UserAdapter {
        viewModel.userId.value = it?.login?:""
        UserDialog(viewModel).show(supportFragmentManager, "User Detail Dialog")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.swiperRefresh.run {
            setOnRefreshListener {
                isRefreshing = false
                pagingAdapter.refresh()

            }
        }
        pagingAdapter.run {
            addLoadStateListener { loadStates ->
                header.loadState = loadStates.refresh
                footer.loadState = loadStates.append
            }
        }
        binding.adapter = pagingAdapter.withLoadStates()
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.getUsers().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }


    class UserDialog(val viewModel: UserViewModel) : DialogFragment() {
        lateinit var binding: DialogUserBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = DialogUserBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            binding.btnClose.setOnClickListener {
                dismiss()
            }
            lifecycleScope.launch {
                viewModel.userDetail.collectLatest {
                    binding.ltContent.isVisible = it.status  == Resource.Status.SUCCESS
                    binding.progressBar.isVisible = it.status  == Resource.Status.LOADING
                    binding.ltNetworking.isVisible = it.status  == Resource.Status.ERROR
                    when(it.status){
                        Resource.Status.ERROR -> {
                            binding.errorMsg.text = it.message
                        }
                        Resource.Status.SUCCESS -> {
                           binding.user = it.data
                            Glide.with(view).load(it.data?.avatar_url).placeholder(R.drawable.ic_baseline_account_circle_24).circleCrop().into(binding.imgProfile)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}