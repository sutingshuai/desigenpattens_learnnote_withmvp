package suts.desigenpattens.learnnote.data.db;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import suts.desigenpattens.learnnote.data.db.greendao.DaoMaster;
import suts.desigenpattens.learnnote.data.db.greendao.DaoSession;
import suts.desigenpattens.learnnote.data.db.model.Option;
import suts.desigenpattens.learnnote.data.db.model.Question;
import suts.desigenpattens.learnnote.data.db.model.User;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getUserDao().insert(user);
            }
        });
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return mDaoSession.getUserDao().loadAll();
            }
        });
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return Observable.fromCallable(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return mDaoSession.getQuestionDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getOptionDao().count() > 0);
            }
        });
    }

    @Override
    public Observable<Boolean> isQustionEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getQuestionDao().count() > 0);
            }
        });
    }

    @Override
    public Observable<Boolean> saveOption(final Option option) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getOptionDao().insertInTx(option);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveQuestion(final Question question) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getQuestionDao().insert(question);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveAllOptions(final List<Option> options) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getOptionDao().insertInTx(options);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveAllQuestions(final List<Question> questions) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getQuestionDao().insertInTx(questions);
                return true;
            }
        });
    }
}
