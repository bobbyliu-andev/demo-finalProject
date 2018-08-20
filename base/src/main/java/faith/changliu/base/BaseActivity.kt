package faith.changliu.base

import android.support.v7.app.AppCompatActivity
import faith.changliu.base.widgets.LoadingDialog

open class BaseActivity : AppCompatActivity() {

	val mLoading by lazy { LoadingDialog(this) }

}