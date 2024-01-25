    package com.example.to_do.repository;

    import android.app.Application;
    import android.os.AsyncTask;
    import android.util.Log;

    import androidx.lifecycle.LiveData;

    import com.example.to_do.db.TaskDao;
    import com.example.to_do.db.TaskDatabase;
    import com.example.to_do.db.UserDao;
    import com.example.to_do.models.Task;
    import com.example.to_do.models.User;

    import java.util.List;

    public class TaskRepository {
        private TaskDao taskDao;
        private UserDao userDao;
        private LiveData<List<Task>> taskList;
        private LiveData<String> username;

        public TaskRepository (Application app){
            TaskDatabase taskDatabase = TaskDatabase.getInstance(app);
            taskDao = taskDatabase.taskDao();
            userDao = taskDatabase.userDao();
        }

        //User methods
        public void insertDataUser(User user){ new InsertUser(userDao).execute(user); }
        public void updateDataUser(User user){ new UpdateUser(userDao).execute(user); }
        public void deleteDataUser(User user){ new DeleteUser(userDao).execute(user); }

        //Task methods
        public void insertData(Task task){ new InsertTask(taskDao).execute(task); }
        public void updateData(Task task){ new UpdateTask(taskDao).execute(task); }
        public void deleteData(Task task){ new DeleteTask(taskDao).execute(task); }

        //Async user methods
        private static class InsertUser extends AsyncTask<User,Void,Void>{
            private UserDao userDao;

            public InsertUser(UserDao userDao) {
                this.userDao = userDao;
            }

            @Override
            protected Void doInBackground(User... users) {
                userDao.insert(users[0]);
                return null;
            }
        }
        private static class DeleteUser extends AsyncTask<User,Void,Void>{
            private UserDao userDao;

            public DeleteUser(UserDao userDao) {
                this.userDao = userDao;
            }

            @Override
            protected Void doInBackground(User... users) {
                userDao.delete(users[0]);
                return null;
            }
        }
        private static class UpdateUser extends AsyncTask<User,Void,Void>{
            private UserDao userDao;

            public UpdateUser(UserDao userDao) {
                this.userDao = userDao;
            }

            @Override
            protected Void doInBackground(User... users) {
                userDao.update(users[0]);
                return null;
            }
        }
        public LiveData<String> getUserNameExist(String mUsername){
            try {
                username = userDao.getUserNameExist(mUsername);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return username;
        }
        //Async task methods
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
        //Live Data
        public LiveData<List<Task>> getAllData(int user) {
            try {
                taskList = taskDao.getAllData(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return taskList;
        }

    }
