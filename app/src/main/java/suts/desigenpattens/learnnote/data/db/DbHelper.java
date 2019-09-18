package suts.desigenpattens.learnnote.data.db;

import java.util.List;

import io.reactivex.Observable;
import suts.desigenpattens.learnnote.data.db.model.Option;
import suts.desigenpattens.learnnote.data.db.model.Question;
import suts.desigenpattens.learnnote.data.db.model.User;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
public interface DbHelper {

    Observable<Long> insertUser(User user);

    Observable<List<User>> getAllUsers();

    Observable<List<Question>> getAllQuestions();

    Observable<Boolean> isOptionEmpty();

    Observable<Boolean> isQustionEmpty();

    Observable<Boolean> saveOption(Option option);

    Observable<Boolean> saveQuestion(Question question);

    Observable<Boolean> saveAllOptions(List<Option> options);

    Observable<Boolean> saveAllQuestions(List<Question> questions);

}
