package com.caffeine.bfst.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.ItemLayoutDonorBinding
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.utils.AlertDialog
import com.caffeine.bfst.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DonorAdapter(val list: ArrayList<UserDetails>, val context: Context) : RecyclerView.Adapter<DonorAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemLayoutDonorBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.name
        val bg = binding.bg
        val ldd = binding.lastDonation
        val loc = binding.location
        val request = binding.requestForBlood
        val call = binding.call
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutDonorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        val sdf = SimpleDateFormat("dd-MMM-yyyy")
        val today = sdf.format(Date())
        val days: Long
        var count = 0

        holder.name.text = user.name
        holder.bg.text = user.bg
        holder.loc.text = user.location

        val donation = user.ldd

        if (donation == "never"){
            holder.ldd.text = "Never Donated"
            count = 1
        }
        else {
            try {
                val TODAY = sdf.parse(today)
                val LAST = sdf.parse(user.ldd)
                days = ((TODAY.time - LAST.time) / (1000 * 60 * 60 * 24))

                holder.ldd.text = "$days days ago"

                if (days < 120){
                    holder.request.setBackgroundResource(R.drawable.disabled_red_button)
                }
                else {
                    holder.request.setBackgroundResource(R.drawable.bg_light_red_5)
                    count = 1
                }
            }
            catch (e : Exception){}
        }

        holder.request.setOnClickListener{
            if (count == 1){
                AlertDialog.showAlertDialog(context, "Functionality of this button will be available on next update", "Close")
            }
        }

        holder.call.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${user.number}"))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}