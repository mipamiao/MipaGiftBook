package com.example.holleworld.DATA.Repository;

import com.example.holleworld.DATA.Person;
import com.example.holleworld.MyApp;
import com.example.holleworld.Utils.DatabaseExecutor;

import java.util.List;
import java.util.stream.Collectors;

public class PeopleRepository {
    private PeopleRepository(){};
    private static PeopleRepository ptr = new PeopleRepository();
    public  static PeopleRepository getInstance(){
        return ptr;
    }

    public List<Person> getAll(){
        AppDatabase db = MyApp.getInstance().getDatabase();
        PeopleDao peopleDao = db.peopleDao();
        List<People> peoples = peopleDao.getAllPeoples();
        return  peoples.stream().map(Person::fromPeople).collect(Collectors.toList());
    }
    public void addPerson(Person person){
        DatabaseExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = MyApp.getInstance().getDatabase();
                PeopleDao peopleDao = db.peopleDao();
                long id = peopleDao.insert(Person.toPeople(person));
                person.setId((int)(id));
            }
        });

    }

    public void addPersons(List<Person> persons){
        DatabaseExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<People> peoples = persons.stream().map(Person::toPeople).collect(Collectors.toList());
                AppDatabase db = MyApp.getInstance().getDatabase();
                PeopleDao peopleDao = db.peopleDao();
                long []ids = peopleDao.inserts(peoples);
                for(int i = 0;i<ids.length;i++)persons.get(i).setId((int)ids[i]);
            }
        });

    }

    public void updatePerson(Person person){
        DatabaseExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = MyApp.getInstance().getDatabase();
                PeopleDao peopleDao = db.peopleDao();
                peopleDao.updataPeople(Person.toPeople(person));
            }
        });

    }

    public void deletePerson(Person person){
        DatabaseExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = MyApp.getInstance().getDatabase();
                PeopleDao peopleDao = db.peopleDao();
                peopleDao.deletePeople(Person.toPeople(person));
            }
        });

    }

    public void clearTable(){
        DatabaseExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = MyApp.getInstance().getDatabase();
                PeopleDao peopleDao = db.peopleDao();
                peopleDao.clearTable();
            }
        });

    }

    public void reCreat(List<Person> persons){
        DatabaseExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = MyApp.getInstance().getDatabase();
                PeopleDao peopleDao = db.peopleDao();
                peopleDao.clearTable();
                List<People> peoples = persons.stream().map(Person::toPeople).collect(Collectors.toList());
                long []ids = peopleDao.inserts(peoples);
                for(int i = 0;i<ids.length;i++)persons.get(i).setId((int)ids[i]);
            }
        });
    }

}
