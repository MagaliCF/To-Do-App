    package com.example.to_do.repository;

    import android.app.Application;
    import android.os.AsyncTask;
    import android.util.Log;

    import androidx.lifecycle.LiveData;

    import com.example.to_do.db.TaskDao;
    import com.example.to_do.db.TaskDatabase;
    import com.example.to_do.models.Task;

    import java.util.List;

    public class TaskRepository {
        private TaskDao taskDao;
        private LiveData<List<Task>> taskList;

        public TaskRepository (Application app){
            TaskDatabase taskDatabase = TaskDatabase.getInstance(app);
            taskDao = taskDatabase.taskDao();
        }

        public void insertData(Task task){ new InsertTask(taskDao).execute(task); }
        public void updateData(Task task){ new UpdateTask(taskDao).execute(task); }
        public void deleteData(Task task){ new DeleteTask(taskDao).execute(task); }
        /*public LiveData<List<Task>> getAllData(){
            return taskList;
        }*/
        public LiveData<List<Task>> getAllData(String user) {
            //taskList = taskDao.getAllData(user);
            try {
                taskList = taskDao.getAllData(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return taskList;
        }
        private static class InsertTask extends AsyncTask<Task,Void,Void>{
            private TaskDao taskDao;

            public InsertTask(TaskDao taskDao) {
                this.taskDao = taskDao;
            }

            @Override
            protected Void doInBackground(Task... tasks) {
                taskDao.insert(tasks[0]);
                return null;
            }
        }
        private static class DeleteTask extends AsyncTask<Task,Void,Void>{
            private TaskDao taskDao;

            public DeleteTask(TaskDao taskDao) {
                this.taskDao = taskDao;
            }

            @Override
            protected Void doInBackground(Task... tasks) {
                taskDao.delete(tasks[0]);
                return null;
            }
        }
        private static class UpdateTask extends AsyncTask<Task,Void,Void>{
            private TaskDao taskDao;

            public UpdateTask(TaskDao taskDao) {
                this.taskDao = taskDao;
            }

            @Override
            protected Void doInBackground(Task... tasks) {
                taskDao.update(tasks[0]);
                return null;
            }
        }
    }
