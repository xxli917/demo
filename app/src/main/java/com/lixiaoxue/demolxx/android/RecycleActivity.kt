package com.lixiaoxue.demolxx.android

import android.app.Instrumentation
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Executors
import com.bumptech.glide.util.Executors.mainThreadExecutor
import com.lixiaoxue.demolxx.DemoApplication
import com.lixiaoxue.demolxx.R
import com.lixiaoxue.demolxx.bean.ItemDemo
import com.lixiaoxue.demolxx.databinding.ActivityRecycleBinding
import com.lixiaoxue.demolxx.databinding.ItemRecycleBinding
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.util.*
import java.util.concurrent.Executor
import kotlin.collections.ArrayList

//RecycleView简单使用
class RecycleActivity : AppCompatActivity() {
     var tag = "RecycleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var dataBinding = DataBindingUtil.setContentView<ActivityRecycleBinding>(this, R.layout.activity_recycle)
        //var layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        /* var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
         layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
         dataBinding.recyclerView.itemAnimator = null*/
        dataBinding.recyclerView.layoutManager = layoutManager

        var dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_line_f3f4fa))

        dataBinding.recyclerView.addItemDecoration(dividerItemDecoration)
        var list = getList()

        //view复用，减少绘制，那数据是如何储存和重新赋值的

        var adapter = MyAdapter(list)
        //是否从屏幕底端开始填充
        //  layoutManager.stackFromEnd = true
        dataBinding.recyclerView.adapter = adapter
        Thread{
            //在当前线程初始化一个looper
            Looper.prepare()
            Looper.loop()
            var handler = Handler(Looper.myLooper())
        }

        //ThreadLocal



    }



    private var layoutManager: LinearLayoutManager = object : LinearLayoutManager(this) {
        //为布局的时候添加额外的空间，添加之后第一次布局的item数将超过屏幕可见数量，有点预加载的感觉
        //之后就可以一直复用，但是第一次创建会很慢
        override fun calculateExtraLayoutSpace(state: RecyclerView.State, extraLayoutSpace: IntArray) {
            super.calculateExtraLayoutSpace(state, extraLayoutSpace)
            extraLayoutSpace[0] = 0
            extraLayoutSpace[1] = 1280 * 1
        }
    }

    inner class MyAdapter(list: List<ItemDemo>) : RecyclerView.Adapter<MyViewHolder>() {
        var list = ArrayList<ItemDemo>()

        init {
            this.list = list as ArrayList<ItemDemo>

        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            Log.e(tag, "onCreateViewHolder")
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = DataBindingUtil.inflate<ItemRecycleBinding>(layoutInflater, R.layout.item_recycle, parent, false)
            return MyViewHolder(itemView.rootView)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        /* override fun getItemViewType(position: Int): Int {
             return super.getItemViewType(position)
         }
 */
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            Log.e(tag, "onBindViewHolder")
            holder.getBinding()?.name?.text = list[position].name
            holder.getBinding()?.imageView?.setImageDrawable(DemoApplication.mINSTANCE.getDrawable(list[position].drawable))
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemRecycleBinding? = null

        //点击背景变色
        private var clickListLister = View.OnClickListener {
            Log.e(tag,"点击了")
            binding?.let {
                it.linear
            }?.let {
                var ac = it.isActivated

                Log.e(tag,"状态=${ac}")

                it.isActivated = !ac

            }
        }

        init {

            binding = DataBindingUtil.bind(itemView)
            binding?.linear?.setOnClickListener(clickListLister)
        }

        fun getBinding(): ItemRecycleBinding? {
            return binding
        }

    }


    private fun getList(): List<ItemDemo> {
         var list = ArrayList<ItemDemo>()
        list.add(ItemDemo(R.mipmap.p1, "黑夜骑士1"))
        list.add(ItemDemo(R.mipmap.p2, "黑夜骑士2"))
        list.add(ItemDemo(R.mipmap.p3, "黑夜骑士3"))
        list.add(ItemDemo(R.mipmap.p4, "黑夜骑士4"))
        list.add(ItemDemo(R.mipmap.p5, "黑夜骑士5"))
        list.add(ItemDemo(R.mipmap.p6, "黑夜骑士6"))

        list.add(ItemDemo(R.mipmap.p1, "黑夜骑士7"))
        list.add(ItemDemo(R.mipmap.p2, "黑夜骑士8"))
        list.add(ItemDemo(R.mipmap.p3, "黑夜骑士9"))
        list.add(ItemDemo(R.mipmap.p4, "黑夜骑士10"))
        list.add(ItemDemo(R.mipmap.p5, "黑夜骑士11"))
        list.add(ItemDemo(R.mipmap.p6, "黑夜骑士12"))
        return list

    }


}