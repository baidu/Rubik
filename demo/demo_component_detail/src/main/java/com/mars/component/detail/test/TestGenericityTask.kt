package com.mars.component.detail.test

import androidx.lifecycle.LiveData
import com.mars.component.detail.value.TestNullableBean
import com.rubik.annotations.route.RRoute

@RRoute(path = "doTestGenericity1", forResult = true)
fun doTestGenericity1(data: List<TestNullableBean?>?): List<TestNullableBean?>? {
    println(" AP DBG doTestGenericity1 Context !!!")
    return data
}

@RRoute(path = "doTestGenericity2", forResult = true)
fun doTestGenericity2(data: LiveData<TestNullableBean?>?): LiveData<TestNullableBean?>? {
    println(" AP DBG doTestGenericity2 Context !!!")
    return data
}

@RRoute(path = "doTestGenericity3", forResult = true)
fun doTestGenericity3(data: Map<String, Long?>): Map<String, Long?> {
    println(" AP DBG doTestGenericity3 Context !!!")
    return data
}

class TestGenericityTask {
    @RRoute(path = "testGenericity4", forResult = true)
    val testGenericity4: List<String?> = listOf()

    @RRoute(path = "testGenericity5", forResult = true)
    val testGenericity5: (List<String?>?) -> List<String?>? = {
        println(" AP DBG testGenericity5 Context !!!")
        it
    }

    @RRoute(path = "doTestGenericity6", forResult = true)
    fun doTestGenericity6(data: Array<TestNullableBean?>?): Array<TestNullableBean?>? {
        println(" AP DBG doTestGenericity6 Context !!!")
        return data
    }

    companion object {
        @RRoute(path = "doTestGenericity7", forResult = true)
        fun doTestGenericity7(data: Array<Long?>?): Array<Long?>? {
            println(" AP DBG doTestGenericity7 Context !!!")
            return data
        }
    }
}

@RRoute(path = "doTestGenericity8")
fun doTestGenericity8(resultSuccess: (ArrayList<String>) -> Unit){
    println(" AP DBG doTestGenericity8 Context !!!")

}

@RRoute(path = "doTestGenericity9")
fun doTestGenericity9(resultSuccess: (List<String>) -> Unit){
    println(" AP DBG doTestGenericity9 Context !!")
}