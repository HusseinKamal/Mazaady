package com.hussein.mazaady.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.hussein.mazaady.data.model.ArrayBaseResponse
import com.hussein.mazaady.data.model.BaseResponse
import com.hussein.mazaady.data.model.Status
import com.hussein.mazaady.domain.category.Category
import com.hussein.mazaady.domain.category.CategoryX
import com.hussein.mazaady.domain.option.Option
import com.hussein.mazaady.domain.option.OptionX
import com.hussein.mazaady.domain.properity.Properity
import com.hussein.mazaady.domain.repository.CategoryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest{

    @MockK
    private lateinit var userRepository: CategoryRepository

    private lateinit var viewModel: MainViewModel

    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        // Initialize the ViewModel with the mocked repository
        userRepository = mockk()
        // Create a mock category
        val category = CategoryX(id=1, slug = "Cars", description = "", circleIcon = "" ,name = "Cars", image = "", disableShipping = 0, children = emptyList())
        coEvery { userRepository.getCategories() } returns flowOf(
            BaseResponse(msg = "", code = 200,data = Category(adsBanners = emptyList(), categories = arrayListOf(category),googleVersion = "1.0.0", iosVersion = "1.0.0", statistics = null))
        )

        // Create a empty mock category
        /** //If you need to add text empty case this is mock data for category
        coEvery { userRepository.getCategories() } returns flowOf(
            BaseResponse(msg = "", code = 200,data = Category(adsBanners = emptyList(), categories = ArrayList(),googleVersion = "1.0.0", iosVersion = "1.0.0", statistics = null))
        )*/

        //create a mock property
        val option1 = Option(id=2, slug = "Cars", description = "" ,name = "Cars Fix Option 1", parent = 0)
        val option2 = Option(id=3, slug = "Cars", description = "" ,name = "Cars Fix Option 2", parent = 0)
        coEvery { userRepository.getProperities("1") } returns flowOf(
            ArrayBaseResponse(msg = "", code = 200,data = arrayListOf(Properity(name = "Car Fix", id = 1, slug = "Car Fix", type = "select",options = arrayListOf(option1,option2))))
        )

        //create a mock option
        val optionData1 = OptionX(id=4, slug = "Car Option 1" ,name = "Cars Fix Option 3", child = true, parent = 0)
        val optionData2 = OptionX(id=5, slug = "Car Option 2",name = "Cars Fix Option 3", child = true, parent = 0)
        coEvery { userRepository.getOptions("1") } returns flowOf(
            ArrayBaseResponse(msg = "", code = 200,data = arrayListOf(Option(name = "Car Fix", id = 1, slug = "Car Fix", parent = 0, type = "select",options = arrayListOf(optionData1,optionData2))))
        )
        viewModel = MainViewModel(userRepository)


    }

   @Test
    fun `getCategory updates categoryData state`() = runTest {
        viewModel.categoryData.test {
            assertThat(awaitItem()).isEqualTo(BaseResponse(code=0, msg="", data=null, status = Status.Loading))
            viewModel.getCategories()  // This line calls the function to trigger the interaction
            val expectedCategory = CategoryX(id=1, slug = "Cars", description = "", circleIcon = "" ,name = "Cars", image = "", disableShipping = 0, children = emptyList())

            assertThat(awaitItem()).isEqualTo(BaseResponse(code = 200, msg = "",data = Category(adsBanners = emptyList(), categories = arrayListOf(expectedCategory),googleVersion = "1.0.0", iosVersion = "1.0.0", statistics = null))
            )
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { userRepository.getCategories() } // Verify after the interaction
    }

    /** //If you need to add text empty case use this test function

    @Test
    fun `getCategory no data founded`() = runTest {
        viewModel.categoryData.test {
            assertThat(awaitItem()).isEqualTo(BaseResponse(code=0, msg="", data=null, status = Status.Loading))
            viewModel.getCategories()  // This line calls the function to trigger the interaction
            assertThat(awaitItem()).isEqualTo(BaseResponse(code = 200, msg = "",data = Category(adsBanners = emptyList(), categories = ArrayList(),googleVersion = "1.0.0", iosVersion = "1.0.0", statistics = null)))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { userRepository.getCategories() } // Verify after the interaction
    }
     **/

    @Test
    fun `getProperty updates properityData state`() = runTest {
        viewModel.properityData.test {
            assertThat(awaitItem()).isEqualTo(ArrayBaseResponse(code=0, msg="", data= ArrayList(), status = Status.Loading))
            viewModel.getProperities("1")  // This line calls the function to trigger the interaction
            val expectedOption1 = Option(id=2, slug = "Cars", description = "" ,name = "Cars Fix Option 1", parent = 0)
            val expectedOption2 = Option(id=3, slug = "Cars", description = "" ,name = "Cars Fix Option 2", parent = 0)
            assertThat(awaitItem()).isEqualTo(ArrayBaseResponse(msg = "", code = 200,data = arrayListOf(Properity(name = "Car Fix", id = 1, slug = "Car Fix", type = "select",options = arrayListOf(expectedOption1,expectedOption2)))))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { userRepository.getProperities("1") } // Verify after the interaction
    }

    @Test
    fun `getOptions updates optionData state`() = runTest {
        viewModel.optionData.test {
            assertThat(awaitItem()).isEqualTo(ArrayBaseResponse(code=0, msg="", data= ArrayList(), status = Status.Loading))
            viewModel.getOptions("1")  // This line calls the function to trigger the interaction
            val expectedOption1= OptionX(id=4, slug = "Car Option 1" ,name = "Cars Fix Option 3", child = true, parent = 0)
            val expectedOption2 = OptionX(id=5, slug = "Car Option 2",name = "Cars Fix Option 3", child = true, parent = 0)
            assertThat(awaitItem()).isEqualTo(ArrayBaseResponse(msg = "", code = 200,data = arrayListOf(Option(name = "Car Fix", id = 1, slug = "Car Fix", type = "select",options = arrayListOf(expectedOption1,expectedOption2), parent = 0))))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { userRepository.getOptions("1") } // Verify after the interaction
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}