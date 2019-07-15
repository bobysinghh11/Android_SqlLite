package com.stuffshuf.p_android_sqllite


import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class TableTask {

    data class Task(
        val id:Int?,
        val task:String,
        var done:Boolean
    )


// we create companion object so that we can use it in other kotlin file
companion object{

    // step-2 --> create table command inside variable
    // pass the CMD_CREATE_TABLE in MyDbHelper onCreate fun
    // TEXT hme maximum size de deta hai
    val TABLE_NAME = "tasks"


    val CMD_CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        task TEXT,
        done BOOLEAN
        );
      """.trimIndent()



    // step3
    //create 2 fun-->1, insertTask, 2-> getAllTask


    fun insertTask(db:SQLiteDatabase, task: Task){

        val taskRow=ContentValues()
        taskRow.put("task" , task.task)
            taskRow.put("done", task.done)


        //db.insert fun require special class call ContentValue(), this is just like key value use in intent
        db.insert(TABLE_NAME, null, taskRow)

    }


    fun deleteTask(db: SQLiteDatabase)

    {

        db.delete(TABLE_NAME, "done=1", null)


    }


fun sortTask(db: SQLiteDatabase):ArrayList<Task>
{
    val tasks=ArrayList<Task>()

    val cursor=db.query(
        TABLE_NAME,
        arrayOf("id", "task" ,"done"),
        null, null,
        null,
        null,
        "done ASC"
    )


   // cursor.moveToFirst()
    val idCol=cursor.getColumnIndex("id")
    val taskCol=cursor.getColumnIndex("task")
    val doneCol=cursor.getColumnIndex("done")

    while (cursor.moveToNext()){
        val task=Task(
            cursor.getInt(idCol),
            cursor.getString(taskCol),
            cursor.getInt(doneCol) ==1

        )

        tasks.add(task)
    }


    cursor.close()
    return tasks

}

    fun searchTask(db: SQLiteDatabase, t:String):ArrayList<Task>
    {
        val tasks=ArrayList<Task>()

        val cursor=db.query(
            TABLE_NAME,
            arrayOf("id", "task" ,"done"),
            "task LIKE  '$t%' AND done=0", null,
            null,
            null,
            null
        )

        //cursor.moveToFirst()
        val idCol=cursor.getColumnIndex("id")
        val taskCol=cursor.getColumnIndex("task")
        val doneCol=cursor.getColumnIndex("done")

        while (cursor.moveToNext()){
            val task=Task(
                cursor.getInt(idCol),
                cursor.getString(taskCol),
                cursor.getInt(doneCol) ==1

            )

            tasks.add(task)
        }


        cursor.close()
        return tasks
    }



    fun updateTask(db: SQLiteDatabase, task:Task)
    {
        val updateRow=ContentValues()
        updateRow.put("task", task.task)
        updateRow.put("done", task.done)
        db.update(TABLE_NAME, updateRow, "id=?", arrayOf(task.id.toString()))
    }






    // here we don't need to enter anything we just want to show data so it return ArrayList<Task>
    fun getAllTasks(db: SQLiteDatabase):ArrayList<Task>{

        val tasks=ArrayList<Task>()
//select table name where[condition][group by fieldname][Having]
        val cursor=db.query(
            TABLE_NAME,
            arrayOf("id", "task", "done"),
            null, null, // where
            null, //group by
            null, //having
            null // order
        )

        //to move the cursor to first need to cursor.moveToFirst() so that we can read data row by row
        // when last row come moveToNext give false condition and exit, inside while we need to add
        // task in ArrayList<Task>
        //cursor.moveToFirst()
        val idCol=cursor.getColumnIndex("id")
        val taskCol=cursor.getColumnIndex("task")
        val doneCol=cursor.getColumnIndex("done")

      while (cursor.moveToNext()){
            val task=Task(
                cursor.getInt(idCol),
                cursor.getString(taskCol),
                cursor.getInt(doneCol) ==1

            )

            tasks.add(task)
        }



        cursor.close()
        return tasks

    }


}

}