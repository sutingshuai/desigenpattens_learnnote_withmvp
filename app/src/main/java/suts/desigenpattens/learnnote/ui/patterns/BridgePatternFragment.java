package suts.desigenpattens.learnnote.ui.patterns;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import suts.desigenpattens.learnnote.R;

/**
 * Created by sutingshuai on 2019-10-18
 * Describe:桥接模式 - Bridge Pattern -
 * 组成：Abstraction、DefinedAbstraction、Implementation、ConcreateImplementation、Client
 * Abstraction：抽象部分
 * 该类保持一个对实现部分对象的引用，抽象部分方法调用需要调用实现部分的对象来实现，该类一般为抽象类
 * DefinedAbstraction：优化的抽象部分
 * 抽象部分的具体实现，该类一般是对抽象部分的方法的优化和完善
 * Implementor：实现部分
 * 可以是接口或者抽象类,该类的方法可以不和抽象部分的一致，一般是由实现部分提供基本的操作，而抽象部分定义的则是基于实现部分这些基本操作的业务方法
 * ConcreteImplementorA/ConcreteImplementorB：实现部分的具体实现
 * 完善实现部分中方法定义的具体逻辑
 * client：客户调用
 *
 * 现实生活中有各种各样的例子：
 * 开关和电器：开关的种类有多重，而电器也是各式各样，这两者是独立变化的且又有耦合。
 * 对于显示屏来说她的尺寸与生产的厂商之间也是一种二维关系：具体的尺寸与具体的厂商独立变化
 * 更贴近生活的咖啡店咖啡的例子：进到咖啡店可选4种咖啡：大杯加奶、大杯原味、小杯加奶、小杯原味。对一杯咖啡来说就只有两种实质上的变化：一个是大杯小杯，一个是加糖和不加糖
 * <eg>这里用的例子是咖啡的例子</eg>
 */
public class BridgePatternFragment extends Fragment {

    private static final String TAG = "BridgePattern";
    @BindView(R.id.info_tips)
    TextView infoTips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_bridge_pattern, container, false);
        SuggarAddtive suggarAddtive = new SuggarAddtive();
        Coffee largeCoffe = new LargeCoffee(suggarAddtive);
        largeCoffe.makeCoffee();

        NoSuggarAdditive noSuggarAdditive = new NoSuggarAdditive();
        Coffee smallCoffe = new SmallCoffe(noSuggarAdditive);
        smallCoffe.makeCoffee();

        infoTips.setText("--- 桥接模式 ---");

        return view;
    }

    //举例：咖啡店里有大杯咖啡和小杯咖啡，他们都可以加奶和原味的，所以大小和加奶原味就成为了两个相关联的维度，可以使用桥接模式
    // 实现部分
    // implementor
    public abstract class CoffeeAdditives {
        public abstract String addSomething();
    }

    //ConcreteImplementorA
    public class SuggarAddtive extends CoffeeAdditives {

        @Override
        public String addSomething() {
            return "加糖";
        }
    }

    //ConcreteImplementorA
    public class NoSuggarAdditive extends CoffeeAdditives {

        @Override
        public String addSomething() {
            return "不加糖";
        }
    }

    //抽象部分
    //Abstraction
    public abstract class Coffee{
        protected CoffeeAdditives impl;

        public Coffee(CoffeeAdditives impl) {
            this.impl = impl;
        }

        public void makeCoffee(){}
    }
    //DefinedImplementorA
    public class LargeCoffee extends Coffee{

        public LargeCoffee(CoffeeAdditives coffeeAdditives) {
            super(coffeeAdditives);
        }

        @Override
        public void makeCoffee() {
            Log.e(TAG, "大杯" + impl.addSomething() + "咖啡");
        }
    }
    //DefinedImplementorB
    public class SmallCoffe extends Coffee{

        public SmallCoffe(CoffeeAdditives impl) {
            super(impl);
        }

        @Override
        public void makeCoffee() {
            Log.e(TAG, "小杯" + impl.addSomething() + "咖啡");
        }
    }


}
