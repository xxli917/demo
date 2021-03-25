package com.lixiaoxue.demolxx.android

import android.animation.ValueAnimator
import android.content.ContentProvider
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.amap.api.mapcore.util.it
import com.bumptech.glide.Glide
import com.lixiaoxue.demolxx.DemoApplication
import com.lixiaoxue.demolxx.R
import com.lixiaoxue.demolxx.bean.Demo
import com.lixiaoxue.demolxx.bean.S
import com.lixiaoxue.demolxx.databinding.ActivityRecycle1Binding
import com.lixiaoxue.demolxx.databinding.ItemRecycle1Binding
import com.nucarf.base.utils.LogUtils


/**
 * viewHolder复用机制
图片异步加载、缓存、压缩（使用glide等解决，最好后台反2套：缩略图和原图）
根据滑动状态处理加载情况

 */
 open class Recycle1Activity : AppCompatActivity() {
    public var tag = "Recycle1Activity"
    var tracker: SelectionTracker<Long>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        var dataBinding = DataBindingUtil.setContentView<ActivityRecycle1Binding>(this, R.layout.activity_recycle1)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dataBinding.recyclerView.layoutManager = layoutManager
        dataBinding.recyclerView.post {

        }
        var dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        dataBinding.recyclerView.addItemDecoration(dividerItemDecoration)
        var list = ArrayList<String>()
        for (i in 0..10) {
            list.add("条目${i}")
        }



        var adapter = MyAdapter(list)
        dataBinding.recyclerView.setHasFixedSize(true)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_IDLE -> //当屏幕停止滚动，加载图片
                        try {
                             Glide.with(baseContext).resumeRequests()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    SCROLL_STATE_DRAGGING ->  //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                        try {
                            Glide.with(baseContext).pauseRequests()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    SCROLL_STATE_SETTLING ->//由于用户的操作，屏幕产生惯性滑动，停止加载图片
                        try {
                             Glide.with(baseContext).pauseRequests()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        //触发事件是长按
        tracker = SelectionTracker.Builder<Long>(
                "id",
                dataBinding.recyclerView,
                StableIdKeyProvider(dataBinding.recyclerView),
                MyItemDetailsLookup(dataBinding.recyclerView),
                StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
                )/*.withOnItemActivatedListener(object :OnItemActivatedListener<Long>{
            override fun onItemActivated(item: ItemDetailsLookup.ItemDetails<Long>, e: MotionEvent): Boolean {
                if(e.action == MotionEvent.ACTION_DOWN){
                   return item.inSelectionHotspot(e)
                }
                return false
            }


        })*/.build()





        tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        val items = tracker?.selection!!.size()
                        if (items == 2) {
                            //launchSum(tracker?.selection!!)
                            Log.e(tag,"选择了两个")
                        }
                    }
                })
        adapter.tracker = tracker



    }






    inner class MyAdapter(list: List<String>) : RecyclerView.Adapter<MyViewHolder>() {
        var tracker: SelectionTracker<Long>?= null
        var list = ArrayList<String>()

        init {
            this.list = list as ArrayList<String>
            setHasStableIds(true)

        }



        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            Log.e(tag, "onCreateViewHolder")
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = DataBindingUtil.inflate<ItemRecycle1Binding>(layoutInflater, R.layout.item_recycle1, parent, false)
            return MyViewHolder(itemView.rootView)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            Log.e(tag, "onBindViewHolder")
            holder?.let {
                it.getBinding()
            }?.let {
                it.name.text = list[position]
                Glide.with(DemoApplication.mINSTANCE).load("").into(it.imageView)
            }

            tracker?.let {
                Log.e(tag, "tracker")

                holder.bind(it.isSelected(position.toLong()))
            }
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemRecycle1Binding? = null

        private var clickListLister = View.OnClickListener {
           // Log.e(tag,"点击了")
            binding?.let {
                it.rootView
            }?.let {
                var ac = it.isActivated

               // Log.e(tag,"状态=${ac}")

                it.isActivated = !ac

            }
        }


        init {
            binding = DataBindingUtil.bind(itemView)
           // binding?.rootView?.setOnClickListener(clickListLister)

        }
        fun bind(isActivated: Boolean = false) {
            Log.e(tag,"状态 = ${isActivated}")
            itemView.isActivated = isActivated
        }
        fun getBinding(): ItemRecycle1Binding? {
            return binding
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
                object : ItemDetailsLookup.ItemDetails<Long>() {
                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): Long? = itemId
                }

    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {

            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as MyViewHolder)
                        .getItemDetails()
            }
            return null
        }
    }






}




