package rubik.generate.aggregate.demo_com_mars_rubik_test_detail_java

import android.content.Context
import androidx.annotation.Keep
import com.rubik.activity.Launcher
import com.rubik.annotations.source.RGenerated
import com.rubik.context.Aggregatable
import com.rubik.context.AggregateFactory
import com.rubik.route.Queries
import com.rubik.route.Result
import com.rubik.route.ResultGroups
import com.rubik.route.mapping.caseToTypeOfT
import com.rubik.route.mapping.toTypeOfT
import com.rubik.router.uri.Path
import kotlin.Array
import kotlin.Function0
import kotlin.Int
import kotlin.Long
import kotlin.LongArray
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.DetailJavaRouteActions
import rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean

/**
 * aggregate router function and router event of Rubik Context.
 *
 * context uri: [demo://com.mars.rubik-test.detail-java]
 * version: 0.1.4
 */
@RGenerated(
  kind = "aggregate",
  by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
  version = "0.1.4"
)
@Keep
class DetailJavaAggregate : Aggregatable, DetailJavaRouteActions {
  override fun onEvent(msg: String, queries: Queries) {
    when(msg){
      else -> {}
    }
  }

  override fun onRoute(
    path: String,
    queries: Queries,
    results: ResultGroups
  ) {
    when {
      "doTestDataType1" == path ->  {
        val data = queries.value(0, null)
        doTestDataType1(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType1-2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType12(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType2(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType2-2" == path ->  {
        val data = queries.value(0, null)
        doTestDataType22(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType3" == path ->  {
        val data = queries.value(0, null)
        doTestDataType3(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType4" == path ->  {
        val data = queries.value(0, null)
        doTestDataType4(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType5" == path ->  {
        val data = queries.value(0, null)
        doTestDataType5(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType6" == path ->  {
        val data = queries.value(0, null)
        doTestDataType6(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "doTestDataType7" == path ->  {
        val data = queries.value(0, null)
        doTestDataType7(
          data.caseToTypeOfT()
        ).apply {
          results.set(0, Result(this))
        }
      }
      "do-sth-bean-provide-instance-by-func" == path ->  {
        val rubikInstanceValue = queries.value(0, null)
        val d1 = queries.value(1, null)
        val rubikReceiveResult0 : (rubik.generate.context.demo_com_mars_rubik_test_detail_java.TestJavaBean) -> kotlin.Unit = { arg0 ->
          results.set(0, Result(arg0))
        }
        doSthBeanProvideInstanceByFunc(
              rubikInstanceValue.caseToTypeOfT(),
              d1.caseToTypeOfT(),
              rubikReceiveResult0
            )
      }
      "doSth" == path ->  {
        doSth()
      }
      "1.0/property/property" == path ->  {
        val rubikReceiveResult0 : (kotlin.String) -> kotlin.Unit = { arg0 ->
          results.set(0, Result(arg0))
        }
        propertyProperty(
              rubikReceiveResult0
            )
      }
      "doSthStatic" == path ->  {
        val i = queries.value(0, null)
        val j = queries.value(1, null)
        val k = queries.value(2, null)
        val rubikReceiveResult0 : (kotlin.String) -> kotlin.Unit = { arg0 ->
          results.set(0, Result(arg0))
        }
        doSthStatic(
              i.caseToTypeOfT(),
              j.caseToTypeOfT(),
              k.caseToTypeOfT(),
              rubikReceiveResult0
            )
      }
      "property/static" == path ->  {
        val rubikReceiveResult0 : (kotlin.String) -> kotlin.Unit = { arg0 ->
          results.set(0, Result(arg0))
        }
        propertyStatic(
              rubikReceiveResult0
            )
      }
      "do-sth-async-open" == path ->  {
        val onResult : (kotlin.String?, kotlin.Int) -> kotlin.Unit = { arg0,arg1 ->
          results.set(0, Result(arg0),Result(arg1))
        }
        doSthAsyncOpen(
              onResult
            )
      }
      "do-sth-async-interface" == path ->  {
        val onResult : (kotlin.String?, kotlin.Int) -> kotlin.Unit = { arg0,arg1 ->
          results.set(0, Result(arg0),Result(arg1))
        }
        doSthAsyncInterface(
              onResult
            )
      }
      "activity/java_page1" == path ->  {
        // com.mars.component.detail.java.ui.FirstPageActivity
        // - parameters:
        // --- key_str4 : kotlin.collections.List<kotlin.String>?
        // --- key_str5 : kotlin.collections.List<com.mars.component.detail.java.value.TestJavaBean>?
        // --- key_str6 : kotlin.Array<com.mars.component.detail.java.value.TestJavaBean>?
        Launcher().launch(com.mars.component.detail.java.ui.FirstPageActivity::class.java,queries,null,results)
      }
      Path("activity/java_page2/{key_str1}/{key_str2}/{key_str3}").matching(path) ->  {
        // com.mars.component.detail.java.ui.SecondActivity
        // - parameters:
        // --- key_str1 : kotlin.String?
        // --- key_str2 : kotlin.String?
        // --- key_str3 : kotlin.Int?
        Launcher().launch(com.mars.component.detail.java.ui.SecondActivity::class.java,queries,Path("activity/java_page2/{key_str1}/{key_str2}/{key_str3}").getParameters(path),results)
      }
      else -> { throw com.rubik.route.exception.BadPathOrVersionException(path)}
    }
  }

  override fun doTestDataType1(data: Context): Context? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType1
    // - parameters:
    // --- data : android.content.Context
    // - resultType:
    // --- android.content.Context
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType1(
          data
        )
  }

  override fun doTestDataType12(data: Context?): Context? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType1_2
    // - parameters:
    // --- data : android.content.Context?
    // - resultType:
    // --- android.content.Context?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType1_2(
          data
        )
  }

  override fun doTestDataType2(data: Int?): Int? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType2
    // - parameters:
    // --- data : kotlin.Int?
    // - resultType:
    // --- kotlin.Int?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType2(
          data
        )
  }

  override fun doTestDataType22(data: Int): Int? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType2_2
    // - parameters:
    // --- data : kotlin.Int
    // - resultType:
    // --- kotlin.Int
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType2_2(
          data
        )
  }

  override fun doTestDataType3(data: String?): String? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType3
    // - parameters:
    // --- data : kotlin.String?
    // - resultType:
    // --- kotlin.String?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType3(
          data
        )
  }

  override fun doTestDataType4(data: TestJavaBean?): TestJavaBean? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType4
    // - parameters:
    // --- data : com.mars.component.detail.java.value.TestJavaBean?
    // - resultType:
    // --- com.mars.component.detail.java.value.TestJavaBean?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType4(
          data.toTypeOfT()
        ).toTypeOfT()
  }

  override fun doTestDataType5(data: List<String>?): List<String>? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType5
    // - parameters:
    // --- data : kotlin.collections.List<kotlin.String>?
    // - resultType:
    // --- kotlin.collections.List<kotlin.String>?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType5(
          data
        )
  }

  override fun doTestDataType6(data: Array<Long>?): Array<Long>? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType6
    // - parameters:
    // --- data : kotlin.Array<kotlin.Long>?
    // - resultType:
    // --- kotlin.Array<kotlin.Long>?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType6(
          data
        )
  }

  override fun doTestDataType7(data: LongArray?): LongArray? {
    // com.mars.component.detail.java.test.TestJavaDataType.doTestDataType7
    // - parameters:
    // --- data : kotlin.LongArray?
    // - resultType:
    // --- kotlin.LongArray?
    return com.mars.component.detail.java.test.TestJavaDataType().doTestDataType7(
          data
        )
  }

  override fun doSthBeanProvideInstanceByFunc(
    rubikInstanceValue: String,
    d1: TestJavaBean,
    rubikReceiveResult0: (TestJavaBean) -> Unit
  ) {
    // com.mars.component.detail.java.api.ApisForInstance.doSthBean
    // - parameters:
    // --- d1 : com.mars.component.detail.java.value.TestJavaBean
    // - resultType:
    // --- com.mars.component.detail.java.value.TestJavaBean
    rubikReceiveResult0(
          com.mars.component.detail.java.api.ApisForInstance(
          rubikInstanceValue
        ).doSthBean(
          d1.toTypeOfT()
        ).toTypeOfT()
        )
  }

  override fun doSth() {
    // com.mars.component.detail.java.api.Apis.doSth
    // - resultType:
    // --- null
    com.mars.component.detail.java.api.Apis().doSth()
  }

  override fun propertyProperty(rubikReceiveResult0: (String) -> Unit) {
    // com.mars.component.detail.java.api.Apis.property
    // - resultType:
    // --- kotlin.String
    rubikReceiveResult0(
          com.mars.component.detail.java.api.Apis().property
        )
  }

  override fun doSthStatic(
    i: Int,
    j: String,
    k: Long,
    rubikReceiveResult0: (String) -> Unit
  ) {
    // com.mars.component.detail.java.api.Apis.doSthCompanion
    // - parameters:
    // --- i : kotlin.Int
    // --- j : kotlin.String
    // --- k : kotlin.Long
    // - resultType:
    // --- kotlin.String
    rubikReceiveResult0(
          com.mars.component.detail.java.api.Apis.doSthCompanion(
          i,
          j,
          k
        )
        )
  }

  override fun propertyStatic(rubikReceiveResult0: (String) -> Unit) {
    // com.mars.component.detail.java.api.Apis.propertyCompanion
    // - resultType:
    // --- kotlin.String
    rubikReceiveResult0(
          com.mars.component.detail.java.api.Apis.propertyCompanion
        )
  }

  override fun doSthAsyncOpen(onResult: (String?, Int) -> Unit) {
    // com.mars.component.detail.java.api.ApisAsyncReturn.doSthAsyncOpen
    // - parameters:
    // --- onResult : com.mars.component.detail.java.api.callback.Callback
    // - resultType:
    // --- null
    val onResultRubikResultTransform = object : com.mars.component.detail.java.api.callback.Callback() {
      override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
        onResult(
              v1,
              v2
            )
      }
    }
    com.mars.component.detail.java.api.ApisAsyncReturn().doSthAsyncOpen(
          onResultRubikResultTransform
        )
  }

  override fun doSthAsyncInterface(onResult: (String?, Int) -> Unit) {
    // com.mars.component.detail.java.api.ApisAsyncReturn.doSthAsyncInterface
    // - parameters:
    // --- onResult : com.mars.component.detail.java.api.callback.Callbackable
    // - resultType:
    // --- null
    val onResultRubikResultTransform = object : com.mars.component.detail.java.api.callback.Callbackable {
      override fun onCall(v1: kotlin.String?,v2: kotlin.Int) {
        onResult(
              v1,
              v2
            )
      }
    }
    com.mars.component.detail.java.api.ApisAsyncReturn().doSthAsyncInterface(
          onResultRubikResultTransform
        )
  }

  @RGenerated(
    kind = "aggregate_companion",
    by = "rubik-kapt:1.9.0.1.T-K1_3-LOCAL",
    version = "0.1.4"
  )
  @Keep
  companion object : AggregateFactory() {
    override val URI: String = "demo://com.mars.rubik-test.detail-java"

    override val DEPENDENCIES: List<String> = listOf()

    override val EVENT_MSGS: List<String> = listOf()

    override val CREATOR: Function0<Aggregatable> = {DetailJavaAggregate()}
  }
}
