package faith.changliu.base

import android.app.Application
import android.content.ContextWrapper
import android.view.View
import org.jetbrains.anko.windowManager

private lateinit var INSTANCE: Application

class BaseApp : Application() {

	override fun onCreate() {
		super.onCreate()
		INSTANCE = this
	}

}

object AppContext: ContextWrapper(INSTANCE)