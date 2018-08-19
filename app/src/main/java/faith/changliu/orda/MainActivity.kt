package faith.changliu.orda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import faith.changliu.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class MainActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		supportActionBar?.hide()

		mFragContainer.addView(
				LayoutInflater.from(this)
						.inflate(R.layout.splash_main, mFragContainer, false)
		)

		launch(UI) {
			delay(2000)
			window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
		}
	}

}

