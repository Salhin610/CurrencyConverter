package com.task.currencyconverter.ui.home
import android.app.Application
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.task.currencyconverter.domain.usecase.GetCurrencyRatesUseCase
import com.task.currencyconverter.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getCurrencyRatesUseCase: GetCurrencyRatesUseCase

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var observer: Observer<String>

    private lateinit var homeViewModel: HomeViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Mock the resources and application context
        `when`(application.applicationContext).thenReturn(application)
        `when`(application.resources).thenReturn(resources)
        `when`(resources.getStringArray(R.array.currency_array)).thenReturn(arrayOf("USD", "EUR", "JPY"))
        `when`(resources.getStringArray(R.array.common_currency_array)).thenReturn(arrayOf("USD", "EUR"))

        homeViewModel = HomeViewModel(getCurrencyRatesUseCase, application)
        homeViewModel.error.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        homeViewModel.error.removeObserver(observer)
    }



    @Test
    fun convertCurrency_fromAmount1() {
        homeViewModel._rates["USD"] = 1.0
        homeViewModel._rates["EUR"] = 0.85

        homeViewModel.amount1.value = "10.000"
        homeViewModel.currency1Index.value = 0 // USD
        homeViewModel.currency2Index.value = 1 // EUR

        homeViewModel.amount1.postValue("10.000")  // Manually trigger observer
        homeViewModel.convertCurrency(true)  // Explicitly call conversion logic

        Assert.assertEquals("8.500", homeViewModel.amount2.value)
    }

    @Test
    fun convertCurrency_fromAmount2() {
        homeViewModel._rates["USD"] = 1.0
        homeViewModel._rates["EUR"] = 0.85

        homeViewModel.amount2.value = "10.000"
        homeViewModel.currency1Index.value = 0 // USD
        homeViewModel.currency2Index.value = 1 // EUR

        homeViewModel.amount2.postValue("10.000")  // Manually trigger observer
        homeViewModel.convertCurrency(false)  // Explicitly call conversion logic

        Assert.assertEquals("11.765", homeViewModel.amount1.value)
    }

    @Test
    fun swapCurrencies() {
        homeViewModel._rates["USD"] = 1.0
        homeViewModel._rates["EUR"] = 0.85

        homeViewModel.currency1Index.value = 0 // USD
        homeViewModel.currency2Index.value = 1 // EUR
        homeViewModel.amount1.value = "10.0"
        homeViewModel.amount2.value = "8.5"

        // Call swapCurrencies to swap indices and values
        homeViewModel.swapCurrencies()

        // Verify indices are swapped
        Assert.assertEquals(1, homeViewModel.currency1Index.value)
        Assert.assertEquals(0, homeViewModel.currency2Index.value)

        // Verify amounts are swapped and conversion is triggered correctly
        Assert.assertEquals("7.225", homeViewModel.amount1.value)
        Assert.assertEquals("8.500", homeViewModel.amount2.value)

    }

    @Test
    fun getTodayRateForUnit() {
        homeViewModel._rates["USD"] = 1.0
        homeViewModel._rates["EUR"] = 0.85

        homeViewModel.currency1Index.value = 0 // USD
        homeViewModel.currency2Index.value = 1 // EUR

        val rate = homeViewModel.getTodayRateForUnit()

        Assert.assertEquals("0.850", rate)
    }

    @Test
    fun getCommonCurrenciesRates() {
        homeViewModel._rates["USD"] = 1.0
        homeViewModel._rates["EUR"] = 0.85

        homeViewModel.currency1Index.value = 0 // USD

        val rates = homeViewModel.getCommonCurrenciesRates()

        Assert.assertArrayEquals(doubleArrayOf(1.0, 0.85), rates, 0.001)
    }
}