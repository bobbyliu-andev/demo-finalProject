package faith.changliu.base.data.viewmodels

import android.arch.lifecycle.ViewModel
import faith.changliu.base.data.AppRepository

class MainViewModel : ViewModel() {
	var orders = AppRepository.getAllOrders()
}

class RequestViewModel : ViewModel() {
	var requests = AppRepository.getAllRequests()
}