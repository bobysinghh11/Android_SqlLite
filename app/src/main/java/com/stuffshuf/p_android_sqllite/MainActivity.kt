package com.stuffshuf.p_android_sqllite

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tasks= arrayListOf<TableTask.Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dbHelper=MyDbHelper(this)
        val taskDb=dbHelper.writableDatabase

        tasks=TableTask.getAllTasks(taskDb)

        val taskadapter=TaskAdapter(tasks)

        lvTodolist.adapter=taskadapter


        btnAdd.setOnClickListener {

            if (etNewItem.text.length>3 ){

                TableTask.insertTask(
                    taskDb, TableTask.Task(
                        null,
                        etNewItem.text.toString(),
                        false
                    )
                )
                tasks=TableTask.getAllTasks(taskDb)

                // lvTodolist.adapter=taskadapter
                taskadapter.updataTask(tasks)
                etNewItem.setText("")
                // taskadapter.tasks
            }
            else
            {
                Toast.makeText(this, "Please Enter Character Above 3", Toast.LENGTH_LONG).show()
            }

        }

        searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

             return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val itemss =newText
               // val items= searchview
                tasks=TableTask.searchTask(taskDb, itemss!!)
                taskadapter.updataTask(tasks)
                return true
            }

        })


     /*   tvSearch.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val items= s
                tasks=TableTask.searchTask(taskDb, items.toString())
                taskadapter.updataTask(tasks)
            }

        })*/


        btnsort.setOnClickListener {

         tasks = TableTask.sortTask(taskDb)
            taskadapter.updataTask(tasks)

        }


        btnDel.setOnClickListener {
            TableTask.deleteTask(taskDb)
            tasks=TableTask.getAllTasks(taskDb)
            taskadapter.updataTask(tasks)
        }




        lvTodolist.onItemClickListener=object :AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val thisTask=taskadapter.getItem(position)
                thisTask.done =!thisTask.done
                TableTask.updateTask(taskDb, thisTask)
                tasks=TableTask.getAllTasks(taskDb)
                taskadapter.updataTask(tasks)



            }

        }


    }


 }

    class TaskAdapter(var tasks:ArrayList<TableTask.Task>):BaseAdapter() {


      fun updataTask(newTasks:ArrayList<TableTask.Task>)
        {   tasks.clear()
            tasks.addAll(newTasks)
            notifyDataSetChanged()
        }



        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val li=parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view=li.inflate(android.R.layout.simple_list_item_1, parent, false)
            view.findViewById<TextView>(android.R.id.text1).text=getItem(position).task

            if (getItem(position).done) {
                view.findViewById<TextView>(android.R.id.text1).setTextColor(Color.GRAY)
            }
            return view
        }


        override fun getItem(position: Int)= tasks[position]


        override fun getItemId(position: Int):Long =0

        override fun getCount(): Int {
            return tasks.size
        }
    }
