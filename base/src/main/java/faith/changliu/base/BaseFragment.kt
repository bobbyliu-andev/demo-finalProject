package faith.changliu.base

import android.support.v4.app.Fragment
import faith.changliu.base.widgets.LoadingDialog

open class BaseFragment : Fragment() {
	
	val mLoading by lazy { (activity as? BaseActivity)?.mLoading }
	
	
}