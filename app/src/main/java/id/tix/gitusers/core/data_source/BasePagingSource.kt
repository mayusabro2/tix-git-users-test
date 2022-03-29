package id.tix.gitusers.core.data_source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.core.network.Resource.*
import id.tix.gitusers.features.data.data_source.remote.UsersPagingSource.Companion.PER_PAGE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

abstract class BasePagingSource<T, V : Any> : PagingSource<Int, V>() {
    open val STARTING_PAGE_INDEX : Int = 1
    val state: MutableLiveData<Status> = MutableLiveData()



    abstract suspend fun getData(params: LoadParams<Int>) : List<V>

    override fun getRefreshKey(state: PagingState<Int, V>): Int? = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {

        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val data = getData(params)
            val nextKey =
                if (data.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + PER_PAGE
                }
            LoadResult.Page(
                data = data,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}