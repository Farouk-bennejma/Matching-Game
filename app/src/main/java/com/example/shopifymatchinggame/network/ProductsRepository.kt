package com.example.shopifymatchinggame.network


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopifymatchinggame.model.ProductList
import com.example.shopifymatchinggame.ui.settings.SettingsPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsRepository (
    private val settingsPreferences: SettingsPreferences
){
    private val shopifyAPI = RetrofitClientInstance.retrofitInstance?.create(GetProductsService::class.java)
    private val response = shopifyAPI?.getAllProducts(1, "c32313df0d0ef512ca64d5b336a0d7c6")

    fun getProducts(): LiveData<ProductList> {
        val productList: MutableLiveData<ProductList> = MutableLiveData()

        response?.clone()?.enqueue(object : Callback<ProductList> {

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.e("TAG", "Response Failure", t)
            }

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                productList.value = response.body()
            }
        })
        return productList
    }

    fun getSettings(): SettingsPreferences {
        return settingsPreferences
    }
}