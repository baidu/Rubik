package com.mars.component.home.test.kotlin

import android.content.Context
import com.rubik.logger.Logger
import com.rubik.router.navigate
import com.rubik.router.navigateForResult
import com.rubik.router.result
import rubik.generate.context.demo_com_mars_rubik_test_detail.DetailContext
import rubik.generate.context.demo_com_mars_rubik_test_detail.TestNullableBean

fun invokeDataType(context: Context) {

    Logger.d(" AP DBG doTestDataType1 result: ${DetailContext.doTestDataType1(null)}")
    Logger.d(" AP DBG doTestDataType2 result: ${DetailContext.doTestDataType2(null)}")
    Logger.d(" AP DBG doTestDataType3 result: ${DetailContext.doTestDataType3(null)}")
    Logger.d(" AP DBG doTestDataType4 result: ${DetailContext.doTestDataType4(null)}")
    Logger.d(" AP DBG doTestDataType5 result: ${DetailContext.doTestDataType5(null)}")
    Logger.d(" AP DBG doTestDataType6 result: ${DetailContext.doTestDataType6(null)}")
    Logger.d(" AP DBG doTestDataType7 result: ${DetailContext.doTestDataType7(null)}")
    Logger.d(" AP DBG doTestDataType8 result: ${DetailContext.doTestDataType8(null)}")
    Logger.d(" AP DBG doTestDataType9 result: ${DetailContext.doTestDataType9(null)}")

    Logger.d(" AP DBG doTestDataType1 result: ${DetailContext.doTestDataType1(context)}")
    Logger.d(" AP DBG doTestDataType2 result: ${DetailContext.doTestDataType2(101)}")
    Logger.d(" AP DBG doTestDataType3 result: ${DetailContext.doTestDataType3("102")}")
    Logger.d(" AP DBG doTestDataType4 result: ${DetailContext.doTestDataType4(TestNullableBean(202, "203"))}")
    Logger.d(
        " AP DBG doTestDataType5 result: ${DetailContext.doTestDataType5(
            mutableListOf(
                "AA11",
                "BB22"
            )
        )}"
    )
    Logger.d(" AP DBG doTestDataType6 result: ${DetailContext.doTestDataType6(arrayOf(111L, 222L))}")
    Logger.d(" AP DBG doTestDataType7 result: ${DetailContext.doTestDataType7(longArrayOf(111L, 222L))}")
    Logger.d(" AP DBG doTestDataType7-2 result: ${navigateForResult<LongArray> {
        uri = DetailContext.Uris.DO_TEST_DATA_TYPE7
        query {
            "data" with longArrayOf(111L, 222L)
        }
    }}")
    Logger.d(" AP DBG doTestDataType8 result: ${DetailContext.doTestDataType8(intArrayOf(11, 22))}")
    Logger.d(
        " AP DBG doTestDataType9 result: ${DetailContext.doTestDataType9(
            booleanArrayOf(
                true,
                false
            )
        )}"
    )

    navigate {
        uri=DetailContext.Uris.DO_TEST_DATA_TYPE10
        query {
            "1" with object : Function1<Int, Unit> {
                override fun invoke(p1: Int) {
                    Logger.d(" AP DBG HOF doTestDataType10_1 result: $p1  dsl!! ")
                }
            }
            "2" with object : Function1<String, Unit> {
                override fun invoke(p1: String) {
                    Logger.d(" AP DBG HOF doTestDataType10_2 result: $p1  dsl!! ")
                }
            }
        }
    }

    DetailContext.doTestDataType10 ({
        Logger.d(" AP DBG HOF doTestDataType10_1 result: $it  !! ")
    },{
        Logger.d(" AP DBG HOF doTestDataType10_2 result: $it  !! ")
    })

    navigate {
        uri = DetailContext.Uris.DO_TEST_DATA_TYPE11
        result<Int, TestNullableBean> { d1, d2 ->
            Logger.d(" AP DBG HOF doTestDataType11_1 result: $d1  ${d2.d2}  dsl!! ")
        }
        result<TestNullableBean?> {
            Logger.d(" AP DBG HOF doTestDataType11_1 result: ${it?.d2}  dsl!! ")
        }
    }

    DetailContext.doTestDataType11({ d1, d2 ->
        Logger.d(" AP DBG HOF doTestDataType11_1 result: $d1  ${d2.d2} !! ")
    }, {
        Logger.d(" AP DBG HOF doTestDataType11_1 result: ${it?.d2}  !! ")
    })
    
}