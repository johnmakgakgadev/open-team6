package com.example.locationhomepage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locationhomepage.R
import com.example.locationhomepage.data.model.PricingRequest
import com.example.locationhomepage.databinding.PrcingItemBinding

class PricingAdapter :RecyclerView.Adapter<PricingAdapter.PricingViewHolder>(){

    private var pricingList:List<PricingRequest> = arrayListOf(PricingRequest(),PricingRequest(),PricingRequest(),
            PricingRequest(),PricingRequest(),PricingRequest())

    fun setData(pricingList:ArrayList<PricingRequest>){
       this.pricingList = pricingList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PricingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PrcingItemBinding.inflate(inflater,parent,false)
     return  PricingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PricingViewHolder, position: Int) {

        holder.bind(pricingList[position])
    }

    override fun getItemCount(): Int {
        return pricingList.size
    }

    inner class PricingViewHolder(private val binding:PrcingItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(bind:PricingRequest){
            binding.apply {
                Glide.with(pricingImage.rootView).load(bind.image)
                    .error(R.drawable.img)
                    .into(pricingImage)
                serviceName.text = bind.name
                time.text = "$time min away"
                price.text = "$${bind.price}"
                numOfUsers.text = bind.capacity.toString()

            }

        }
    }
}