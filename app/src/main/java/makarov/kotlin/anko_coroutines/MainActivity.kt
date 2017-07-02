package makarov.kotlin.anko_coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.TextView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            padding = dip(30)
            val name = textView {
                textSize = 24f
                gravity = Gravity.CENTER
                lparams(width = matchParent) {
                    bottomMargin = dip(20)
                }
            }
            button(getString(R.string.say_hello)) {
                textSize = 24f
                onClick { loadAndShowData(name) }
            }
        }
    }

    fun loadAndShowData(textView: TextView) {
        // Ref<T> uses the WeakReference under the hood - protects from situations when activity can be leaked
        val ref: Ref<MainActivity> = this.asReference()

        //block that are executed in a background thread and return the result to the UI thread
        async(UI) {
            val data = bg { getData() }
            ref().showData(textView, data.await())
        }
    }

    /* some complex operation - can be network request  */
    fun getData(): String {
        return getString(R.string.hello_world)
    }

    fun showData(textView: TextView, data: String) {
        textView.text = data
    }
}
