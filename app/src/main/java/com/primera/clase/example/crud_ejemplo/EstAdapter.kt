package com.primera.clase.example.crud_ejemplo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.primera.clase.example.crud_ejemplo.databinding.ListStudentBinding

class EstAdapter() : RecyclerView.Adapter<EstAdapter.EstViewHolder>() {

    private var estList: List<EstModel> = ArrayList()
    private var onClickItem: ((EstModel)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= EstViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_student, parent, false)
    )

    override fun onBindViewHolder(holder: EstViewHolder, position: Int) {
        val itemList = estList[position]
        holder.bindView(itemList)
        holder.itemView.setOnClickListener { onClickItem?.invoke(itemList) }
    }

    override fun getItemCount(): Int = estList.size

    fun setOnClickItem(callback: (EstModel)->Unit){
        this.onClickItem = callback
    }

    fun addItems (items: ArrayList<EstModel>){
        this.estList = items
        notifyDataSetChanged()
    }

    inner class EstViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding =  ListStudentBinding.bind(itemView)
        fun bindView(est: EstModel){
            with(binding){
                tvIdEst.text =  est.id.toString()
                tvNombreEst.text= est.nombre
                tvCorreoEst.text = est.correo
                tvCursoEst.text = est.curso
            }
        }
    }

}