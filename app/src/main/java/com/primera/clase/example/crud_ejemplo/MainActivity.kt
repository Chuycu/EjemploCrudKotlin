package com.primera.clase.example.crud_ejemplo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.primera.clase.example.crud_ejemplo.databinding.ActivityMainBinding
import io.github.muddz.styleabletoast.StyleableToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var SQLiteHelper: SQLHelper
    private var adapter: EstAdapter? = null
    private var estModel: EstModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SQLiteHelper = SQLHelper(this)
        initRecyclerView()

        // Trabajar por medio de un with
        with(binding) {
            btnInsertar.setOnClickListener {
                val nombreEst = etNombreEst.text.toString()
                val correoEst = etCorreoEst.text.toString()
                val cursoEst = etCursoEst.text.toString()

                addEstudent(nombreEst, correoEst, cursoEst)
            }

            btnListar.setOnClickListener{
                getStudent()
            }

            btnLimpiar.setOnClickListener{
                clearText()
            }

            btnActualizar.setOnClickListener {
                val nombreEst = etNombreEst.text.toString()
                val correoEst = etCorreoEst.text.toString()
                val cursoEst = etCursoEst.text.toString()
                updateStudent(nombreEst, correoEst, cursoEst)
            }


        }

        adapter?.setOnClickItem {
            Toast.makeText(this,it.nombre, Toast.LENGTH_SHORT).show()
            with(binding){
                etNombreEst.setText(it.nombre)
                etCorreoEst.setText(it.correo)
                etCursoEst.setText(it.curso)
                estModel = it
            }
        }

    }

    private fun updateStudent(nombre: String, correo: String, curso: String) {
        if (nombre.equals(estModel?.nombre)
            && correo.equals(estModel?.correo)
            && curso.equals(estModel?.curso)){
            StyleableToast.makeText(
                this,
                "No se actualizo el estudiante , no hay cambios.",
                R.style.error_toast
            ).show()
            clearText()
            return
        }

        if(estModel == null){
            StyleableToast.makeText(
                this,
                "Debe dar click a un estudiante de la lista.",
                R.style.error_toast
            ).show()
            return
        }

        if(nombre.isEmpty() || curso.isEmpty()){
            StyleableToast.makeText(
                this,
                "Debe ingresar nombre y curso.",
                R.style.error_toast
            ).show()
        }else if(curso.length < 4 || curso.length > 4 ){
            StyleableToast.makeText(
                this,
                "El curso debe tener max 4 digitos.",
                R.style.error_toast
            ).show()
        }

    }

    private fun clearText() {
        with(binding){
            etNombreEst.setText("")
            etCorreoEst.setText("")
            etCursoEst.setText("")
            etNombreEst.requestFocus()
        }
    }

    private fun initRecyclerView() {
        binding.rvListEst.layoutManager = LinearLayoutManager(this)
        adapter = EstAdapter()
        binding.rvListEst.adapter = adapter
    }

    private fun getStudent() {
        val estList = SQLiteHelper.getListEst()
        adapter?.addItems(estList)
    }

    private fun addEstudent(nombreEst: String, correoEst: String, cursoEst: String) {
        if(nombreEst.isEmpty() || cursoEst.isEmpty()){
            StyleableToast.makeText(
                this,
                "Debe ingresar nombre y curso.",
                R.style.error_toast
            ).show()
        }else if(cursoEst.length < 1 || cursoEst.length > 4 ){
            StyleableToast.makeText(
                this,
                "El curso debe tener max 4 digitos.",
                R.style.error_toast
            ).show()
        }else{
            val est = EstModel(null, nombreEst,correoEst,cursoEst)
            val status = SQLiteHelper.insertEst(est)
            if (status >-1){
                clearText()
                getStudent()
                StyleableToast.makeText(
                    this,
                    "Agregado correctamente.",
                    R.style.success_toast
                ).show()
            }else{
                StyleableToast.makeText(
                    this,
                    "Error al guardar el estudiante.",
                    R.style.error_toast
                ).show()
            }
        }
    }



}