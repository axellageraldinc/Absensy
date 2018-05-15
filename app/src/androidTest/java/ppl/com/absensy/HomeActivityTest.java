package ppl.com.absensy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ppl.com.absensy.view.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void tambahMataKuliahTerusMunculDiList() {
        onView(withId(R.id.btnAddMataKuliah)).perform(click());
        onView(withId(R.id.txtInputMataKuliah)).check(matches(isDisplayed()));
        onView(withId(R.id.txtInputMataKuliah)).perform(typeText("KID"), closeSoftKeyboard());
        onView(withId(R.id.txtInputJumlahKosong)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.btnSimpanMataKuliah)).perform(click());
        onView(withId(R.id.recyclerViewMataKuliah)).check(matches(hasDescendant(withText("KID"))));
    }

    @Test
    public void bukaAplikasiMunculMataKuliahTerdaftarDiList(){
        onView(withId(R.id.recyclerViewMataKuliah)).check(matches(hasDescendant(withText("KID"))));
    }

    @Test
    public void absenMataKuliah(){
        onView(withId(R.id.recyclerViewMataKuliah)).perform(actionOnItem(hasDescendant(withText("KID")), click()));
        onView(withId(R.id.btnAddAbsen)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAddAbsen)).perform(click());
        onView(withId(R.id.recyclerViewMataKuliah)).check(matches(hasDescendant(allOf(withText("KID"),
                hasSibling(withText("2"))))));
    }
}